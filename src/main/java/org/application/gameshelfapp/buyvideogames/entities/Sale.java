package org.application.gameshelfapp.buyvideogames.entities;

public class Sale {
    private int id;
    private int copies;
    private float price;
    private String objectName;
    private String email;
    private String address;
    private String state;
    private String platform;

    public static final String TO_CONFIRM = "To confirm";
    public static final String CONFIRMED = "Confirmed";
    public Sale(int copies, float price, String objectName, String email, String address, String state, String platform) {
        this.copies = copies;
        this.price = price;
        this.objectName = objectName;
        this.email = email;
        this.address = address;
        this.state = state;
        this.platform = platform;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCopies() {
        return this.copies;
    }
    public void setCopies(int copies) {
        this.copies = copies;
    }
    public String getObjectName() {
        return this.objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPlatform() {
        return this.platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
