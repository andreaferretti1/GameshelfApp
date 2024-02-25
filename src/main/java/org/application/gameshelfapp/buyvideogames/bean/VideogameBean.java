package org.application.gameshelfapp.buyvideogames.bean;

public class VideogameBean {
    private String name;
    private String id;
    private SellerBean sellerBean;

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

    public void setSellerBean(SellerBean sellerBean) {
        this.sellerBean = sellerBean;
    }

    public SellerBean getSellerBean() {
        return this.sellerBean;
    }
}
