/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Energy;
import Model.Engine;
import Model.Vehicle;
import Model.Velocity;
import java.util.ArrayList;

/**
 *
 * @author rekgnyz
 */
public class VehicleFactory {
    public VehicleFactory(){
    }
    
    public Vehicle createVehicle(String name, String description, String vehicleType, String load, String drag, String rrc, String wheelSize, ArrayList<Velocity> velocities, Energy energy, String mass, Engine engine, String frontal_area){
        
        if(!vehicleType.equalsIgnoreCase("") && engine!=null && !load.equalsIgnoreCase("") && !drag.equalsIgnoreCase("") && !frontal_area.equalsIgnoreCase("") && !rrc.equalsIgnoreCase("") && !wheelSize.equalsIgnoreCase("") && !mass.equalsIgnoreCase("")){
            float ld = Float.parseFloat(removeKg(load));
            float drg = Float.parseFloat(drag);
            float r = Float.parseFloat(rrc);
            float mss = Float.parseFloat(removeKg(mass));
            float whls = Float.parseFloat(wheelSize);
            float frontal = Float.parseFloat(frontal_area);
         
            Vehicle v = new Vehicle(name, description, vehicleType, ld, drg, r, whls, velocities, energy, mss, engine, frontal);
        
            return v;
        }else{
            return null;
        }
    }
    
    public String removeKg(String text){
        String ret="";
        String vec[];
        if(text.contains("Kg")){
           vec = text.split("Kg");
           ret = vec[0].trim();
        }
        
        if(text.contains("kg")){
           vec = text.split("kg");
           ret = vec[0].trim();
        }
        
        if(text.contains("kG")){
           vec = text.split("kG");
           ret = vec[0].trim();
        }
        
        if(text.contains("KG")){
           vec = text.split("KG");
           ret = vec[0].trim();
        }
        
        return ret;
    }
}
