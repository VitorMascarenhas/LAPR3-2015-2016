/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author 1082310
 */
public class Hybrid implements Engine {
    
    public enum FuelType {
        DIESEL, GASOLINE, ELECTRIC;
    }
    
    private FuelType[] fuelType;
    
    public Hybrid(String type) {
        String[] arr;
        arr = type.split(" ");
        this.fuelType[0] = FuelType.valueOf(arr[0].toUpperCase());
        this.fuelType[1] = FuelType.valueOf(arr[1].toUpperCase());
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
        if(this.fuelType == ((Hybrid) e).fuelType){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String getFuelType() {
        
        String fuel = "";
        for(int i = 0; i < this.fuelType.length;i++) {
            fuel+=this.fuelType[i].toString();
            fuel+=" ";
        }
        return fuel;
    }
}