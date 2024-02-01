package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.buyvideogames.exception.CopiesException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {

    private List<Videogame> videogames;
    private List<Integer> quantities;
    private float totalCost;

    public ShoppingCart(){
        this.totalCost = 0;
        this.videogames = new ArrayList<Videogame>();
        this.quantities = new ArrayList<Integer>();
    }

    public void addGame(Videogame game, int quantity) throws CopiesException{
        String id = game.getId();

        for(int i = 0; i<this.videogames.size(); i++){

            Videogame temp = this.videogames.get(i);
            if(id.equals(temp.getId())){
                int currentCopies = this.quantities.get(i);
                if(currentCopies - quantity < 0) throw new CopiesException("Copies not available. There are" + currentCopies + "copies left.");
                this.quantities.add(i, currentCopies + quantity);
                this.calculateTotalCost();
                return;
            }
        }
        this.videogames.add(game);
        this.quantities.add(quantity);
        this.calculateTotalCost();
    }



    public void markAsPayed(Videogame game){
        String id = game.getId();
        Iterator<Videogame> iterator = this.videogames.iterator();

        while(iterator.hasNext()){

            Videogame temp = iterator.next();
            if(id.equals(temp.getId())){
                this.quantities.set(this.videogames.indexOf(temp), 0);
                iterator.remove();
                break;
            }
        }
        this.quantities.removeIf(quantity -> quantity == 0);
    }

    public void removeFromCart(Videogame game, int quantity) throws  CopiesException{
        String id = game.getId();
        Iterator<Videogame> iterator = this.videogames.iterator();

        while (iterator.hasNext()){
            Videogame temp = iterator.next();
            if(id.equals(temp.getId())){
                int copiesInCart = this.quantities.get(this.videogames.indexOf(temp));
                int difference = copiesInCart - quantity;
                if(difference < 0) throw new CopiesException("You are trying to remove more copies than the ones present in the cart");
                this.quantities.set(this.videogames.indexOf(temp), difference);
                if(difference == 0) {
                    this.videogames.remove(temp);
                }
            }
        }
        int ownerCopies = quantity + game.getOwnerCopies();
        game.setOwnerCopies(ownerCopies);
        this.quantities.removeIf(copies -> copies == 0);
        this.calculateTotalCost();
    }



    public List<Videogame> listVideogames(){
        return this.videogames;
    }

    private void calculateTotalCost(){
        this.totalCost = 0;
        for(int i = 0; i<this.videogames.size(); i++){
            Videogame game = this.videogames.get(i);
            int quantity = this.quantities.get(i);
            float price = game.getOwnerPrice();
            this.totalCost += quantity * price;
        }
    }

    public float getTotalCost(){
        return this.totalCost;
    }

    public List<Integer> listQuantities(){
        return this.quantities;
    }


}



