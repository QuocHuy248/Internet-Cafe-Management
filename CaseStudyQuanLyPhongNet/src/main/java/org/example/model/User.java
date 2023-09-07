package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Utils.DateUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class User implements IParseModel<User>{
    public void setBalance(long balance) {
        this.balance = balance;
    }

    private long id;
    private String name;
    private String username;
    private String password;
    private long identityCardId;
    private int age;
    private LocalDate dob;
    private long balance;
    private EGender gender;
    private ERole role;
    public User(long id, String name, String username, String password
            ,long identityCardId, int age, LocalDate dob, long balance
            ,EGender gender, ERole role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.identityCardId = identityCardId;
        this.age = age;
        this.dob = dob;
        this.balance = balance;
        this.gender = gender;
        this.role = role;
    }

    //    private long id;
//    private String name;
//    private String username;
//    private String password;
//    private long identityCardId;
//    private int age;
//    private LocalDate dob;
//    private double balance;
//    private EGender gender;
//    private ERole role;
    @Override
    public User parse(String line) {
        User u=null;
//12020,Lê Quốc Huy,admin,$2a$12$IkSTiBCaO9k/s38obmfpCegZl829mELqygWx6MvgP1rz5E0R0e22y,046022057984,25,24-08-1999,0.0,MALE,ADMIN
        String[] items = line.split(",");
        try {
         u = new User(Long.parseLong(items[0]),
                 items[1], items[2],items[3],Long.parseLong(items[4]),
                 Integer.parseInt(items[5]), DateUtils.parseDate(items[6]),
                 Long.parseLong(items[7]), EGender.valueOf(items[8]),ERole.valueOf(items[9]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                this.id, this.name,this.username,this.password,
                this.identityCardId ,this.age, DateUtils.formatDate(this.dob),
                this.balance, this.gender,this.role);
    }
}
