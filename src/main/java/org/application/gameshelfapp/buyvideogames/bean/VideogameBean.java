package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;

public class VideogameBean {
    private String name;
    private int copiesBean;
    private float priceBean;
    private String descriptionBean;

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public int getCopiesBean() {
        return copiesBean;
    }
    public void setCopiesBean(int copiesBean) {
        this.copiesBean = copiesBean;
    }
    public float getPriceBean() {
        return this.priceBean;
    }
    public void setPriceBean(float priceBean) {
        this.priceBean = priceBean;
    }
    public String getDescriptionBean() {
        return this.descriptionBean;
    }
    public void setDescriptionBean(String descriptionBean) {
        this.descriptionBean = descriptionBean;
    }
    public void getVideogameFromModel(Videogame game){
        this.name = game.getName();
        this.copiesBean = game.getCopies();
        this.priceBean = game.getPrice();
        this.descriptionBean = game.getDescription();
    }
}
