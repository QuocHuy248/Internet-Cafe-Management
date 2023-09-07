package org.example.Utils;

import lombok.Getter;
import org.example.model.Computer;
import org.example.model.ERole;
import org.example.model.User;

import javax.jws.soap.SOAPBinding;

public class AuthUtils {
    @Getter
    private static User user;
    @Getter
    private static Computer computer;

    public static void setComputerAuthentication(Computer computer){AuthUtils.computer=computer;}

    public static void setUserAuthentication(User user) {
        AuthUtils.user = user;
    }

    public static boolean hasRole(ERole role){
        return user.getRole().equals(role);
    }
}
