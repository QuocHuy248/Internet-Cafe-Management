package org.example.Service;

import org.example.Utils.FileUtils;
import org.example.model.Order;
import org.example.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemService implements IOrderItemService{
    private final String fileOrderItem="./data/order_details.txt";
    @Override
    public OrderItem findOrderItem(long id) {
        List<OrderItem> orderItem = getAllOrderItems();
        OrderItem ot = orderItem.stream().filter(orderItem1 -> orderItem1.getId() == id).findFirst().orElseThrow(null);
        return ot;
    }

    @Override
    public void updateOrderItem(long id, OrderItem OrderItem) {
        List<OrderItem> orderItems = getAllOrderItems();
        for (OrderItem ot : orderItems) {
            if (ot.getId() == id) {
                ot.setIdOrder(OrderItem.getIdOrder());
                ot.setPrice(OrderItem.getPrice());
                ot.setQuantity(OrderItem.getQuantity());
                ot.setIdProduct(OrderItem.getIdProduct());

            }
        }
        FileUtils.writeData(fileOrderItem, orderItems);
    }

    @Override
    public void deleteOrderItem(long id) {
        List<OrderItem> orderItems = getAllOrderItems();
        orderItems.remove(orderItems.stream().filter(orderItem -> orderItem.getId() == id).findFirst().get());
        FileUtils.writeData(fileOrderItem, orderItems);

    }

    @Override
    public void createOrderItem(OrderItem OrderItem) {
        List<OrderItem> orderItems = getAllOrderItems();
        orderItems.add(OrderItem);
        FileUtils.writeData(fileOrderItem, orderItems);
    }




    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItems = FileUtils.readData(fileOrderItem, OrderItem.class);
        return orderItems;
    }

    @Override
    public List<OrderItem> getAllOrderItemsByOrderId(long idOrder) {
        List<OrderItem> orderItems = getAllOrderItems();
        List<OrderItem> result = orderItems.stream()
                .filter(orderItem -> orderItem.getIdOrder() == idOrder)
                .collect(Collectors.toList());
        return result;
    }
}
