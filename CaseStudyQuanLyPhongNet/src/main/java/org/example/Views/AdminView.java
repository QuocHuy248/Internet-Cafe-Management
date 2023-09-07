package org.example.Views;

import org.example.Control.*;
import org.example.Service.IUserService;
import org.example.Service.UserService;
import org.example.Utils.AuthUtils;
import org.example.Utils.ValidateUtils;
import org.example.model.ERole;
import org.example.model.User;

import java.util.Scanner;

public class AdminView {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;

    public AdminView() {
        iUserService = new UserService();
    }

    public void adminLogin() {
        LoginView loginView = new LoginView();

        EmployeeView employeeView = new EmployeeView();
        System.out.println("Nhập username:");
        String username = scanner.nextLine();
        System.out.println("Nhập password:");
        String password = scanner.nextLine();
        if (iUserService.checkUserNamePassword(username, password)) {
            User user = iUserService.findUser(username);
            AuthUtils.setUserAuthentication(user);
            if (user.getRole().equals(ERole.ADMIN)) {
                adminWorkView();
            } else if (user.getRole().equals(ERole.EMPLOYEE)) {
                employeeView.employeeLauncher();
            } else if (user.getRole().equals(ERole.GUEST)) {
                System.out.println("Bạn đã chọn sai vai trò, vui lòng chọn lại. ");
                loginView.launcher();
            }
        } else {
            System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║   Tài khoản và mật khẩu không chính xác.    ║");
            System.out.println("╚═════════════════════════════════════════════╝");
            System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║           Bạn có muốn nhập lại không.       ║");
            System.out.println("║                                             ║");
            System.out.println("║            1. Có.                           ║");
            System.out.println("║            2. Trở về màn hình chọn vai trò. ║");
            System.out.println("║                                             ║");
            System.out.println("╚═════════════════════════════════════════════╝");
            int action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    loginView.launcher();
            }
        }
    }

    public void adminWorkView() {
        LoginView loginView = new LoginView();
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║           Trang quản lý của Admin.          ║");
        System.out.println("║                                             ║");
        System.out.println("║            0.Thoát.                         ║");
        System.out.println("║            1.Quản lý tài khoản.             ║");
        System.out.println("║            2.Quản lý máy trạm.              ║");
        System.out.println("║            3.Quản lý đơn hàng.              ║");
        System.out.println("║            4.Quản lý sản phẩm.              ║");
        System.out.println("║            5.Quản lý doanh thu.             ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0,5);
        switch (action) {
            case 0:
                loginView.launcher();
                break;
            case 1:
                userControl();
                break;
            case 2:
                ComputerControl computerControl = new ComputerControl();
                computerControl.computerControlView();
                break;
            case 3:
                OrderControl orderControl = new OrderControl();
                orderControl.orderControlView();
                break;
            case 4:
                ProductControl productControl = new ProductControl();
                productControl.productControlView();
                break;
            case 5:
                RevenueControl revenueControl = new RevenueControl();
                revenueControl.totalRevenueControl();
                break;

        }
    }

    public void userControl() {
        System.out.println("Menu quản lý User");
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Menu quản lý User.             ║");
        System.out.println("║                                             ║");
        System.out.println("║            0. Trở về.                       ║");
        System.out.println("║            1. Quản lý tài khoản nhân viên.  ║");
        System.out.println("║            2. Quản lý tài khoản khách hàng. ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int action = ValidateUtils.getIntOfWithBounds(0,2);
        switch (action) {
            case 0:
                adminWorkView();
                break;
            case 1:
                EmployeeControl employeeControl = new EmployeeControl();
                employeeControl.employeeControlView();
                break;
            case 2:
                GuestControl guestControl = new GuestControl();
                guestControl.guestControlView();
                break;
        }
    }
}
