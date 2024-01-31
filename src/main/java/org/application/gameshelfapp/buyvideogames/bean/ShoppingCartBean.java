package org.application.gameshelfapp.buyvideogames.bean;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartBean {

    private ArrayList<VideogameBean> items;
    private ArrayList<Integer> quantities;
    private float totalCost;

    public ShoppingCartBean(ArrayList<VideogameBean> items,  ArrayList<Integer> quantities , float cost){
        this.items = items;
        this.quantities = quantities;
        this.totalCost = cost;
    }

    public void setItems(ArrayList<VideogameBean> items) {
        this.items = items;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<VideogameBean> getItems() {
        return this.items;
    }

    public ArrayList<Integer> getQuantities() {
        return this.quantities;
    }

    public float getTotalCost() {
        return this.totalCost;
    }
}
