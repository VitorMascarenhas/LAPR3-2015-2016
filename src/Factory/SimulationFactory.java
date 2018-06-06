/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Simulation;
import Model.TrafficPattern;
import java.util.ArrayList;

/**
 *
 * @author rekgnyz
 */
public class SimulationFactory {
    public SimulationFactory(){}
    
    public Simulation createSimulation(String id, String description, ArrayList<TrafficPattern> trafficPatternList){
        if(trafficPatternList == null || id.equalsIgnoreCase("") || description.equalsIgnoreCase("")){
            return null;
        }else{
            return new Simulation(id, description, trafficPatternList);
        }
    }
}
