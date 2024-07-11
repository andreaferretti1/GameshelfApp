package org.application.gameshelfapp.confirmsale.bean;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.confirmsale.entities.Sale;

public class SaleBean {
    private long idBean;
    private VideogameBean gameSoldBean;
    private String emailBean;
    private String addressBean;
    private String nameBean;
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

    public String getNameBean() {
        return nameBean;
    }

    public void setNameBean(String nameBean) {
        this.nameBean = nameBean;
    }

    public String getStateBean() {
        return stateBean;
    }

    public void setStateBean(String stateBean) {
        this.stateBean = stateBean;
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
        this.nameBean = this.sale.getName();
        this.stateBean = this.sale.getState();
    }
}
