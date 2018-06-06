/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author 1081320
 */
public class Combustion implements Engine {

    
    public enum FuelType {
        DIESEL, GASOLINE, HYDROGEN;
    }
    
    private FuelType fuelType;
    
    public Combustion(String type) {
        this.fuelType = FuelType.valueOf(type.toUpperCase());
    }
    
    public float energyEfficiency() {
        // verify return
        return 0f;
    }
    
    public float breakingEnergyRegeneration() {
        return -1f;
    }

    @Override
    public String getFuelType() {
        return this.fuelType.toString();
    }
    
    public boolean equals(Engine e){
        if(this.fuelType == ((Combustion) e).fuelType){
            return true;
        }else{
            return false;
        }
    }
    

}