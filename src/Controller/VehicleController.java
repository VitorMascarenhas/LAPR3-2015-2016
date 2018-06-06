/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Energy;
import Model.Engine;
import Model.Vehicle;
import Model.Velocity;
import java.util.ArrayList;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class VehicleController {
    
    public VehicleController() {
    }
    
    public Vehicle importVehicle(String name, String description, String vehicleType, String type, String load, String drag, String rrc, String wheelSize, ArrayList<Velocity> velocity, Energy energy, String mass, Engine engine, String frontal_area) {
        
        float ld = Float.parseFloat(load);
        float drg = Float.parseFloat(drag);
        float r = Float.parseFloat(rrc);
        float mss = Float.parseFloat(mass);
        float whls = Float.parseFloat(wheelSize);
        
       float frontal = Float.parseFloat(frontal_area);
         
          //  Vehicle v = new Vehicle(name, description, vehicleType, ld, drg, r, whls, velocities, energy, mss, engine, frontal);
        
        return null;
    }
}