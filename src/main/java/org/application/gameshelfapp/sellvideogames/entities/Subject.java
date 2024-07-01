package org.application.gameshelfapp.sellvideogames.entities;

import org.application.gameshelfapp.sellvideogames.bean.Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();

    }
    public void attach (Observer observer){
        this.observers.add(observer);
    }

    public void detach (Observer observer){
        this.observers.remove(observer);
    }

    protected void notifyObservers(){
        for (Observer obs: this.observers){
            obs.update();
        }
    }
}