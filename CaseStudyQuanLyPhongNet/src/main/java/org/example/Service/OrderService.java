package org.example.Service;

import org.example.Utils.FileUtils;
import org.example.model.Order;
import org.example.model.OrderItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    private final String fileIOrder = "./data/orders.txt";
    private IOrderItemService iOrderItemService;
    private IProductService iProductService;

    public OrderService() {
        iOrderItemService = new OrderItemService();
        iProductService = new ProductService();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = FileUtils.readData(fileIOrder, Order.class);
        orders.stream().forEach(order -> {
            List<OrderItem> orderItems = iOrderItemService.getAllOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        });
        return orders;
    }

    @Override
    public Order findOrder(long id) {
        List<Order> orders = getAllOrders();
        Order o = orders.stream().filter(order -> order.getId() == id).findFirst().orElseThrow(null);
        return o;
    }

    @Override
    public void updateOrder(long id, Order order) {
        List<Order> orders = getAllOrders();
        for (Order o : orders) {
            if (o.getId() == id) {
                o.setOrderItems(order.getOrderItems());
                o.setUsername(order.getUsername());
                o.setTotal(order.getTotal());
                o.setCreateAt(order.getCreateAt());
            }
        }
    }

    @Override
    public void deleteOrder(long id) {
        List<Order> orders = getAllOrders();
        orders.remove(orders.stream().filter(order -> order.getId() == id).findFirst().get());
        FileUtils.writeData(fileIOrder, orders);
    }

    @Override
    public void createOrder(Order Order) {
        List<Order> orders = getAllOrders();
        orders.add(Order);
        FileUtils.writeData(fileIOrder, orders);
        for (OrderItem ot : Order.getOrderItems()) {
            iOrderItemService.createOrderItem(ot);
        }
    }

    @Override
    public long calculateTotalRevenue(List<Order> orders) {
        Long totalRevenue = 0L;
        for (Order o : orders) {
            totalRevenue += o.getTotal();
        }
        return totalRevenue;
    }

    @Override
    public List<Order> getOrderBetWeen(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = getAllOrders();
        List<Order> orderBetween = new ArrayList<>();
        for (Order order : orders) {
            LocalDate orderDate = order.getCreateAt();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                orderBetween.add(order);
            }
        }
        return orderBetween;
    }


}
