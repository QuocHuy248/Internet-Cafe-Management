package org.example.model;

public enum EGender {
    MALE(1, " Nam"), FEMALE(2, "Nữ");

    private EGender(long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public static EGender findById(long id) {
        for (EGender e : values()) {
            if (e.getId() == id) {
                return e;
            }
        }return null;
    }
}
