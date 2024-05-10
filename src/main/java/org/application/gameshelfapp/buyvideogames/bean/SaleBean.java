package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Sale;

public class SaleBean {

    private long idBean;
    private VideogameBean gameSoldBean;
    private String emailBean;
    private String addressBean;
    private String platformBean;
    private String stateBean;
    private Sale sale;

    public long getIdBean() {
        return idBean;
    }

    public void setIdBean(long idBean) {
        this.idBean = idBean;
    }

    public VideogameBean getGameSoldBean() {
        return gameSoldBean;
    }

    public void setGameSoldBean(VideogameBean gameSoldBean) {
        this.gameSoldBean = gameSoldBean;
    }

    public String getEmailBean() {
        return emailBean;
    }

    public void setEmailBean(String emailBean) {
        this.emailBean = emailBean;
    }

    public String getAddressBean() {
        return addressBean;
    }

    public void setAddressBean(String addressBean) {
        this.addressBean = addressBean;
    }

    public String getPlatformBean() {
        return platformBean;
    }

    public void setPlatformBean(String platformBean) {
        this.platformBean = platformBean;
    }

    public String getStateBean() {
        return stateBean;
    }

    public void setStateBean(String stateBean) {
        this.stateBean = stateBean;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public void getInformationFromModel(){
        this.idBean = this.sale.getId();
        this.gameSoldBean = new VideogameBean();
        this.gameSoldBean.setVideogame(this.sale.getVideogameSold());
        this.gameSoldBean.getVideogameFromModel();
        this.emailBean = this.sale.getEmail();
        this.addressBean = this.sale.getAddress();
        this.platformBean = this.sale.getPlatform();
        this.stateBean = this.sale.getState();

    }
}
