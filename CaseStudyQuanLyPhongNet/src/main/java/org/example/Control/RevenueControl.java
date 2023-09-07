package org.example.Control;

import org.example.Service.IOrderService;
import org.example.Service.ITransactionHistory;
import org.example.Service.OrderService;
import org.example.Service.TransactionHistoryService;
import org.example.Utils.DateUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.model.Product;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.Scanner;

public class RevenueControl {
    private Scanner scanner = new Scanner(System.in);
    private IOrderService iOrderService;
    private ITransactionHistory iTransactionHistory;

    public RevenueControl() {
        iOrderService = new OrderService();
        iTransactionHistory = new TransactionHistoryService();
    }

    public void totalRevenueControl() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Menu quản lý doanh thu:        ║");
        System.out.println("║                                             ║");
        System.out.println("║              0. Trở về                      ║");
        System.out.println("║              1. Doanh thu giờ chơi          ║");
        System.out.println("║              2. Doanh thu bán hàng          ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 2);
        ;
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.adminWorkView();
                break;
            case 1:
                transHistoryRevenue();
                totalRevenueControl();
                break;
            case 2:
                orderRevenue();
                totalRevenueControl();
                break;
        }
    }

    private void orderRevenue() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║          Menu quản lý doanh thu bán hàng:   ║");
        System.out.println("║                                             ║");
        System.out.println("║            0. Trở về                        ║");
        System.out.println("║            1. Doanh thu theo ngày           ║");
        System.out.println("║            2. Doanh thu theo tháng          ║");
        System.out.println("║            3. Doanh thu theo năm            ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int action = ValidateUtils.getIntOfWithBounds(0, 3);
        ;
        switch (action) {
            case 0:
                totalRevenueControl();
                break;
            case 1:
                revenueOrderByDay();
                break;
            case 2:
                revenueOrderByMonth();
                break;
            case 3:
                revenueOrderByYear();
                break;
        }
    }

    private void revenueOrderByDay() {
        LocalDate date = validDay();
        Long revenue = iOrderService.calculateTotalRevenue(
                iOrderService.getOrderBetWeen(date, date));
        showRevenueByDate(date, revenue);
    }

    private void revenueOrderByYear() {
        Year year = validYear();
        LocalDate startOfYear = year.atDay(1);
        LocalDate endOfYear = year.atDay(year.length());
        Long revenue = iOrderService.calculateTotalRevenue(
                iOrderService.getOrderBetWeen(startOfYear, endOfYear));
        showRevenueByYear(year, revenue);

    }

    private void revenueOrderByMonth() {
        YearMonth month = validMonthYear();
        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();
        Long revenue = iOrderService.calculateTotalRevenue(
                iOrderService.getOrderBetWeen(startOfMonth, endOfMonth));
        showRevenueByMonth(month, revenue);
    }

    private void transHistoryRevenue() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║          Menu quản lý doanh thu giờ chơi:   ║");
        System.out.println("║                                             ║");
        System.out.println("║            0. Trở về                        ║");
        System.out.println("║            1. Doanh thu theo ngày           ║");
        System.out.println("║            2. Doanh thu theo tháng          ║");
        System.out.println("║            3. Doanh thu theo năm            ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 3);
        switch (action) {
            case 0:
                totalRevenueControl();
                break;
            case 1:
                revenueTHByDay();
                break;
            case 2:
                revenueTHByMonth();
                break;
            case 3:
                revenueTHByYear();
                break;
        }
    }

    private void revenueTHByDay() {
        LocalDate date = validDay();
        Long revenue = iTransactionHistory.calculateRevenue(
                iTransactionHistory.getTHBetween(date, date));
        showRevenueByDate(date, revenue);
    }

    private void revenueTHByYear() {
        Year year = validYear();
        LocalDate startOfYear = year.atDay(1);
        LocalDate endOfYear = year.atDay(year.length());
        Long revenue = iTransactionHistory.calculateRevenue(
                iTransactionHistory.getTHBetween(startOfYear, endOfYear));
        showRevenueByYear(year, revenue);

    }

    private void revenueTHByMonth() {
        YearMonth month = validMonthYear();
        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();
        Long revenue = iTransactionHistory.calculateRevenue(
                iTransactionHistory.getTHBetween(startOfMonth, endOfMonth));
        showRevenueByMonth(month, revenue);
    }
    private void showRevenueByDate(LocalDate date, long revenue) {
        System.out.printf("╔═══════════════════════════════════════╗\n");
        System.out.printf("║ %-20s ║ %-14s ║\n", "Date", "Total");
        System.out.printf("║═══════════════════════════════════════║\n");

        System.out.printf("║ %-20s ║ %-14s ║\n", DateUtils.formatDate(date), revenue);
        System.out.printf("╚═══════════════════════════════════════╝\n");
    }
    private void showRevenueByYear(Year year, long revenue) {
        System.out.printf("╔═══════════════════════════════════════╗\n");
        System.out.printf("║ %-20s ║ %-14s ║\n", "Year", "Total");
        System.out.printf("║═══════════════════════════════════════║\n");

        System.out.printf("║ %-20s ║ %-14s ║\n", DateUtils.formatYear(year), revenue);
        System.out.printf("╚═══════════════════════════════════════╝\n");
    }

    private void showRevenueByMonth(YearMonth month, long revenue) {
        System.out.printf("╔═══════════════════════════════════════╗\n");

        System.out.printf("║ %-20s ║ %-14s ║\n", "Month", "Total");
        System.out.printf("║═══════════════════════════════════════║\n");

        System.out.printf("║ %-20s ║ %-14s ║\n", DateUtils.formatMonth(month), revenue);
        System.out.printf("╚═══════════════════════════════════════╝\n");
    }

    public Year validYear() {
        Year year;
        do {
            System.out.println("Nhập năm (yyyy):");
            String strYear = scanner.nextLine();
            year = DateUtils.getValidYearFromMessage(strYear,"Năm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy");
        } while (year == null);
        return year;
    }

    public YearMonth validMonthYear() {
        YearMonth month;
        do {
            System.out.println("Nhập tháng (MM-yyyy):");
            String strMonth = scanner.nextLine();// validate
            month = DateUtils.getValidMonthFromMessage(strMonth,"Tháng không hợp lệ. Vui lòng nhập lại theo định dạng MM-yyyy");
        } while (month == null);
        return month;
    }
    public LocalDate validDay() {
        LocalDate localDate;
        do {
            System.out.println("Nhập ngày (dd-MM-yyyy):");
            String strDate = scanner.nextLine();
            localDate = DateUtils.getValidDateFromMessage(strDate,"Ngày không hợp lệ. Vui lòng nhập lại theo định dạng  dd-MM-yyyy");
        } while (localDate == null);
        return localDate;
    }

    public static void main(String[] args) {
        RevenueControl revenueControl = new RevenueControl();
        revenueControl.totalRevenueControl();
    }
}
