package org.example.Control;

import org.example.Service.*;
import org.example.Utils.DateUtils;
import org.example.Utils.PasswordUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.model.Computer;
import org.example.model.EGender;
import org.example.model.ERole;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EmployeeControl {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;


    public EmployeeControl() {
        iUserService = new UserService();

    }

    public void employeeControlView() {

        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý tài khoản nhân viên     ║");
        System.out.println("║                                                ║");
        System.out.println("║          0.Trở về.                             ║");
        System.out.println("║          1.Xem danh sách                       ║");
        System.out.println("║          2.Đăng ký tài khoản nhân viên         ║");
        System.out.println("║          3.Tìm kiếm nhân viên                  ║");
        System.out.println("║          4.Xóa tài khoản nhân viên             ║");
        System.out.println("║          5.Sắp xếp tài khoản nhân viên         ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 5);
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.userControl();
                break;
            case 1:
                showEmployee();
                employeeControlView();
                break;
            case 2:
                addEmployee();
                showEmployee();
                employeeControlView();
                break;
            case 3:
                findEmployee();
                employeeControlView();
                break;
            case 4:
                deleteEmployee();
                employeeControlView();
                break;
            case 5:
                sortEmployee();
                employeeControlView();
                break;

        }
    }

    private void showEmployee() {
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.EMPLOYEE))
                .collect(Collectors.toList());
        System.out.printf("╔════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %8s ║\n",
                "ID", "Name", "Username", "Identity Id", "AGE", "DOB", "GENDER", "ROLE");
        System.out.printf("║════════════════════════════════════════════════════════════════════════════════════════════════════║\n");

        for (User user : users1) {
            System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %8s ║\n",
                    user.getId(), user.getName(), user.getUsername(), user.getIdentityCardId()
                    , user.getAge(),DateUtils.formatDate(user.getDob()), user.getGender(), user.getRole());
        }
        System.out.printf("╚════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

    }

    private void showEmployee(List<User> users) {
        System.out.printf("╔════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %8s ║\n",
                "ID", "Name", "Username", "Identity Id", "AGE", "DOB", "GENDER", "ROLE");
        System.out.printf("║════════════════════════════════════════════════════════════════════════════════════════════════════║\n");

        for (User user : users) {
            System.out.printf("║%7s ║ %20s ║ %13s ║ %15s ║ %6s ║ %13s║ %8s ║\n",
                    user.getId(), user.getName(), user.getUsername(), user.getIdentityCardId()
                    , user.getAge(), DateUtils.formatDate(user.getDob()), user.getGender(), user.getRole());
        }
        System.out.printf("╚════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

        }


    private void addEmployee() {
        String name = checkInputValid(ValidateUtils.FIELD_FULLNAME, ValidateUtils.FIELD_FULLNAME_MESSAGE, ValidateUtils.REGEX_FULLNAME);
        long pid = Long.parseLong(checkInputValidPID(ValidateUtils.FIELD_PERSONALID, ValidateUtils.FIELD_PID_MESSAGE, ValidateUtils.REGEX_PERSONALID));
        String username = checkInputValidUser(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        String password1 = checkInputValid(ValidateUtils.FIELD_PASSWORD, ValidateUtils.FIELD_PASSWORD_MESSAGE, ValidateUtils.REGEX_PASSWORD);
        String password = PasswordUtils.generatePassword(password1);
        int age = Integer.parseInt(checkInputValid(ValidateUtils.FIELD_AGE, ValidateUtils.FIELD_AGE_MESSAGE, ValidateUtils.REGEX_AGE));
        LocalDate dob = DateUtils.parseDate(checkInputValid(ValidateUtils.FIELD_DOB, ValidateUtils.FIELD_DOB_MESSAGE, ValidateUtils.REGEX_DOB));

        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Nhập giới tính:                ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Nam                       ║");
        System.out.println("║                 2.Nữ                        ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int idGender = ValidateUtils.getIntOfWithBounds(1, 2);
        EGender gender = EGender.findById(idGender);
        User user = new User(System.currentTimeMillis() % 100000, name, username,
                password, pid, age, dob, 0, gender, ERole.EMPLOYEE);
        iUserService.createUser(user);


    }

    private void findEmployee() {
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
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.EMPLOYEE))
                .collect(Collectors.toList());
        List<User> usersResult = null;
        int action = ValidateUtils.getIntOfWithBounds(1, 5);
        switch (action) {
            case 1:
                usersResult = findEmployeebyName(users1);
                break;
            case 2:
                usersResult = findEmployeeByUserName(users1);
                break;
            case 3:
                usersResult = findEmployeeByPid(users1);
                break;
            case 4:
                usersResult = findEmployeeByAge(users1);
                break;
            case 5:
                usersResult = findEmployeeByGender(users1);
                break;

        }
        showEmployee(usersResult);

    }

    private List<User> findEmployeebyName(List<User> users1) {

        String name = checkInputValidNameExist(ValidateUtils.FIELD_FULLNAME, ValidateUtils.FIELD_FULLNAME_MESSAGE, ValidateUtils.REGEX_FULLNAME);
        List<User> resultUser = users1.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        return resultUser;

    }

    private List<User> findEmployeeByUserName(List<User> users1) {
        String name = checkInputValidUserExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        List<User> resultUser = users1.stream().filter(user -> user.getUsername().equals(name)).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findEmployeeByPid(List<User> users1) {
        long id = Long.parseLong(checkInputValidPIDExist(ValidateUtils.FIELD_PERSONALID, ValidateUtils.FIELD_PID_MESSAGE, ValidateUtils.REGEX_PERSONALID));
        List<User> resultUser = users1.stream().filter(user -> user.getIdentityCardId() == id).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findEmployeeByAge(List<User> users1) {

        int age = Integer.parseInt(checkInputValidAgeExist(ValidateUtils.FIELD_AGE, ValidateUtils.FIELD_AGE_MESSAGE, ValidateUtils.REGEX_AGE));
        List<User> resultUser = users1.stream().filter(user -> user.getAge() == age).collect(Collectors.toList());
        return resultUser;
    }

    private List<User> findEmployeeByGender(List<User> users1) {
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

    private void deleteEmployee() {
        System.out.println("Nhập username cần xóa");
        String name = checkInputValidUserExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        iUserService.deleteUser(name);
        showEmployee();

    }

    private void sortEmployee() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn kiểu sắp xếp:             ║");
        System.out.println("║                                             ║");
        System.out.println("║                1.Tăng dần.                  ║");
        System.out.println("║                2.Giảm dân.                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int select = ValidateUtils.getIntOfWithBounds(1, 2);
        List<User> users = iUserService.getAllUsers();
        List<User> users1 = users.stream().filter(user -> user.getRole().equals(ERole.EMPLOYEE))
                .collect(Collectors.toList());
        Comparator<User> comparator = null;
        switch (select) {
            case 1:
                users1.sort(sortEmployeeDescending(comparator));
                break;

            case 2:
                users1.sort(sortEmployeeDecreasing(comparator));
                break;

        }
        showEmployee(users1);


    }

    private Comparator<User> sortEmployeeDecreasing(Comparator<User> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Tên đăng nhập.            ║");
        System.out.println("║                 4.Tuổi.                     ║");
        System.out.println("║                 5.Ngày sinh                 ║");
        System.out.println("║                 6.Giới tính.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int choice = ValidateUtils.getIntOfWithBounds(1, 6);
        switch (choice) {
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

    public Comparator<User> sortEmployeeDescending(Comparator<User> comparator) {

        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.ID                        ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Tên đăng nhập.            ║");
        System.out.println("║                 4.Tuổi.                     ║");
        System.out.println("║                 5.Ngày sinh                 ║");
        System.out.println("║                 6.Giới tính.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(1, 6);
        switch (choice) {
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

    public static void main(String[] args) {
        EmployeeControl employeeControl = new EmployeeControl();
        employeeControl.showEmployee();

    }

    private String checkInputValidUser(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
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
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkUser(input)) {
                System.out.println("Không tìm thấy tên đăng nhập, vui lòng nhập lại");
                validateInput = true;
            } else if(ValidateUtils.isValid(fieldPattern, input) && checkUser(input)){
                validateInput = false;}


        } while (validateInput);
        return input;
    }

    private String checkInputValidPID(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && checkPid(input)) {
                System.out.println("Mã căn cước đã tồn tại, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidPIDExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkPid(input)) {
                System.out.println("Không tìm thấy mã căn cước, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidNameExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkName(input)) {
                System.out.println("Không tìm thấy họ tên, vui lòng nhập lại");
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && checkName(input)) {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidAgeExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkAge(input)) {
                System.out.println("Không tìm thấy tuổi, vui lòng nhập lại ");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private boolean checkUser(String UserName) {
        List<User> users = iUserService.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(UserName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPid(String pid) {
        List<User> users = iUserService.getAllUsers();
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
