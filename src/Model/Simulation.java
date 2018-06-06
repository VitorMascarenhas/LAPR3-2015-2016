/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author rekgnyz
 */
public class Simulation {
    private String id;
    private String description;
    private ArrayList<TrafficPattern> trafficPatternList;
    
    public Simulation(String id, String description, ArrayList<TrafficPattern> trafficPatternList){
        this.id = id;
        this.description = description;
        this.trafficPatternList = trafficPatternList;
    }
}
