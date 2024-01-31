package org.application.gameshelfapp.buyvideogames.bean;

public class VideogameBean {
    private String name;
    private String id;
    private float price;

    public VideogameBean(String name, String id, float price){
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    public float getPrice(){
        return this.price;
    }

}
