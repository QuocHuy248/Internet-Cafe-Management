package org.example.Control;

import org.example.Service.IProductService;
import org.example.Service.ProductService;
import org.example.Utils.AuthUtils;
import org.example.Utils.DateUtils;
import org.example.Utils.ValidateUtils;
import org.example.Views.AdminView;
import org.example.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductControl {
    private Scanner scanner = new Scanner(System.in);
    private IProductService iProductService;

    public ProductControl() {
        iProductService = new ProductService();
    }

    public void productControlView() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║          Menu quản lý sản phẩm.                ║");
        System.out.println("║                                                ║");
        System.out.println("║         0.Trở về                               ║");
        System.out.println("║         1.Hiện danh sách sản phẩm.             ║");
        System.out.println("║         2.Thêm sản phẩm.                       ║");
        System.out.println("║         3.Sửa sản phẩm.                        ║");
        System.out.println("║         4.Xóa sản phẩm.                        ║");
        System.out.println("║         5.Sắp xếp sản phẩm.                    ║");
        System.out.println("║         6.Tìm kiếm sản phẩm.                   ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        int action = ValidateUtils.getIntOfWithBounds(0, 6);
        switch (action) {
            case 0:
                AdminView adminView = new AdminView();
                adminView.adminWorkView();
                break;
            case 1:
                showProduct();
                productControlView();
                break;
            case 2:
                addProduct();
                showProduct();
                productControlView();
                break;
            case 3:
                updateProduct();
                showProduct();
                productControlView();
                break;
            case 4:
                deleteProduct();
                showProduct();
                productControlView();
                break;
            case 5:
                sortProduct();
                productControlView();
                break;
            case 6:
                findProduct();
                productControlView();
                break;

        }
    }

    public void showProduct() {
        List<Product> products = iProductService.getAllProducts();

        System.out.printf("╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");

        System.out.printf("║%10s ║ %15s ║ %30s ║ %15s ║ %15s ║ %15s ║\n",
                "ID", "NAME", "PRODUCT DESCRIPTION", "PRICE", "CATEGORY", "QUANTITY");
        System.out.printf("║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║\n");

        for (Product product : products) {
            System.out.printf("║%10s ║ %15s ║ %30s ║ %15s ║ %15s ║ %15s ║ \n",
                    product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                    product.getCategory(), product.getQuantity());
        }
        System.out.printf("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

    }

    private void showProduct(List<Product> products) {
        System.out.printf("%10s | %15s | %30s | %15s | %15s | %15s \n",
                "ID", "NAME", "PRODUCT DESCRIPTION", "PRICE", "CATEGORY", "QUANTITY");
        for (Product product : products) {
            System.out.printf("%10s | %15s | %30s | %15s | %15s | %15s \n",
                    product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                    product.getCategory(), product.getQuantity());

        }
    }

    private void addProduct() {
        List<Product> products = iProductService.getAllProducts();
        String name = checkInputProductNameValid(ValidateUtils.FIELD_PRODUCTNAME, ValidateUtils.FIELD_PRODUCTNAME_MESSAGE, ValidateUtils.REGEX_PRODUCTNAME);
        String productDes = checkInputValid(ValidateUtils.FIELD_PRODUCTDES, ValidateUtils.FIELD_PRODUCTDES_MESSAGE, ValidateUtils.REGEX_PRODUCTNAME);
        long price = Long.parseLong(checkInputValid(ValidateUtils.FIELD_PRODUCTPRICE, ValidateUtils.FIELD_PRODUCTPRICE_MESSAGE, ValidateUtils.REGEX_PRODUCTPRICE));
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Nhập chủng loại::              ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Đồ ăn.                    ║");
        System.out.println("║                 2.Nước uống.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        long choice = ValidateUtils.getIntOfWithBounds(1, 2);
        ECategory category = ECategory.findById(choice);
        long quantity = Long.parseLong(checkInputValid(ValidateUtils.FIELD_PRODUCTQUANTITY, ValidateUtils.FIELD_QUANTITYP_MESSAGE, ValidateUtils.REGEX_QUANTITYPRODUCT));
        Product product = new Product(products.size(), name, productDes
                , price, category, quantity);
        iProductService.createProduct(product);

    }

    private void updateProduct() {
        showProduct();
        long id = Long.parseLong(checkInputIDProductValidExist(ValidateUtils.FIELD_IDPRODUCT, ValidateUtils.FIELD_IDPRODUCT_MESSAGE, ValidateUtils.REGEX_IDPRODUCT));
        String name = checkInputProductNameValid(ValidateUtils.FIELD_PRODUCTNAME, ValidateUtils.FIELD_PRODUCTNAME_MESSAGE, ValidateUtils.REGEX_PRODUCTNAME);
        String productDes = checkInputValid(ValidateUtils.FIELD_PRODUCTDES, ValidateUtils.FIELD_PRODUCTDES_MESSAGE, ValidateUtils.REGEX_PRODUCTNAME);
        long price = Long.parseLong(checkInputValid(ValidateUtils.FIELD_PRODUCTPRICE, ValidateUtils.FIELD_PRODUCTPRICE_MESSAGE, ValidateUtils.REGEX_PRODUCTPRICE));
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Nhập chủng loại::              ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Đồ ăn.                    ║");
        System.out.println("║                 2.Nước uống.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        long choice = ValidateUtils.getIntOfWithBounds(1, 2);
        ECategory eCategory = ECategory.findById(choice);
        long quantity = Long.parseLong(checkInputValid(ValidateUtils.FIELD_PRODUCTQUANTITY, ValidateUtils.FIELD_QUANTITYP_MESSAGE, ValidateUtils.REGEX_QUANTITYPRODUCT));
        Product product = new Product(id, name, productDes, price, eCategory, quantity);
        iProductService.updateProduct(id, product);
    }

    private void deleteProduct() {
        showProduct();
        long id = Long.parseLong(checkInputIDProductValidExist(ValidateUtils.FIELD_IDPRODUCT, ValidateUtils.FIELD_IDPRODUCT_MESSAGE, ValidateUtils.REGEX_IDPRODUCT));
        iProductService.deleteProduct(id);


    }

    private void sortProduct() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Chọn kiểu sắp xếp:             ║");
        System.out.println("║                                             ║");
        System.out.println("║                1.Tăng dần.                  ║");
        System.out.println("║                2.Giảm dân.                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int select = ValidateUtils.getIntOfWithBounds(1, 2);
        List<Product> products = iProductService.getAllProducts();
        Comparator<Product> comparator = null;
        switch (select) {
            case 1:
                products.sort(sortProductDescending(comparator));
                break;

            case 2:
                products.sort(sortProductDecreasing(comparator));
                break;

        }
        showProduct(products);


    }

    private Comparator<Product> sortProductDecreasing(Comparator<Product> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về.                   ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Mô tả.                    ║");
        System.out.println("║                 4.Giá.                      ║");
        System.out.println("║                 5.Chủng loại.               ║");
        System.out.println("║                 6.Số lượng                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");

        int choice = ValidateUtils.getIntOfWithBounds(0, 6);
        switch (choice) {
            case 0:
                sortProduct();
                break;
            case 1:
                comparator = Comparator.comparing(Product::getId).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(Product::getName).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(Product::getDescription).reversed();
                break;
            case 4:
                comparator = Comparator.comparing(Product::getPrice).reversed();
                break;
            case 5:
                comparator = Comparator.comparing(Product::getCategory).reversed();
                break;
            case 6:
                comparator = Comparator.comparing(Product::getQuantity).reversed();
                break;
        }
        return comparator;
    }

    private Comparator<Product> sortProductDescending(Comparator<Product> comparator) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn sắp xếp theo:         ║");
        System.out.println("║                                             ║");
        System.out.println("║                 0.Trở về.                   ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Mô tả.                    ║");
        System.out.println("║                 4.Giá.                      ║");
        System.out.println("║                 5.Chủng loại.               ║");
        System.out.println("║                 6.Số lượng                  ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        int choice = ValidateUtils.getIntOfWithBounds(0, 6);
        switch (choice) {
            case 0:
                sortProduct();
                break;
            case 1:
                comparator = Comparator.comparing(Product::getId);
                break;
            case 2:
                comparator = Comparator.comparing(Product::getName);
                break;
            case 3:
                comparator = Comparator.comparing(Product::getDescription);
                break;
            case 4:
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case 5:
                comparator = Comparator.comparing(Product::getCategory);
                break;
            case 6:
                comparator = Comparator.comparing(Product::getQuantity);
                break;
        }
        return comparator;
    }

    private void findProduct() {
        showProduct();
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Bạn muốn tìm kiếm theo:        ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.ID.                       ║");
        System.out.println("║                 2.Tên.                      ║");
        System.out.println("║                 3.Chủng loại                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        List<Product> products = iProductService.getAllProducts();
        List<Product> products1 = null;
        int action = ValidateUtils.getIntOfWithBounds(1, 3);
        ;
        switch (action) {
            case 1:
                products1 = findProductByID(products);
                break;
            case 2:
                products1 = findProductbyName(products);
                break;
            case 3:
                products1 = findProductByCategory(products);
                break;


        }
        showProduct(products1);
    }

    public static void main(String[] args) {
        ProductControl productControl = new ProductControl();
        productControl.showProduct();
    }


    private List<Product> findProductByCategory(List<Product> products) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║              Nhập chủng loại::              ║");
        System.out.println("║                                             ║");
        System.out.println("║                 1.Đồ ăn.                    ║");
        System.out.println("║                 2.Nước uống.                ║");
        System.out.println("║                                             ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        long choice = ValidateUtils.getIntOfWithBounds(1, 2);
        ECategory e = ECategory.findById(choice);
        List<Product> products1 = products.stream().filter(product -> product.getCategory().equals(e)).collect(Collectors.toList());
        return products1;
    }

    private List<Product> findProductbyName(List<Product> products) {
        String name = checkInputProductNameValidExist(ValidateUtils.FIELD_PRODUCTNAME, ValidateUtils.FIELD_PRODUCTNAME_MESSAGE, ValidateUtils.REGEX_PRODUCTNAME);
        List<Product> products1 = products.stream().filter(product -> product.getName().equals(name)).collect(Collectors.toList());
        return products1;
    }

    private List<Product> findProductByID(List<Product> products) {
        long id = Long.parseLong(checkInputIDProductValidExist(ValidateUtils.FIELD_IDPRODUCT, ValidateUtils.FIELD_IDPRODUCT_MESSAGE, ValidateUtils.REGEX_IDPRODUCT));
        List<Product> products1 = products.stream().filter(product -> product.getId() == id)
                .collect(Collectors.toList());
        return products1;
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

    private String checkInputProductNameValid(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && checkProductName(input)) {
                System.out.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private String checkInputProductNameValidExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!ValidateUtils.isValid(fieldPattern, input)) {
                System.out.println(fieldMessage);
                validateInput = true;
            } else if (ValidateUtils.isValid(fieldPattern, input) && !checkProductName(input)) {
                System.out.println("Không tìm thấy tên sản phẩm, vui lòng nhập lại");
                validateInput = true;
            } else {
                validateInput = false;
            }

        } while (validateInput);
        return input;
    }

    private boolean checkProductName(String input) {
        boolean check = false;
        List<Product> products = iProductService.getAllProducts();
        for (Product p : products) {
            if (p.getName().equals(input)) {
                check = true;
            }
        }
        return check;
    }

    private boolean checkProductID(String id) {
        boolean check = false;
        List<Product> products = iProductService.getAllProducts();
        for (Product p : products) {
            if (p.getId() == Long.parseLong(id)) {
                check = true;
            }
        }
        return check;
    }

    private String checkInputIDProductValidExist(String fieldName, String fieldMessage, String fieldPattern) {
        String input = null;
        boolean validateInput = false;
        do {
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (!ValidateUtils.isValid(fieldPattern, input)) {
                    System.out.println(fieldMessage);
                    validateInput = true;
                } else if (ValidateUtils.isValid(fieldPattern, input) && !checkProductID(input)) {
                    System.out.println("Không tìm thấy id sản phẩm, vui lòng nhập lại");
                    validateInput = true;
                } else {
                    validateInput = false;
                }
            } else {
                    productControlView();
            }
        } while (validateInput);
        return input;
    }
}
