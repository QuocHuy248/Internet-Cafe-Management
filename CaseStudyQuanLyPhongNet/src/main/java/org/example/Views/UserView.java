package org.example.Views;

import org.example.Control.ComputerControl;
import org.example.Control.OrderControl;
import org.example.Control.ProductControl;
import org.example.Service.*;
import org.example.Utils.AuthUtils;
import org.example.Utils.PasswordUtils;
import org.example.Utils.TimeUtils;
import org.example.Utils.ValidateUtils;
import org.example.model.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private IComputerService iComputerService;
    private IUserService iUserService;
    private IProductService iProductService;

    public UserView() {
        iComputerService = new ComputerService();
        iUserService = new UserService();
        iProductService = new ProductService();
    }


    private Scanner scanner = new Scanner(System.in);

    public void userLauncher() {
        LoginView loginView = new LoginView();
        ComputerService computerService = new ComputerService();
        ComputerControl computerControl = new ComputerControl();

        computerControl.showComputer(computerControl.updateBalance());
        String computerName =
                checkInputValid(ValidateUtils.FIELD_COMPUTER,
                        ValidateUtils.FIELD_COMPUTER_MESSAGE,
                        ValidateUtils.REGEX_COMPUTER);
        if (computerService.checkComputerStatus(computerName)) {
            AuthUtils.setComputerAuthentication(computerService.findComputerByName(computerName));
            Computer computer = AuthUtils.getComputer();
            System.out.println("Nhập username:");
            String username = scanner.nextLine();
            System.out.println("Nhập password:");
            String password = scanner.nextLine();
            if (iUserService.checkUserNamePassword(username, password)) {
                if (!checkUserUsing(username)) {
                    User user = iUserService.findUser(username);
                    AuthUtils.setUserAuthentication(user);
                    if (user.getRole().equals(ERole.GUEST)) {
                        if (iUserService.checkGuestBalance(user)) {
                            computer.setUsername(username);
                            computer.setStatusComputer(EStatusComputer.InUse);
                            LocalTime time = LocalTime.now();
                            String timeNow = TimeUtils.formatTime(time);
                            LocalTime timeResult = TimeUtils.parseTime(timeNow);
                            computer.setStartUsing(timeResult);
                            iComputerService.updateComputer(computer.getName(), computer);
                            userViewLauncher();
                        } else if (!iUserService.checkGuestBalance(user)) {
                            System.out.println("Tài khoản của bạn không còn tiền, vui lòng nạp thêm tiền để có thể sử dụng");
                            userLauncher();
                        }
                    } else if (user.getRole().equals(ERole.ADMIN) || user.getRole().equals(ERole.EMPLOYEE)) {
                        System.out.println("Bạn đã chọn sai role, vui lòng chọn lại");
                        loginView.launcher();
                    }
                } else {
                    System.out.println("Tài khoản đang được sử dụng, vui lòng chọn lại");
                    userLauncher();
                }
            } else if (!iUserService.checkUserNamePassword(username, password)) {
                System.out.println("╔═════════════════════════════════════════════╗");
                System.out.println("║   Tài khoản và mật khẩu không chính xác.    ║");
                System.out.println("╚═════════════════════════════════════════════╝");
                System.out.println("╔═════════════════════════════════════════════╗");
                System.out.println("║           Bạn có muốn nhập lại không.       ║");
                System.out.println("║                                             ║");
                System.out.println("║            1. Có.                           ║");
                System.out.println("║            2. Không.                        ║");
                System.out.println("║                                             ║");
                System.out.println("╚═════════════════════════════════════════════╝");
                int action = ValidateUtils.getIntOfWithBounds(1, 2);
                switch (action) {
                    case 1:
                        userLauncher();
                        break;
                    case 2:
                        loginView.launcher();
                }
            }
        } else if (!computerService.checkComputerStatus(computerName)) {
            System.out.println("Máy không thể sử dụng, vui lòng chọn lại");
            userLauncher();
        }


    }

    public void userViewLauncher() {
        upDateBalanceOfUserPlaying(AuthUtils.getComputer(), AuthUtils.getUser());
        if (!checkBalance(AuthUtils.getUser())) {
            iComputerService.updateComputerStatusFromInUsetoReady(AuthUtils.getComputer());
            userLauncher();
        }
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║            Menu của người chơi.             ║");
        System.out.println("║                                             ║");
        System.out.println("║              0.Thoát.                       ║");
        System.out.println("║              1.Đổi mật khẩu.                ║");
        System.out.println("║              2.Đặt hàng.                    ║");
        System.out.println("║                                             ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int act = ValidateUtils.getIntOfWithBounds(0, 2);
        switch (act) {
            case 0:
                iComputerService.updateComputerStatusFromInUsetoReady(AuthUtils.getComputer());
                LoginView loginView = new LoginView();
                loginView.launcher();
                break;
            case 1:
                updatePassword();
                userViewLauncher();
                break;
            case 2:
                ProductControl productControl = new ProductControl();
                productControl.showProduct();
                OrderControl orderControl = new OrderControl();
                orderControl.createOrderByUser(AuthUtils.getUser());
                userViewLauncher();
                break;

        }

    }


    private void updatePassword() {
        String password1 = checkInputPassWordValid(ValidateUtils.FIELD_PASSWORD, ValidateUtils.FIELD_PASSWORD_MESSAGE, ValidateUtils.REGEX_PASSWORD);
        if (iUserService.checkUserPassword(AuthUtils.getUser(), password1)) {
            String password = checkInputPassWordValid(ValidateUtils.FIELD_PASSWORDNEW, ValidateUtils.FIELD_PASSWORD_MESSAGE, ValidateUtils.REGEX_PASSWORD);
            System.out.println("Bạn có muốn đổi mật khẩu không");
            System.out.println("1.Có");
            System.out.println("2.Không");
            int choice = ValidateUtils.getIntOfWithBounds(1, 2);
            switch (choice) {
                case 1:
                    String newPassword = PasswordUtils.generatePassword(password);
                    iUserService.updatePassword(AuthUtils.getUser(), newPassword);
                    break;
                case 2:
                    userViewLauncher();
                    break;
            }
        } else {
            System.out.println("Mật khẩu không chính xác, bạn có muốn tiếp tục?");
            System.out.println("1.Có");
            System.out.println("2.Không");
            int choice = ValidateUtils.getIntOfWithBounds(1, 2);
            switch (choice) {
                case 1:
                    updatePassword();
                    break;
                case 2:
                    userViewLauncher();
                    break;
            }
        }

        System.out.println("Đổi mật khẩu thành công");

    }
    private String checkInputPassWordValid(String fieldName, String fieldMessage, String fieldPattern) {
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

    private String checkInputValid(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<Computer> computers = iComputerService.getAllComputer();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkComputer(input)) {
                System.out.println("Không tìm thấy máy, vui lòng nhập lại tên máy");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private boolean checkComputer(String computerName) {
        List<Computer> computers = iComputerService.getAllComputer();
        for (Computer computer : computers) {
            if (computer.getName().equals(computerName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBalance(User user) {
        boolean check = true;
        if (user.getBalance() <= 0) {
            check = false;
        }
        return check;
    }

    public void upDateBalanceOfUserPlaying(Computer computer, User user) {
        LocalTime time = LocalTime.now();
        String timeNow = TimeUtils.formatTime(time);
        LocalTime timeResult = TimeUtils.parseTime(timeNow);
//       long pricePerSecond = Computer.getPrice() / 60;
//        Duration duration = Duration.between(startime, now);
//        long minute =  duration.toMinutes();
//        long resultbalance = balance - pricePerSecond * minute;
//        if (resultbalance < 0) {
//            return 0 ;
//        } else return resultbalance;
        long pricePerMinute = Computer.getPrice() / 60;
        Duration duration = Duration.between(computer.getStartUsing(), timeResult);
        long minute = duration.toMinutes();

        long resultBalance = user.getBalance() - pricePerMinute * minute;
        long value = 0;
        if (resultBalance < 0) {
            user.setBalance(value);
        }
        user.setBalance(resultBalance);

        long timePlayOfUser = (user.getBalance()) / pricePerMinute;
        System.out.printf("╔═══════════════════════════════════════════════╗\n");
        System.out.printf("║  Tên máy                ║ %-20s║\n", AuthUtils.getComputer().getName());
        System.out.printf("║  Tên người chơi         ║ %-20s║\n", AuthUtils.getUser().getUsername());
        System.out.printf("╚═══════════════════════════════════════════════╝\n");
        System.out.printf("╔═══════════════════════════════════════════════╗\n");
        System.out.printf("║  Thời gian đã sử dụng   ║ %-20s║\n", minute + " phút");
        System.out.printf("║  Thời gian còn lại      ║ %-20s║\n", timePlayOfUser + " phút");
        System.out.printf("║  Số dư tài khoản        ║ %-20s║\n", user.getBalance() + " đồng");
        System.out.printf("╚═══════════════════════════════════════════════╝\n");
        iUserService.updateBalance(user.getBalance(), user);
    }

    private boolean checkUserUsing(String username) {
        boolean check = false;
        List<Computer> computers = iComputerService.getAllComputer();
        for (Computer computer : computers) {
            if (computer.getUsername().equals(username)) {
                check = true;
            }
        }
        return check;
    }
}