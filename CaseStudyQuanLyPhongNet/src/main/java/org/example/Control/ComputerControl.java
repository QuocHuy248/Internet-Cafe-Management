package org.example.Control;

import org.example.Service.ComputerService;
import org.example.Service.IComputerService;
import org.example.Service.IUserService;
import org.example.Service.UserService;
import org.example.Utils.AuthUtils;
import org.example.Utils.TimeUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.Views.EmployeeView;
import org.example.model.Computer;
import org.example.model.ERole;
import org.example.model.EStatusComputer;
import org.example.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ComputerControl {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;
    private IComputerService icomputerService;

    public ComputerControl() {
        iUserService = new UserService();
        icomputerService = new ComputerService();
    }

    public void computerControlView() {
        showComputer();
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý máy trạm.               ║");
        System.out.println("║                                                ║");
        System.out.println("║     0.Trở về.                                  ║");
        System.out.println("║     1.Hiển thị máy theo trạng thái.            ║");
        System.out.println("║     2.Thêm máy.                                ║");
        System.out.println("║     3.Cập nhật tên/trạng thái máy.             ║");
        System.out.println("║     4.Xóa máy.                                 ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 4);
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.adminWorkView();
                break;
            case 1:
                showComputerByStatus();
                computerControlView();
                break;
            case 2:
                addComputer();
                computerControlView();
                break;
            case 3:
                upDateComputer();
                computerControlView();
                break;
            case 4:
                deleteComputer();
                computerControlView();
                break;
        }
    }

    public void computerControlViewByEmployee() {
        showComputer();
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý máy trạm.               ║");
        System.out.println("║                                                ║");
        System.out.println("║     0.Trở về.                                  ║");
        System.out.println("║     1.Hiển thị máy theo trạng thái.            ║");
        System.out.println("║     2.Thêm máy.                                ║");
        System.out.println("║     3.Cập nhật tên/trạng thái máy.             ║");
        System.out.println("║     4.Xóa máy.                                 ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 4);
        switch (action) {
            case 0:
                EmployeeView employeeView = new EmployeeView();
                employeeView.employeeLauncher();
                break;
            case 1:
                showComputerByStatus();
                computerControlViewByEmployee();
                break;
            case 2:
                addComputer();
                computerControlViewByEmployee();
                break;
            case 3:
                upDateComputer();
                computerControlViewByEmployee();
                break;
            case 4:
                deleteComputer();
                computerControlViewByEmployee();
                break;
        }
    }

    private void deleteComputer() {
        String name =
                checkInputValid(ValidateUtils.FIELD_COMPUTER,
                        ValidateUtils.FIELD_COMPUTER_MESSAGE,
                        ValidateUtils.REGEX_COMPUTER);
        List<Computer> computers = icomputerService.getAllComputer();
        icomputerService.deleteComputer(computers.stream().filter(computer -> computer.getName().equals(name)).findFirst().get().getId());
        showComputer();
    }

    private void upDateComputer() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu cập nhật máy tính.              ║");
        System.out.println("║                                                ║");
        System.out.println("║     0.Trở về.                                  ║");
        System.out.println("║     1.Cập nhật tên máy .                       ║");
        System.out.println("║     2.Cập nhật trạng thái của máy.             ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        int action = ValidateUtils.getIntOfWithBounds(0, 2);
        switch (action) {
            case 0:
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    computerControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    computerControlViewByEmployee();
                }
                break;
            case 1:
                updateComputerName();
                upDateComputer();
                break;
            case 2:
                updateComputerStatus();
                upDateComputer();
                break;
        }

    }

    private void updateComputerStatus() {
        String nameComputer =
                checkInputValid(ValidateUtils.FIELD_COMPUTER,
                        ValidateUtils.FIELD_COMPUTER_MESSAGE,
                        ValidateUtils.REGEX_COMPUTER);
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Cập nhập trạng thái cho máy tính.    ║");
        System.out.println("║                                                ║");
        System.out.println("║            1. Đang sử dụng.                    ║");
        System.out.println("║            2. Đang bảo trì.                    ║");
        System.out.println("║            3. Sẵn sàng.                        ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int idStatus = ValidateUtils.getIntOfWithBounds(1, 3);
        icomputerService.updateComputerStatus(nameComputer, idStatus);
    }

    private void updateComputerName() {
        String nameComputer =
                checkInputValid(ValidateUtils.FIELD_COMPUTER,
                        ValidateUtils.FIELD_COMPUTER_MESSAGE,
                        ValidateUtils.REGEX_COMPUTER);

        String name = checkInputValidNewComputer(ValidateUtils.FIELD_NEW_COMPUTER,
                ValidateUtils.FIELD_COMPUTER_MESSAGE,
                ValidateUtils.REGEX_COMPUTER);
        icomputerService.updateComputerName(nameComputer, name);
        showComputer();
    }

    private void addComputer() {
        String name = checkInputValidNewComputer(ValidateUtils.FIELD_NEW_COMPUTER,
                ValidateUtils.FIELD_COMPUTER_MESSAGE,
                ValidateUtils.REGEX_COMPUTER);

        Computer computer = new Computer(System.currentTimeMillis() % 100000, name, EStatusComputer.Ready, name, TimeUtils.parseTime("00:00"));
        icomputerService.createComputer(computer);
        showComputer();
    }

    private void showComputerByStatus() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Menu hiển thị máy theo trạng thái hoạt động. ║");
        System.out.println("║                                                ║");
        System.out.println("║        0. Trở về.                              ║");
        System.out.println("║        1. Hiển thị máy đang bảo trì.           ║");
        System.out.println("║        2. Hiển thị máy đang được sử dụng.      ║");
        System.out.println("║        3. Hiển thị máy chưa có người sử dụng.  ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 3);
        List<Computer> computers = icomputerService.getAllComputer();

        switch (action) {
            case 0:
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    computerControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    computerControlViewByEmployee();
                }
                break;
            case 1:
                showAllComputerByStatus(computers, EStatusComputer.UnderMaintenance);
                break;
            case 2:
                showAllComputerByStatus(computers, EStatusComputer.InUse);
                break;
            case 3:
                showAllComputerByStatus(computers, EStatusComputer.Ready);
                break;

        }
    }

    private void showAllComputerByStatus(List<Computer> computers, EStatusComputer eStatusComputer) {
        List<Computer> Result = computers.stream().filter(computer -> computer.getStatusComputer().equals(eStatusComputer)).collect(Collectors.toList());
        showComputer(Result);
    }


    ;

    private void showComputer() {
        List<Computer> computers = icomputerService.getAllComputer();
        System.out.printf("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%5s ║ %15s ║ %20s ║ %15s ║ %-10s ║\n", "ID",
                "COMPUTER NAME", "COMPUTER STATUS", "USER NAME", "TIME START");
        System.out.printf("║══════════════════════════════════════════════════════════════════════════════║\n");

        for (Computer computer : computers) {
            System.out.printf("║%5s ║ %15s ║ %20s ║ %15s ║ %-10s ║\n", computer.getId(), computer.getName()
                    , computer.getStatusComputer()
                    , computer.getUsername(), computer.getStartUsing());
        }
        System.out.printf("╚══════════════════════════════════════════════════════════════════════════════╝\n");

    }

    public void showComputer(List<Computer> computers) {

        System.out.printf("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%5s ║ %15s ║ %20s ║ %15s ║ %-10s ║\n", "ID",
                "COMPUTER NAME", "COMPUTER STATUS", "USER NAME", "TIME START");
        System.out.printf("║══════════════════════════════════════════════════════════════════════════════║\n");

        for (Computer computer : computers) {
            System.out.printf("║%5s ║ %15s ║ %20s ║ %15s ║ %-10s ║\n", computer.getId(), computer.getName()
                    , computer.getStatusComputer()
                    , computer.getUsername(), computer.getStartUsing());
        }
        System.out.printf("╚══════════════════════════════════════════════════════════════════════════════╝\n");
    }

    ;


    public List<Computer> updateBalance() {
        LocalTime time = LocalTime.now();
        String timeNow = TimeUtils.formatTime(time);
        LocalTime timeResult = TimeUtils.parseTime(timeNow);
        List<User> users = iUserService.getAllUsers();
        List<Computer> computers = icomputerService.getAllComputer();
        List<Computer> computerUsing = computers.stream().filter(computer -> computer
                .getStatusComputer().equals(EStatusComputer.InUse)).collect(Collectors.toList());
//        List<String> userUsing = new ArrayList<>();
//        computerUsing.stream().forEach(computer -> userUsing.add(computer.getName()));
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < computerUsing.size(); j++) {
                if (users.get(i).getUsername().equals(computerUsing.get(j).getUsername())) {
                    users.get(i).setBalance(calculateBalance(computerUsing.get(j).getStartUsing(), timeResult,
                            users.get(i).getBalance()));
                    iUserService.updateBalance(users.get(i).getBalance(), users.get(i));
                    if (users.get(i).getBalance() == 0) {
                        computerUsing.get(j).setStatusComputer(EStatusComputer.Ready);
                        computerUsing.get(j).setStartUsing(TimeUtils.parseTime("00:00"));
                        computerUsing.get(j).setUsername(computerUsing.get(j).getName());
                    }
                }
            }
        }
        computerUsing.stream().forEach(computer -> icomputerService.
                updateComputer(computer.getName(), computer));
        return computers;
    }

    private long calculateBalance(LocalTime startime, LocalTime now, long balance) {
        long pricePerMinute = Computer.getPrice() / 60;
        Duration duration = Duration.between(startime, now);
        long minute = duration.toMinutes();
        long resultbalance = balance - pricePerMinute * minute;
        if (resultbalance < 0) {
            return 0;
        } else return resultbalance;
    }

    private String checkInputValid(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<Computer> computers = icomputerService.getAllComputer();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkComputer(input)) {
                    System.out.println("Không tìm thấy máy, vui lòng nhập lại tên máy");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    computerControlView();
                }
                if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    computerControlViewByEmployee();
                }
            }

        } while (validateInput);
        return input;
    }

    private boolean checkComputer(String computerName) {
        List<Computer> computers = icomputerService.getAllComputer();
        for (Computer computer : computers) {
            if (computer.getName().equals(computerName)) {
                return true;
            }
        }
        return false;
    }

    private String checkInputValidNewComputer(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            List<Computer> computers = icomputerService.getAllComputer();
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && checkComputer(input)) {
                System.out.println("Tên máy đã tồn tại, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    public static void main(String[] args) {
        ComputerControl computerControl = new ComputerControl();
        computerControl.showComputer();
    }
}
