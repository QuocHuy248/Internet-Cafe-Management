package org.example.Control;

import lombok.Getter;
import org.example.Service.ITransactionHistory;
import org.example.Service.IUserService;
import org.example.Service.TransactionHistoryService;
import org.example.Service.UserService;
import org.example.Utils.AuthUtils;
import org.example.Utils.DateUtils;
import org.example.Utils.PasswordUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.Views.EmployeeView;
import org.example.model.EGender;
import org.example.model.ERole;
import org.example.model.TransactionHistory;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GuestControl {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;
    private ITransactionHistory iTransactionHistory;

    @Getter
    private long BonusInterest = 1;

    public void setBonusInterest(long bonusInterest) {
        BonusInterest = bonusInterest;

    }

    public GuestControl() {
        iUserService = new UserService();
        iTransactionHistory = new TransactionHistoryService();
    }

    public void guestControlView() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý tài khoản khách hàng.   ║");
        System.out.println("║                                                ║");
        System.out.println("║    0.Trở về.                                   ║");
        System.out.println("║    1.Xem danh sách.                            ║");
        System.out.println("║    2.Đăng ký tài khoản khách hàng.             ║");
        System.out.println("║    3.Tìm kiếm khách hàng.                      ║");
        System.out.println("║    4.Xóa tài khoản khách hàng.                 ║");
        System.out.println("║    5.Sắp xếp tài khoản khách hàng.             ║");
        System.out.println("║    6.Nạp tiền tài khoản khách hàng.            ║");
        System.out.println("║    7.Quản lý khuyến mãi.                       ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 7);
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.userControl();
                break;
            case 1:
                showGuest();
                guestControlView();
                break;
            case 2:
                addGuest();
                guestControlView();
                break;
            case 3:
                findGuest();
                guestControlView();
                break;
            case 4:
                deleteGuest();
                guestControlView();
                break;
            case 5:
                sortGuest();
                guestControlView();
                break;
            case 6:
                depositBalanceGuest();
                guestControlView();
                break;
            case 7:
                controlBonusBalanceInterest();
                guestControlView();
                break;
        }
    }

    public void guestControlViewByEmployee() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý tài khoản khách hàng.   ║");
        System.out.println("║                                                ║");
        System.out.println("║    0.Trở về.                                   ║");
        System.out.println("║    1.Xem danh sách.                            ║");
        System.out.println("║    2.Đăng ký tài khoản khách hàng.             ║");
        System.out.println("║    3.Tìm kiếm khách hàng.                      ║");
        System.out.println("║    4.Xóa tài khoản khách hàng.                 ║");
        System.out.println("║    5.Sắp xếp tài khoản khách hàng.             ║");
        System.out.println("║    6.Nạp tiền tài khoản khách hàng.            ║");
        System.out.println("║    7.Quản lý khuyến mãi.                       ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 7);
        switch (action) {
            case 0:
                EmployeeView employeeView = new EmployeeView();
                employeeView.employeeLauncher();
                break;
            case 1:
                showGuest();
                guestControlViewByEmployee();
                break;
            case 2:
                addGuest();
                guestControlViewByEmployee();
                break;
            case 3:
                findGuest();
                guestControlViewByEmployee();
                break;
            case 4:
                deleteGuest();
                guestControlViewByEmployee();
                break;
            case 5:
                sortGuest();
                guestControlViewByEmployee();
                break;
            case 6:
                depositBalanceGuest();
                guestControlViewByEmployee();
                break;
            case 7:
                controlBonusBalanceInterest();
                guestControlViewByEmployee();
                break;
        }
    }

    private void showGuest() {
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.GUEST))
                .collect(Collectors.toList());
        System.out.printf("╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");

        System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %13s║ %8s ║ \n",
                "ID", "Name", "Username", "Identity Id", "AGE", "DOB", "BALANCE", "GENDER", "ROLE");
        System.out.printf("║═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════║\n");

        for (User user : users1) {
            System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %13s║ %8s ║ \n",
                    user.getId(), user.getName(), user.getUsername(), user.getIdentityCardId()
                    , user.getAge(), DateUtils.formatDate(user.getDob()), user.getBalance(), user.getGender());
        }
        System.out.printf("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

    }

    private void showGuest(List<User> users) {
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.GUEST))
                .collect(Collectors.toList());
        System.out.printf("╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");

        System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %13s║ %8s ║ \n",
                "ID", "Name", "Username", "Identity Id", "AGE", "DOB", "BALANCE", "GENDER", "ROLE");
        System.out.printf("║═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════║\n");

        for (User user : users1) {
            System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %13s║ %8s ║ \n",
                    user.getId(), user.getName(), user.getUsername(), user.getIdentityCardId()
                    , user.getAge(), DateUtils.formatDate(user.getDob()), user.getBalance(), user.getGender());
        }
        System.out.printf("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

    }

    private void addGuest() {
        LocalDate date = LocalDate.now();
        String date1 = DateUtils.formatDate(date);
        LocalDate dateCreate = DateUtils.parseDate(date1);
        String name = checkInputValid(ValidateUtils.FIELD_FULLNAME, ValidateUtils.FIELD_FULLNAME_MESSAGE, ValidateUtils.REGEX_FULLNAME);
        long pid = Long.parseLong(checkInputValidPID(ValidateUtils.FIELD_PERSONALID, ValidateUtils.FIELD_PID_MESSAGE, ValidateUtils.REGEX_PERSONALID));
        String username = checkInputValidUser(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        String password1 = checkInputValid(ValidateUtils.FIELD_PASSWORD, ValidateUtils.FIELD_PASSWORD_MESSAGE, ValidateUtils.REGEX_PASSWORD);
        String password = PasswordUtils.generatePassword(password1);
        int age = Integer.parseInt(checkInputValid(ValidateUtils.FIELD_AGE, ValidateUtils.FIELD_AGE_MESSAGE, ValidateUtils.REGEX_AGE));
        LocalDate dob = DateUtils.parseDate(checkInputValid(ValidateUtils.FIELD_DOB, ValidateUtils.FIELD_DOB_MESSAGE, ValidateUtils.REGEX_DOB));
        long amountDeposit = Long.parseLong(checkInputValid(ValidateUtils.FIELD_BALANCE, ValidateUtils.FIELD_BALANCE_MESSAGE, ValidateUtils.REGEX_BALANCE));
        long balance = amountDeposit * BonusInterest;
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn giới tính:                ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Nam                       ║");
        System.out.println("║                 2.Nữ                        ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int idGender = ValidateUtils.getIntOfWithBounds(1, 2);
        EGender gender = EGender.findById(idGender);
        User user = new User(System.currentTimeMillis() % 100000, name, username,
                password, pid, age, dob, balance, gender, ERole.GUEST);
        TransactionHistory transactionHistory = new TransactionHistory(System.currentTimeMillis() % 100000, username, LocalDate.now(), amountDeposit);
        iTransactionHistory.createTH(transactionHistory);
        iUserService.createUser(user);
    }

    private void findGuest() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn tìm kiếm theo:        ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Tên.                      ║");
        System.out.println("║                 2.Tên đăng nhập.            ║");
        System.out.println("║                 3.Căn cước công dân.        ║");
        System.out.println("║                 4.Tuổi.                     ║");
        System.out.println("║                 5.Giới tính.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(1, 5);
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.GUEST))
                .collect(Collectors.toList());
        List<User> usersResult = null;
        switch (action) {
            case 1:
                usersResult = findGuestbyName(users1);
                break;
            case 2:
                usersResult = findGuestByUserName(users1);
                break;
            case 3:
                usersResult = findGuestByPid(users1);
                break;
            case 4:
                usersResult = findGuestByAge(users1);
                break;
            case 5:
                usersResult = findGuestByGender(users1);
                break;

        }

    }


    private List<User> findGuestbyName(List<User> users1) {
        String name = checkInputValidNameExist(ValidateUtils.FIELD_FULLNAME, ValidateUtils.FIELD_FULLNAME_MESSAGE, ValidateUtils.REGEX_FULLNAME);

        List<User> resultUser = users1.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findGuestByUserName(List<User> users1) {
        String name = checkInputValidUserExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);

        List<User> resultUser = users1.stream().filter(user -> user.getUsername().equals(name)).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findGuestByPid(List<User> users1) {
        long id = Long.parseLong(checkInputValidPIDExist(ValidateUtils.FIELD_PERSONALID, ValidateUtils.FIELD_PID_MESSAGE, ValidateUtils.REGEX_PERSONALID));
        List<User> resultUser = users1.stream().filter(user -> user.getIdentityCardId() == id).collect(Collectors.toList());
        return resultUser;
    }


    private List<User> findGuestByAge(List<User> users1) {
        System.out.println("Nhập tuổi");
        int age = Integer.parseInt(scanner.nextLine());
        List<User> resultUser = users1.stream().filter(user -> user.getAge() == age).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findGuestByGender(List<User> users1) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn giới tính:                ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Nam                       ║");
        System.out.println("║                 2.Nữ                        ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int id = ValidateUtils.getIntOfWithBounds(1, 2);
        EGender gender = EGender.findById(id);
        List<User> resultUser = users1.stream().filter(user -> user.getGender().equals(gender)).collect(Collectors.toList());
        return resultUser;
    }

    private void deleteGuest() {
        String name = checkInputValidUserExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        iUserService.deleteUser(name);
        showGuest();

    }

    private void sortGuest() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn kiểu sắp xếp:             ║");
        System.out.println("║                                             ║");
        System.out.println("║                1.Tăng dần.                  ║");
        System.out.println("║                2.Giảm dân.                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int select = ValidateUtils.getIntOfWithBounds(1, 2);
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.GUEST))
                .collect(Collectors.toList());
        Comparator<User> comparator = null;
        switch (select) {

            case 1:
                users1.sort(sortGuestDescending(comparator));
                break;

            case 2:
                users1.sort(sortGuestDecreasing(comparator));
                break;

        }
        showGuest(users1);


    }

    private Comparator<User> sortGuestDecreasing(Comparator<User> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về                    ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Tên đăng nhập.            ║");
        System.out.println("║                 4.Tuổi.                     ║");
        System.out.println("║                 5.Ngày sinh                 ║");
        System.out.println("║                 6.Giới tính.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 6);
        switch (choice) {
            case 0:
                sortGuest();
                break;
            case 1:
                comparator = Comparator.comparing(User::getId).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(User::getName).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(User::getUsername).reversed();
                break;
            case 4:
                comparator = Comparator.comparing(User::getAge).reversed();
                break;
            case 5:
                comparator = Comparator.comparing(User::getDob).reversed();
                break;
            case 6:
                comparator = Comparator.comparing(User::getGender).reversed();
                break;
        }
        return comparator;

    }

    private Comparator<User> sortGuestDescending(Comparator<User> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về                    ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Tên đăng nhập.            ║");
        System.out.println("║                 4.Tuổi.                     ║");
        System.out.println("║                 5.Ngày sinh                 ║");
        System.out.println("║                 6.Giới tính.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 6);
        switch (choice) {
            case 0:
                sortGuest();
                break;
            case 1:
                comparator = Comparator.comparing(User::getId);
                break;
            case 2:
                comparator = Comparator.comparing(User::getName);
                break;
            case 3:
                comparator = Comparator.comparing(User::getUsername);
                break;
            case 4:
                comparator = Comparator.comparing(User::getAge);
                break;
            case 5:
                comparator = Comparator.comparing(User::getDob);
                break;
            case 6:
                comparator = Comparator.comparing(User::getGender);
                break;
        }
        return comparator;
    }

    private void depositBalanceGuest() {

        showGuest();

        String username = checkInputValidUserExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        long deposit = Long.parseLong(checkInputValid(ValidateUtils.FIELD_DEPOSIT, ValidateUtils.FIELD_BALANCE_MESSAGE, ValidateUtils.REGEX_BALANCE));
        long balance = (long) (deposit * BonusInterest);
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.GUEST))
                .collect(Collectors.toList());
        User userResult = users1.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
        userResult.setBalance(userResult.getBalance() + balance);
        System.out.println("Nạp tiền thành công");
        iUserService.updateBalance(userResult.getBalance(), userResult);
        TransactionHistory transactionHistory = new TransactionHistory(System.currentTimeMillis() % 100000, userResult.getUsername(), LocalDate.now(), deposit);
        iTransactionHistory.createTH(transactionHistory);


    }

    private void controlBonusBalanceInterest() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Nhập các mức khuyến mãi :      ║");
        System.out.println("║                                             ║");
        System.out.println("║            1. Mức khuyến mãi là 0%.         ║");
        System.out.println("║            2. Mức khuyến mãi là 100%.       ║");
        System.out.println("║            3. Mức khuyến mãi là 200%.       ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(1, 3);
        switch (choice) {
            case 1:
                setBonusInterest(1);
                break;
            case 2:
                setBonusInterest(2);
                break;
            case 3:
                setBonusInterest(3);
                break;
        }

    }

    public static void main(String[] args) {
        GuestControl guestControl = new GuestControl();
        guestControl.showGuest();

    }

    private String checkInputValidUser(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<User> users = iUserService.getAllUsers();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && checkUser(input)) {
                System.out.println("Tên đăng nhập đã tồn tại, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidUserExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<User> users = iUserService.getAllUsers();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkUser(input)) {
                    System.out.println("Không tìm thấy tên đăng nhập, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    guestControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    guestControlViewByEmployee();
                }
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidPID(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<User> users = iUserService.getAllUsers();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && checkPid(input)) {
                    System.out.println("Mã căn cước đã tồn tại, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    guestControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    guestControlViewByEmployee();
                }
            }
        } while (validateInput);
        return input;
    }

    private String checkInputValidPIDExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<User> users = iUserService.getAllUsers();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkPid(input)) {
                    System.out.println("Không tìm thấy mã căn cước, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    guestControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    guestControlViewByEmployee();
                }
            }
        } while (validateInput);
        return input;
    }

    private String checkInputValidNameExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<User> users = iUserService.getAllUsers();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkName(input)) {
                    System.out.println("Không tìm thấy họ tên, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    guestControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    guestControlViewByEmployee();
                }
            }


        } while (validateInput);
        return input;
    }


    private boolean checkUser(String userName) {
        List<User> users = iUserService.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPid(String pid) {
        List<User> users = iUserService.getAllUsers();
        Long.parseLong(pid);
        for (User u : users) {
            if (u.getIdentityCardId() == Long.parseLong(pid)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkName(String name) {
        List<User> users = iUserService.getAllUsers();
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAge(String age) {
        List<User> users = iUserService.getAllUsers();
        for (User u : users) {
            if (u.getAge() == Integer.parseInt(age)) {
                return true;
            }
        }
        return false;
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
}
