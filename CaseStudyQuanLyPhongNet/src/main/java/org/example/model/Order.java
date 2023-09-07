package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order implements IParseModel<Order>{
    private long id;
    private LocalDate createAt;
    private long total;
    private String username;
    List<OrderItem> orderItems;
    @Override
    public Order parse(String line) {
        Order order = null;
        String[] items = line.split(",");
        try {
            order = new Order(Long.parseLong(items[0]), DateUtils.parseDate(items[1]), Long.parseLong(items[2]),
                    items[3],null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public void setTotalPrice() {
        long total = 0;
        if (this.getOrderItems() != null) {
            for (OrderItem ot : this.getOrderItems()) {
                total += ot.getQuantity() * ot.getPrice();
            }
        }
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.id, DateUtils.formatDate(this.createAt), this.total,
                 this.username);

    }

}
