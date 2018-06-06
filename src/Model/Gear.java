/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class Gear {
    
    private int id;
    private float ratio;
    
    public Gear(int id, float ratio) {
        this.id = id;
        this.ratio = ratio;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the ratio
     */
    public float getRatio() {
        return ratio;
    }
    
    public boolean equals(Gear g){
        if(this.id == g.id && this.ratio == g.ratio){
            return true;
        }else{
            return false;
        }
    }
}