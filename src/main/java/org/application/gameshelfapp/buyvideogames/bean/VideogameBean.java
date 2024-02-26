package org.application.gameshelfapp.buyvideogames.bean;

public class VideogameBean {
    private String name;
    private String id;
    private OwnerBean ownerBean;

    public VideogameBean(String name, String id){
        this.name = name;
        this.id = id;
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

    public void setOwnerBean(OwnerBean ownerBean) {
        this.ownerBean = ownerBean;
    }

    public OwnerBean getOwnerBean() {
        return this.ownerBean;
    }
}
