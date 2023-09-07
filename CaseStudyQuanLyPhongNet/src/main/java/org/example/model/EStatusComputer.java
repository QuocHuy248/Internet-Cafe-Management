package org.example.model;

public enum EStatusComputer {
    InUse(1,"Đang sử dụng"),UnderMaintenance(2,"Đang bảo trì"), Ready(3,"Sẵn sàng");
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

    EStatusComputer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static EStatusComputer findById(long id) {
        for (EStatusComputer e : values()) {
            if (e.getId() == id) {
                return e;
            }
        }return null;
    }
}
