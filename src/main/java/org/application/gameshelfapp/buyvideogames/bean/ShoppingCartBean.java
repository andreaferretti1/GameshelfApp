package org.application.gameshelfapp.buyvideogames.bean;

import java.util.List;

public class ShoppingCartBean {

    private List<VideogameBean> items;
    private List<Integer> quantities;
    private float totalCost;

    public ShoppingCartBean(List<VideogameBean> items,  List<Integer> quantities , float cost){
        this.items = items;
        this.quantities = quantities;
        this.totalCost = cost;
    }

    public void setItems(List<VideogameBean> items) {
        this.items = items;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public List<VideogameBean> getItems() {
        return this.items;
    }

    public List<Integer> getQuantities() {
        return this.quantities;
    }

    public float getTotalCost() {
        return this.totalCost;
    }
}
