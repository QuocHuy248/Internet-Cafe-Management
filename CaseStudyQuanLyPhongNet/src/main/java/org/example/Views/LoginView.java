package org.example.Views;

import org.example.Service.IUserService;
import org.example.Service.UserService;
import org.example.Utils.ValidateUtils;

import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);



    public void launcher() {
        AdminView adminView= new AdminView();
        UserView userView= new UserView();
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║             Chọn vai trò đăng nhập.         ║");
        System.out.println("║                                             ║");
        System.out.println("║                  1. Admin.                  ║");
        System.out.println("║                  2. User.                   ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int action = ValidateUtils.getIntOfWithBounds(1,2);
        switch (action) {
            case 1:
                adminView.adminLogin();
                break;

            case 2:
                userView.userLauncher();
                break;
        }

    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.launcher();
    }


}

