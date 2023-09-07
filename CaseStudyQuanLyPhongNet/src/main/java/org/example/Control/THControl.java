package org.example.Control;

import org.example.Service.ITransactionHistory;
import org.example.Service.IUserService;
import org.example.Service.TransactionHistoryService;
import org.example.Service.UserService;
import org.example.Utils.DateUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.model.Order;
import org.example.model.TransactionHistory;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class THControl {
    private ITransactionHistory iTransactionHistory;
    private IUserService iUserService;
    private Scanner scanner = new Scanner(System.in);

    public THControl() {
        iTransactionHistory = new TransactionHistoryService();
        iUserService = new UserService();
    }

    public void THControlView() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║             Quản lý lịch sử nạp tiền        ║");
        System.out.println("║                                             ║");
        System.out.println("║          0.Trở về.                          ║");
        System.out.println("║          1.Hiển thị lịch sử nạp tiền.       ║");
        System.out.println("║          2.Tìm kiếm lịch sử giao dịch.      ║");
        System.out.println("║          3.Sắp xếp lịch sử giao dịch.       ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 3);
        ;
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.adminWorkView();
                break;
            case 1:
                showTH();
                THControlView();
                break;
            case 2:
                findTH();
                THControlView();
                break;
            case 3:
                sortTH();
                THControlView();
                break;

        }
    }

    private void sortTH() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn kiểu sắp xếp:             ║");
        System.out.println("║                                             ║");
        System.out.println("║                0.Trở về                     ║");
        System.out.println("║                1.Tăng dần.                  ║");
        System.out.println("║                2.Giảm dân.                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int select = ValidateUtils.getIntOfWithBounds(0, 2);
        ;
        List<TransactionHistory> th = iTransactionHistory.getAllTH();
        Comparator<TransactionHistory> comparator = null;
        switch (select) {
            case 0:
                THControlView();
                break;
            case 1:
                th.sort(sortTHDescending(comparator));
                break;

            case 2:
                th.sort(sortTHDecreasing(comparator));
                break;

        }
        showTH(th);
    }

    private Comparator<TransactionHistory> sortTHDecreasing(Comparator<TransactionHistory> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║              0.Trở về.                      ║");
        System.out.println("║              1.ID.                          ║");
        System.out.println("║              2.Ngày giao dịch.              ║");
        System.out.println("║              3.Username.                    ║");
        System.out.println("║              4.Giá trị giao dịch.           ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int choice = ValidateUtils.getIntOfWithBounds(0, 4);
        ;
        switch (choice) {
            case 0:
                sortTH();
                break;
            case 1:
                comparator = Comparator.comparing(TransactionHistory::getId).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(TransactionHistory::getDateDeposit).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(TransactionHistory::getUsername).reversed();
                break;
            case 4:
                comparator = Comparator.comparing(TransactionHistory::getAmountDeposit).reversed();
                break;
        }
        return comparator;

    }

    private Comparator<TransactionHistory> sortTHDescending(Comparator<TransactionHistory> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║              0.Trở về.                      ║");
        System.out.println("║              1.ID.                          ║");
        System.out.println("║              2.Ngày giao dịch.              ║");
        System.out.println("║              3.Username.                    ║");
        System.out.println("║              4.Giá trị giao dịch.           ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 4);
        ;
        switch (choice) {
            case 0:
                sortTH();
                break;
            case 1:
                comparator = Comparator.comparing(TransactionHistory::getId);
                break;
            case 2:
                comparator = Comparator.comparing(TransactionHistory::getDateDeposit);
                break;
            case 3:
                comparator = Comparator.comparing(TransactionHistory::getUsername);
                break;
            case 4:
                comparator = Comparator.comparing(TransactionHistory::getAmountDeposit);
                break;
        }
        return comparator;

    }


    private void findTH() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn tìm kiếm theo:        ║");
        System.out.println("║                                             ║");
        System.out.println("║                0.Trở về                     ║");
        System.out.println("║                1.Username.                  ║");
        System.out.println("║                2.Ngày giao dịch.            ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 2);
        ;
        List<TransactionHistory> transactionHistories1 = null;
        switch (action) {
            case 0:
                THControlView();
                break;
            case 1:
                transactionHistories1 = findTHByUserName();
                break;
            case 2:
                transactionHistories1 = findTHByDate();
                break;
        }
        showTH(transactionHistories1);
    }

    private List<TransactionHistory> findTHByDate() {
        LocalDate date = DateUtils.parseDate(checkInputValidDayTHExist(ValidateUtils.FIELD_DATE, ValidateUtils.FIELD_DOB_MESSAGE, ValidateUtils.REGEX_DATE));
        List<TransactionHistory> th = iTransactionHistory.findTH(date);
        return th;
    }

    private List<TransactionHistory> findTHByUserName() {
        String username = checkInputValidUserNameOExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        List<TransactionHistory> th = iTransactionHistory.findTH(username);
        return th;
    }


    private void showTH() {
        List<TransactionHistory> th = iTransactionHistory.getAllTH();
        System.out.printf("╔═══════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%10s ║ %15s ║ %20s ║ %20s ║ \n", "ID",
                "USER NAME", "DATE", "AMOUNT DEPOSIT");
        System.out.printf("║═══════════════════════════════════════════════════════════════════════════║\n");

        for (TransactionHistory o : th) {
            System.out.printf("║%10s ║ %15s ║ %20s ║ %20s ║  \n", o.getId(),
                    o.getUsername(), DateUtils.formatDate(o.getDateDeposit()), o.getAmountDeposit());
        }
        System.out.printf("╚═══════════════════════════════════════════════════════════════════════════╝\n");


    }

    private void showTH(List<TransactionHistory> th) {
        System.out.printf("╔═══════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%10s ║ %15s ║ %20s ║ %20s ║ \n", "ID",
                "USER NAME", "DATE", "AMOUNT DEPOSIT");
        System.out.printf("║═══════════════════════════════════════════════════════════════════════════║\n");

        for (TransactionHistory o : th) {
            System.out.printf("║%10s ║ %15s ║ %20s ║ %20s ║  \n", o.getId(),
                    o.getUsername(), DateUtils.formatDate(o.getDateDeposit()), o.getAmountDeposit());
        }
        System.out.printf("╚═══════════════════════════════════════════════════════════════════════════╝\n");
    }

    public static void main(String[] args) {
        THControl thControl = new THControl();
        thControl.THControlView();
    }

    private String checkInputValid(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {         // Nếu SAI
                System.out.println(fieldMessage);
                validateInput = true;
            } else {
                validateInput = false;
            }
        } while (validateInput);
        return input;
    }

    private String checkInputValidUserNameOExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkUserNameOrder(input)) {
                    System.out.println("Không tìm thấy username trong danh sách , vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                THControlView();
            }

        } while (validateInput);
        return input;
    }

    private boolean checkUserNameOrder(String userName) {
        List<User> users = iUserService.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private String checkInputValidDayTHExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkDayTH(input)) {
                    System.out.println("Không tìm thấy ngày trong danh sách giao dịch, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                THControlView();
            }

        } while (validateInput);
        return input;
    }

    private boolean checkDayTH(String input) {
        boolean check = false;
        LocalDate date = DateUtils.parseDate(input);
        List<TransactionHistory> transactionHistories = iTransactionHistory.getAllTH();
        for (TransactionHistory th : transactionHistories) {
            if (th.getDateDeposit().equals(date)) {
                check = true;
            }
        }
        return check;
    }

}
