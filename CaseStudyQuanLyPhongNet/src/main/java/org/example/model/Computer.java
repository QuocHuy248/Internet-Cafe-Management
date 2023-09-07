package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Utils.TimeUtils;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class Computer implements IParseModel<Computer> {
    private  long id;
    private String name;
    EStatusComputer statusComputer;
    private String username;
    private LocalTime startUsing;
    private static long pricePerHour = 10000;

    public Computer parse(String line) {
        String[] items = line.split(",");
        Computer p = new Computer(Long.parseLong(items[0]), items[1], EStatusComputer.valueOf(items[2]), items[3], TimeUtils.parseTime(items[4]));

        return p;

    }

    public Computer( Long id ,String name, EStatusComputer statusComputer, String username, LocalTime startUsing) {
    this.id =id;
        this.name = name;
        this.statusComputer = statusComputer;
        this.username = username;
        this.startUsing = startUsing;
    }

    public Computer( String name, EStatusComputer statusComputer) {

        this.name = name;
        this.statusComputer = statusComputer;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s",this.id, this.name, this.statusComputer, this.username, TimeUtils.formatTime(this.startUsing));
    }
    public static void setPrice(long newPrice) {
        pricePerHour = newPrice;
    }

    public static long getPrice() {
        return pricePerHour;
    }


}
