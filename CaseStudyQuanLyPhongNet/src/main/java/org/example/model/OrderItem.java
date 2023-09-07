package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements IParseModel<OrderItem>{
    private long id;
    private long idOrder;
    private long idProduct;
    private int quantity;
    private long price;
    @Override
    public OrderItem parse(String line) {
        String[] items = line.split(",");

        OrderItem orderItem = null;
        //public OrderItem(long id,long idOrder,long idProduct,int quantity,double price )
        try {
            orderItem = new OrderItem(Long.parseLong(items[0]), Long.parseLong(items[1]),
                    Long.parseLong(items[2]), Integer.parseInt(items[3]), Long.parseLong(items[4]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public String toString() {
        //15045,10044,20045,1,8000.0
        return String.format("%s,%s,%s,%s,%s", this.id, this.idProduct, this.idOrder, this.quantity, this.price);
    }
}
