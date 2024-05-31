package org.application.gameshelfapp.buyvideogames.bean;

public class FiltersBean {

    private String nameBean;
    private String consoleBean;
    private String categoryBean;
    public String getNameBean() {
        return this.nameBean;
    }

    public String getConsoleBean() {
        return this.consoleBean;
    }

    public String getCategoryBean() {
        return this.categoryBean;
    }

    public void setNameBean(String nameBean){
        this.nameBean = nameBean;
    }

    public void setCategoryBean(String categoryBean){
        this.categoryBean = categoryBean;
    }
    public void setConsoleBean(String consoleBean){
        this.consoleBean = consoleBean;
    }
}
