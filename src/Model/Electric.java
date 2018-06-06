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
public class Electric implements Engine {
    
    public enum FuelType {
        ELECTRIC;
    }
    
    private FuelType fuelType;
    
    public Electric(String type) {
        this.fuelType = FuelType.valueOf(type.toUpperCase());
    }
    
    public float energyEfficiency() {
        // verify return
        return 0f;
    }
    
    public float breakingEnergyRegeneration() {
        // verify calculation
        return 0f;
    }
    
    @Override
    public boolean equals(Engine e){
        if(this.fuelType == ((Electric) e).fuelType){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String getFuelType() {
        return this.fuelType.toString();
    }
}