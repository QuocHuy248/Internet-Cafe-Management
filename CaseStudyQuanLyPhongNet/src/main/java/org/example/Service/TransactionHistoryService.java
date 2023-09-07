package org.example.Service;

import org.example.Utils.FileUtils;
import org.example.model.TransactionHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionHistoryService implements ITransactionHistory {
    private String fileTransaction = "./data/deposit_transaction_history.txt";

    @Override
    public List<TransactionHistory> getAllTH() {
        return FileUtils.readData(fileTransaction, TransactionHistory.class);
    }

    @Override
    public void deleteTH(long id) {
        List<TransactionHistory> transactionHistories = getAllTH();
        transactionHistories.remove(transactionHistories.stream().filter(o -> o.getId() == id).findFirst().get());
        FileUtils.writeData(fileTransaction, transactionHistories);

    }

    @Override
    public void deleteTH(String username) {
        List<TransactionHistory> transactionHistories = getAllTH();
        List<TransactionHistory> transactionHistories1 =
                transactionHistories.stream().filter(transactionHistory
                        -> transactionHistory.getUsername() == username).collect(Collectors.toList());
        transactionHistories1.stream().forEach(transactionHistory
                -> transactionHistories.remove(transactionHistory));
        FileUtils.writeData(fileTransaction, transactionHistories);
    }

    @Override
    public void deleteTH(LocalDate localDate) {
        List<TransactionHistory> transactionHistories = getAllTH();
        List<TransactionHistory> transactionHistories1 =
                transactionHistories.stream().filter(transactionHistory
                        -> transactionHistory.getDateDeposit().equals(localDate)).collect(Collectors.toList());
        transactionHistories1.stream().forEach(transactionHistory
                -> transactionHistories.remove(transactionHistory));
        FileUtils.writeData(fileTransaction, transactionHistories);

    }

    @Override
    public List<TransactionHistory> findTH(String username) {
        List<TransactionHistory> transactionHistories = getAllTH();
        List<TransactionHistory> transactionHistories1 =
                transactionHistories.stream().filter(transactionHistory
                        -> transactionHistory.getUsername().equals(username)).collect(Collectors.toList());
        return transactionHistories1;
    }

    @Override
    public TransactionHistory findTHbyid(long id) {
        return null;
    }

    @Override
    public List<TransactionHistory> findTHByAmountTransaction(long amount) {
        List<TransactionHistory> transactionHistories = getAllTH();
        List<TransactionHistory> transactionHistory = transactionHistories.stream()
                .filter(transactionHistory1 -> transactionHistory1.getAmountDeposit() == amount).collect(Collectors.toList());
        return transactionHistory;
    }

    @Override
    public List<TransactionHistory> findTH(LocalDate date) {
        List<TransactionHistory> transactionHistories = getAllTH();
        List<TransactionHistory> transactionHistories1 =
                transactionHistories.stream().filter(transactionHistory
                        -> transactionHistory.getDateDeposit().equals(date)).collect(Collectors.toList());
        return transactionHistories1;
    }

    @Override
    public void createTH(TransactionHistory transactionHistory) {
        List<TransactionHistory> transactionHistories = getAllTH();
        transactionHistories.add(transactionHistory);
        FileUtils.writeData(fileTransaction, transactionHistories);

    }

    @Override
    public long calculateRevenue(List<TransactionHistory> transactionHistories) {
        Long totalRevenue = 0L;
        for(TransactionHistory o : transactionHistories){
            totalRevenue += o.getAmountDeposit();
        }
        return totalRevenue;
    }

    @Override
    public List<TransactionHistory> getTHBetween(LocalDate startDate, LocalDate endDate) {
        List<TransactionHistory> THs = getAllTH();
        List<TransactionHistory> orderBetween = new ArrayList<>();
        for(TransactionHistory TH : THs){
            LocalDate orderDate = TH.getDateDeposit();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                orderBetween.add(TH);
            }
        }
        return orderBetween;
    }


}
