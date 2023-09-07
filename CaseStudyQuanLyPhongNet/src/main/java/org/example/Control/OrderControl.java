package org.example.Control;

import org.example.Service.*;
import org.example.Utils.AuthUtils;
import org.example.Utils.DateUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.Views.EmployeeView;
import org.example.Views.UserView;
import org.example.model.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderControl {
    private Scanner scanner = new Scanner(System.in);
    private IOrderService iOrderService;
    private IProductService iProductService;
    private IOrderItemService iOrderItemService;


    public OrderControl() {
        iOrderService = new OrderService();
        iProductService = new ProductService();

    }

    public void orderControlView() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý đơn hàng                ║");
        System.out.println("║                                                ║");
        System.out.println("║         0.Trở về.                              ║");
        System.out.println("║         1.Xem danh sách đơn hàng.              ║");
        System.out.println("║         2.Xóa order.                           ║");
        System.out.println("║         3.Đặt hàng.                            ║");
        System.out.println("║         4.Sắp xếp đơn hàng.                    ║");
        System.out.println("║         5.Tìm kiếm đơn hàng.                   ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 5);
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.adminWorkView();
                break;
            case 1:
                showOrder();
                orderControlView();
                break;
            case 2:
                deleteOrder();
                orderControlView();
                break;
            case 3:
                createOrderByAdmin();
                orderControlView();
                break;
            case 4:
                sortOder();
                orderControlView();
                break;
            case 5:
                findOrder();
                orderControlView();
                break;
        }
    }

    public void orderControlViewByEmployee() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           Menu quản lý đơn hàng                ║");
        System.out.println("║                                                ║");
        System.out.println("║         0.Trở về.                              ║");
        System.out.println("║         1.Xem danh sách đơn hàng.              ║");
        System.out.println("║         2.Xóa order.                           ║");
        System.out.println("║         3.Đặt hàng.                            ║");
        System.out.println("║         4.Sắp xếp đơn hàng.                    ║");
        System.out.println("║         5.Tìm kiếm đơn hàng.                   ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 5);
        switch (action) {
            case 0:
                EmployeeView employeeView = new EmployeeView();
                employeeView.employeeLauncher();
                break;
            case 1:
                showOrder();
                orderControlViewByEmployee();
                break;
            case 2:
                deleteOrder();
                orderControlViewByEmployee();
                break;
            case 3:
                createOrderByAdmin();
                orderControlViewByEmployee();
                break;
            case 4:
                sortOder();
                orderControlViewByEmployee();
                break;
            case 5:
                findOrder();
                orderControlViewByEmployee();
                break;
        }
    }

    private void showOrder() {
        List<Order> orders = iOrderService.getAllOrders();
        System.out.printf("╔════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%10s ║ %15s ║ %10s ║ %15s ║\n", "ID", "CREATE AT", "TOTAL", "USER");
        System.out.printf("║════════════════════════════════════════════════════════════║\n");
        for (Order order : orders) {
            System.out.printf("║%10s ║ %15s ║ %10s ║ %15s ║\n", order.getId()
                    , DateUtils.formatDate(order.getCreateAt()), order.getTotal(), order.getUsername());
        }
        System.out.printf("╚════════════════════════════════════════════════════════════╝\n");

    }

    private void showOrder(List<Order> orders) {
//        50055,29-08-2023,25000,dat123
        System.out.printf("╔════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%10s ║ %15s ║ %10s ║ %15s ║\n", "ID", "CREATE AT", "TOTAL", "USER");
        System.out.printf("║════════════════════════════════════════════════════════════║\n");
        for (Order order : orders) {
            System.out.printf("║%10s ║ %15s ║ %10s ║ %15s ║\n", order.getId()
                    , DateUtils.formatDate(order.getCreateAt()), order.getTotal(), order.getUsername());
        }
        System.out.printf("╚════════════════════════════════════════════════════════════╝\n");
    }

    private void deleteOrder() {
        long id = Long.parseLong(checkInputValidIdOExist(ValidateUtils.FIELD_ORDERID, ValidateUtils.FIELD_IDORDER_MESSAGE, ValidateUtils.REGEX_ORDERID));
        iOrderService.deleteOrder(id);
        System.out.println("Xóa thành công");
    }

    private void createOrderByAdmin() {
        Order order = new Order();
        order.setId(System.currentTimeMillis() % 100000);
        boolean checkContinueOrderItem = false;
        ProductControl productControl = new ProductControl();
        productControl.showProduct();
        do {
            Product p = null;
            long idProduct = 0;
            do {
                idProduct = Long.parseLong(checkInputValidIdProductExist(ValidateUtils.FIELD_IDPRODUCT, ValidateUtils.FIELD_IDPRODUCT_MESSAGE, ValidateUtils.REGEX_IDPRODUCT));
                p = iProductService.findProduct(idProduct);
            }
            while (!checkQuantityExist(p));
            int quantity = Integer.parseInt(checkInputValidProductQuantityExist(ValidateUtils.FIELD_PRODUCTQUANTITY, ValidateUtils.FIELD_QUANTITYP_MESSAGE, ValidateUtils.REGEX_QUANTITYPRODUCT, p));
            if (order.getOrderItems() == null) {
                List<OrderItem> orderItems = new ArrayList<>();
                OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000,
                        order.getId(), idProduct, quantity, p.getPrice());
                orderItems.add(orderItem);
                order.setOrderItems(orderItems);
            } else {
                if (checkProductExistsInOrder(idProduct, order)) {
                    for (OrderItem ot : order.getOrderItems()) {
                        if (ot.getIdProduct() == idProduct) {
                            ot.setQuantity(quantity);
                        }
                    }

                } else {
                    OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000,
                            order.getId(), idProduct, quantity, p.getPrice());
                    order.getOrderItems().add(orderItem);
                }
            }
            System.out.println("Bạn có muốn thêm tiếp sản phẩm không: Y/N");
            String actionContinueOrderItem = checkInputValid(ValidateUtils.FIELD_ACTIONCONTINUE, ValidateUtils.FIELD_ACTIONCONTINUE_MESSAGE, ValidateUtils.REGEX_ACTIONCONTINUE);
            switch (actionContinueOrderItem) {
                case "Y":
                    checkContinueOrderItem = true;
                    break;
                case "N":
                    checkContinueOrderItem = false;
                    break;
            }
        } while (checkContinueOrderItem);
        order.setTotalPrice();
        order.setCreateAt(LocalDate.now());
        order.setUsername("null");
        updateProductQuantity(order);
        iOrderService.createOrder(order);


    }

    public void createOrderByUser(User user) {
        Order order = new Order();
        order.setId(System.currentTimeMillis() % 100000);
        boolean checkContinueOrderItem = false;
        do {
            Product p = null;
            long idProduct = 0;
            do {
                idProduct = Long.parseLong(checkInputValidIdProductExist(ValidateUtils.FIELD_IDPRODUCT, ValidateUtils.FIELD_IDPRODUCT_MESSAGE, ValidateUtils.REGEX_IDPRODUCT));
                p = iProductService.findProduct(idProduct);
            }
            while (!checkQuantityExist(p));
            int quantity = Integer.parseInt(checkInputValidProductQuantityExist(ValidateUtils.FIELD_PRODUCTQUANTITY, ValidateUtils.FIELD_QUANTITYP_MESSAGE, ValidateUtils.REGEX_QUANTITYPRODUCT, p));
            if (order.getOrderItems() == null) {
                List<OrderItem> orderItems = new ArrayList<>();
                OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000,
                        order.getId(), idProduct, quantity, p.getPrice());
                orderItems.add(orderItem);
                order.setOrderItems(orderItems);
            } else {
                if (checkProductExistsInOrder(idProduct, order)) {
                    for (OrderItem ot : order.getOrderItems()) {
                        if (ot.getIdProduct() == idProduct) {
                            ot.setQuantity(quantity);
                        }
                    }

                } else {
                    OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000,
                            order.getId(), idProduct, quantity, p.getPrice());
                    order.getOrderItems().add(orderItem);
                }
            }
            System.out.println("Bạn có muốn thêm tiếp sản phẩm không: Y/N");
            String actionContinueOrderItem = checkInputValid(ValidateUtils.FIELD_ACTIONCONTINUE, ValidateUtils.FIELD_ACTIONCONTINUE_MESSAGE, ValidateUtils.REGEX_ACTIONCONTINUE);
            switch (actionContinueOrderItem) {
                case "Y":
                    checkContinueOrderItem = true;
                    break;
                case "N":
                    checkContinueOrderItem = false;
                    break;
            }
        } while (checkContinueOrderItem);
        order.setTotalPrice();
        order.setCreateAt(LocalDate.now());
        order.setUsername(user.getUsername());
        System.out.printf("╔════════════════════════════════════════════════════════════╗\n");

        System.out.printf("║15%s ║ 15%s ║ 15%s ║ 15%s ║ 15%s║\n", "ID Product", " Name Product", "Quantity", " Price of Product", "Price");
        List<OrderItem> orderItems = order.getOrderItems();
        System.out.printf("║════════════════════════════════════════════════════════════║\n");

        for (OrderItem o : orderItems) {
            System.out.printf("║15%s ║ 15%s ║ 15%s ║ 15%s ║ 15%s║\n",
                    o.getId(), iProductService.getProductNameByIdProduct(o.getIdProduct()),
                    o.getQuantity(), o.getPrice(), o.getQuantity() * o.getPrice());

        }
        System.out.printf(" ║69%s ║ 15%s\n",
                "Total Price", order.getTotal());
        System.out.printf("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║           Bạn có muốn thanh toán không.     ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1. Có.                      ║");
        System.out.println("║                 2. Không.                   ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int act = ValidateUtils.getIntOfWithBounds(1, 2);
        switch (act) {
            case 1:
                iOrderService.createOrder(order);
                updateProductQuantity(order);
                break;
            case 2:
                UserView userView = new UserView();
                userView.userViewLauncher();
                break;
        }

    }


    public boolean checkProductExistsInOrder(long idProduct, Order order) {
        if (order.getOrderItems() == null) {
            return false;
        } else {
            for (OrderItem ot : order.getOrderItems()) {
                if (ot.getIdProduct() == idProduct) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sortOder() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn kiểu sắp xếp:             ║");
        System.out.println("║                                             ║");
        System.out.println("║                1.Tăng dần.                  ║");
        System.out.println("║                2.Giảm dân.                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int select = Integer.parseInt(scanner.nextLine());
        List<Order> orders = iOrderService.getAllOrders();
        Comparator<Order> comparator = null;
        switch (select) {
            case 1:
                orders.sort(sortOrderDescending(comparator));
                break;

            case 2:
                orders.sort(sortOrderDecreasing(comparator));
                break;

        }
        showOrder(orders);
    }

    private Comparator<Order> sortOrderDecreasing(Comparator<Order> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về.                   ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Ngày tạo đơn hàng.        ║");
        System.out.println("║                 3.Tên người đặt hàng.       ║");
        System.out.println("║                 4.Giá trị hóa đơn.          ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 4);
        switch (choice) {
            case 0:
                sortOder();
                break;
            case 1:
                comparator = Comparator.comparing(Order::getId).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(Order::getCreateAt).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(Order::getUsername).reversed();
                break;
            case 4:
                comparator = Comparator.comparing(Order::getTotal).reversed();
                break;
        }
        return comparator;
    }

    private Comparator<Order> sortOrderDescending(Comparator<Order> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về.                   ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Ngày tạo đơn hàng.        ║");
        System.out.println("║                 3.Tên người đặt hàng.       ║");
        System.out.println("║                 4.Giá trị hóa đơn.          ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 4);
        switch (choice) {
            case 0:
                sortOder();
                break;
            case 1:
                comparator = Comparator.comparing(Order::getId);
                break;
            case 2:
                comparator = Comparator.comparing(Order::getCreateAt);
                break;
            case 3:
                comparator = Comparator.comparing(Order::getUsername);
                break;
            case 4:
                comparator = Comparator.comparing(Order::getTotal);
                break;
        }
        return comparator;
    }


    private void findOrder() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn tìm kiếm theo:        ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên người đặt hàng.       ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(1, 2);
        List<Order> orders = iOrderService.getAllOrders();
        List<Order> orderResult = null;
        switch (action) {
            case 1:
                orderResult = findOrderByID(orders);
                break;
            case 2:
                orderResult = findOrderByUserName(orders);
                break;
        }
        showOrder(orderResult);

    }

    private List<Order> findOrderByID(List<Order> orders) {

        long id = Long.parseLong(checkInputValidIdOExist(ValidateUtils.FIELD_ORDERID, ValidateUtils.FIELD_IDORDER_MESSAGE, ValidateUtils.REGEX_ORDERID));
        List<Order> resultOrder = orders.stream().filter(order -> order.getId() == id).collect(Collectors.toList());
        return resultOrder;
    }

    private List<Order> findOrderByUserName(List<Order> orders) {
        String name = checkInputValidUserNameOExist(ValidateUtils.FIELD_USER, ValidateUtils.FIELD_USER_MESSAGE, ValidateUtils.REGEX_USERNAME);
        List<Order> resultOrder = orders.stream().filter(order -> order.getUsername().equals(name)).collect(Collectors.toList());
        return resultOrder;
    }

    public static void main(String[] args) {
        OrderControl orderControl = new OrderControl();
        orderControl.showOrder();
    }

    private String checkInputValid(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {         // Nếu SAI
                System.out.println(fieldMessage);
                validateInput = true;
            } else {
                validateInput = false;
            }
        } while (validateInput);
        return input;
    }

    private boolean checkIDOrder(String pid) {
        List<Order> orders = iOrderService.getAllOrders();
        for (Order o : orders) {
            if (o.getId() == Long.parseLong(pid)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkUserNameOrder(String userName) {
        List<Order> orders = iOrderService.getAllOrders();
        for (Order o : orders) {
            if (o.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIDProduct(String pid) {
        List<Product> products = iProductService.getAllProducts();
        for (Product p : products) {
            if (p.getId() == Long.parseLong(pid)) {
                return true;
            }
        }
        return false;
    }

    private String checkInputValidUserNameOExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkUserNameOrder(input)) {
                    System.out.println("Không tìm thấy username trong danh sách order, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    orderControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    orderControlViewByEmployee();
                }
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidIdOExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkIDOrder(input)) {
                    System.out.println("Không tìm thấy id của Order, vui lòng nhập lại id");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                if (AuthUtils.getUser().getRole().equals(ERole.ADMIN)) {
                    orderControlView();
                } else if (AuthUtils.getUser().getRole().equals(ERole.EMPLOYEE)) {
                    orderControlViewByEmployee();
                }
            }
        } while (validateInput);
        return input;
    }

    private String checkInputValidIdProductExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkIDProduct(input)) {
                System.out.println("Không tìm thấy id của sản phẩm, vui lòng nhập lại id");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputValidProductQuantityExist(String fieldName, String fieldMessage, String fieldPattern, Product product) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkQuantity(product, input)) {
                System.out.println("Số lượng sản phẩm bạn đặt lớn hơn số lượng sản phẩm hiện có, vui lòng chọn lại số lượng sản phẩm");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private boolean checkQuantity(Product product, String input) {
        boolean check = false;
        long quantity = Long.parseLong(input);

        if (quantity > product.getQuantity()) {
            check = false;
        } else if (quantity <= product.getQuantity()) {
            check = true;
        }
        return check;
    }

    private boolean checkQuantityExist(Product product) {
        boolean check = false;
        if (product.getQuantity() == 0) {
            check = false;
            System.out.println("Sản phẩm đã hết, vui lòng chọn sản phẩm khác");
        } else {
            check = true;
        }

        return check;
    }

    private void updateProductQuantity(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.stream().forEach(orderItem -> iProductService.updateQuantity(orderItem.getQuantity(), orderItem.getIdProduct()));
    }


}
