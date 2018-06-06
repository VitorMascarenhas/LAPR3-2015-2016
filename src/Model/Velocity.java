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
public class Velocity {
    
    public enum Typology {
        REGULAR,
        URBAN,
        EXPRESS,
        HIGHWAY,
        CONTROLLED
    }
    
    private Typology segmentType;
    private float limit;
    
    public Velocity(String segment, float limit) {
        this.segmentType = Typology.valueOf(segment.toUpperCase());
        this.limit = limit;
    }
    
    public Typology getTypology() {
        return this.segmentType;
    }
    
    public float getLimit() {
        return this.limit;
    }
    
    public boolean equals(Velocity v){
        if(this.segmentType == v.segmentType && this.limit == v.limit){
            return true;
        }else{
            return false;
        }
    }
}
