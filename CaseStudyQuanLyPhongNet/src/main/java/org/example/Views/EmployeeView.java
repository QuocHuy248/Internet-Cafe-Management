package org.example.Views;

import org.example.Control.ComputerControl;
import org.example.Control.GuestControl;
import org.example.Control.OrderControl;
import org.example.Utils.ValidateUtils;

import java.util.Scanner;

public class EmployeeView {
    private Scanner scanner = new Scanner(System.in);

    public void employeeLauncher() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║          Trang quản lý của Nhân viên.       ║");
        System.out.println("║                                             ║");
        System.out.println("║            0.Thoát.                         ║");
        System.out.println("║            1.Quản lý đơn hàng.              ║");
        System.out.println("║            2.Quản lý tài khoản thành viên.  ║");
        System.out.println("║            3.Quản lý máy trạm.              ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int choice = ValidateUtils.getIntOfWithBounds(0,3);
        switch (choice) {
            case 0:LoginView loginView = new LoginView();
            loginView.launcher();
                break;
            case 1:
                OrderControl orderControl= new OrderControl();
                orderControl.orderControlViewByEmployee();
                break;
            case 2:
                GuestControl guestControl = new GuestControl();
                guestControl.guestControlViewByEmployee();
                break;
            case 3:
                ComputerControl computerControl= new ComputerControl();
                computerControl.computerControlViewByEmployee();
                break;
        }
    }
}
