package org.example.Service;

import org.example.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();

    Order findOrder(long id);

    void updateOrder(long id, Order order);

    void deleteOrder(long id);

    void createOrder(Order Order);

    long calculateTotalRevenue(List<Order> orders);

    List<Order> getOrderBetWeen(LocalDate startDate, LocalDate endDate);


}
