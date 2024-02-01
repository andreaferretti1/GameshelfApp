package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.buyvideogames.exception.CopiesException;

import java.util.ArrayList;
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



    public void markAsPayed(Videogame game, int quantity){
        String id = game.getId();

        for(int i = 0; i<this.videogames.size(); i++){

            Videogame temp = this.videogames.get(i);
            if(id.equals(temp.getId())){
                int copiesInCart = this.quantities.get(i);
                    if(copiesInCart - quantity == 0){
                        this.videogames.remove(temp);
                        this.quantities.remove(i);
                    }
                    else this.quantities.add(i, copiesInCart - quantity);
            }
        }
        this.calculateTotalCost();
    }

    public void removeFromCart(Videogame game, int quantity) throws  CopiesException{
        String id = game.getId();

        for(int i = 0; i < this.videogames.size(); i++){
            Videogame temp = this.videogames.get(i);
            if(id.equals(temp.getId())){
                int copiesInCart = this.quantities.get(i);
                int difference = copiesInCart - quantity;
                if(difference < 0) throw new CopiesException("You are trying to remove more copies than the ones present in the cart");
                else if(difference == 0) {
                    this.videogames.remove(temp);
                    this.quantities.remove(i);
                }else{
                    this.quantities.add(i, difference);
                }
                int ownerCopies = quantity + game.getOwnerCopies();
                game.setOwnerCopies(ownerCopies);
            }
        }
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



