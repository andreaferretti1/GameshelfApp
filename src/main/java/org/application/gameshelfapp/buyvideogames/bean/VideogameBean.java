package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;

public class VideogameBean {
    private String name;
    private int copiesBean;
    private float priceBean;
    private String descriptionBean;
    private String platformBean;
    private String categoryBean;
    private Videogame game;

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

    public Videogame getVideogame(){
        return this.game;
    }

    public void setVideogame(Videogame game){
        this.game = game;
    }

    public String getPlatformBean() {
        return this.platformBean;
    }

    public void setPlatformBean(String platformBean) {
        this.platformBean = platformBean;
    }

    public String getCategoryBean() {
        return this.categoryBean;
    }

    public void setCategoryBean(String categoryBean) {
        this.categoryBean = categoryBean;
    }

    public void getVideogameFromModel(){
        this.name = this.game.getName();
        this.copiesBean = this.game.getCopies();
        this.priceBean = this.game.getPrice();
        this.descriptionBean = this.game.getDescription();
        this.platformBean = this.game.getPlatform();
        this.categoryBean = this.game.getCategory();
    }
}
