package org.example.model;

public enum ECategory {

    FOOD(1,"Đồ ăn"),DRINK(2,"Nước uống");
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ECategory(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public static ECategory findById(long id) {
        for (ECategory e : values()) {
            if (e.getId() == id) {
                return e;
            }
        }return null;
    }
}
