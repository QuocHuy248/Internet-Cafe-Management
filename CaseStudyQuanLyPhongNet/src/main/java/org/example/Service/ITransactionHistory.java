package org.example.Service;

import org.example.model.TransactionHistory;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionHistory {
    List<TransactionHistory> getAllTH();
    void deleteTH(long id);
    void deleteTH(String username);
    void deleteTH(LocalDate localDate);
    List<TransactionHistory>  findTH(String username);
    TransactionHistory findTHbyid(long id);
    List<TransactionHistory>   findTH(LocalDate date);
    List<TransactionHistory>   findTHByAmountTransaction(long amount);

    void createTH(TransactionHistory transactionHistory);
    long calculateRevenue(List<TransactionHistory>transactionHistories);
    List<TransactionHistory>getTHBetween(LocalDate startDate, LocalDate endDate);



}
