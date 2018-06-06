/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Junction;
import java.util.ArrayList;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class RepositoryJunctions {
    
    private static ArrayList<Junction> junctions = new ArrayList<>();
    
    public void addJunction(Junction junction) {
        junctions.add(junction);
    }
    
    public Junction getJunction(String id) {
        
        for(Junction j : junctions) {
            if(j.getId().equalsIgnoreCase(id)) {
                return j;
            }
        }
        return null;
    }
}