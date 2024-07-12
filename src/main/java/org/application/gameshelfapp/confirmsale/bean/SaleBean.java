package org.application.gameshelfapp.confirmsale.bean;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.confirmsale.entities.Sale;

public class SaleBean {
    private long idBean;
    private VideogameBean gameSoldBean;
    private CredentialsBean credentialsBean;
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

    public CredentialsBean getCredentialsBean() {
        return this.credentialsBean;
    }
    public void setCredentialsBean(CredentialsBean credentialsBean) {
        this.credentialsBean = credentialsBean;
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
        this.credentialsBean = new CredentialsBean();
        this.credentialsBean.setCredentials(this.sale.getCredentials());
        this.credentialsBean.getInformationFromModel();
        this.stateBean = this.sale.getState();
    }
}
