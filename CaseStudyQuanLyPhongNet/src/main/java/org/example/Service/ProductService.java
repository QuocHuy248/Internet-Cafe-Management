package org.example.Service;

import org.example.Utils.FileUtils;
import org.example.model.Product;

import java.util.List;

public class ProductService implements IProductService {
    private String fileProduct = "./data/products.txt";

    @Override
    public List<Product> getAllProducts() {
        return FileUtils.readData(fileProduct, Product.class);

    }

    @Override
    public Product findProduct(long id) {
        List<Product> products = getAllProducts();
        Product p = products.stream().filter(product -> product.getId() == id).findFirst().orElseThrow(null);
        return p;
    }

    @Override
    public void updateProduct(long id, Product product) {
        List<Product> products = getAllProducts();
        for (Product p : products) {
            if (p.getId() == id) {
                p.setName(product.getName());
                p.setDescription(product.getDescription());
                p.setPrice(product.getPrice());
                p.setCategory(product.getCategory());
                p.setQuantity(product.getQuantity());

            }
        }
        FileUtils.writeData(fileProduct, products);
    }

    @Override
    public void deleteProduct(long id) {
        List<Product> products = getAllProducts();
        Product product = null;
        for (Product p : products) {
            if (p.getId() == id) {
                product = p;
            }
        }
        products.remove(product);
        FileUtils.writeData(fileProduct, products);

    }

    @Override
    public void createProduct(Product product) {
        List<Product> products = getAllProducts();
        product.setId(products.size() + 1);
        products.add(product);
        FileUtils.writeData(fileProduct, products);

    }

    @Override
    public String getProductNameByIdProduct(long idProduct) {
        List<Product> products = getAllProducts();
        return products.stream().filter(product -> product.getId() == idProduct).findFirst().get().getName();
    }

    @Override
    public void updateQuantity(long quantity, long idProduct) {
        List<Product> products = getAllProducts();
        Product product = products.stream().filter(p -> p.getId() == idProduct).findFirst().get();
        product.setQuantity(product.getQuantity() - quantity);
        FileUtils.writeData(fileProduct, products);
    }
}

