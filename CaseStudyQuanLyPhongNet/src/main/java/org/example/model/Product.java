package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements IParseModel<Product> {
    private long id;
    private String name;
    private String description;
    private long price;
    private ECategory category;
    private long quantity;


    public Product parse(String line) {
        String[] items = line.split(",");
        Product p =null;
        try {
         p = new Product(Long.parseLong(items[0]),items[1],items[2],Long.parseLong(items[3]),ECategory.valueOf(items[4]),Long.parseLong(items[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s",this.id,this.name,this.description,this.price,this.category,this.quantity);
    }
}
