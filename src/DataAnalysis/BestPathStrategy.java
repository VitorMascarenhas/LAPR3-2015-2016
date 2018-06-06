/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import Model.Junction;
import Model.Section;
import Model.Vehicle;
import java.util.ArrayList;
import java.util.Deque;

/**
 *
 * @author 11011_000
 */
public interface BestPathStrategy {
    
    public double getBestPath(ArrayList<Junction> junctions, ArrayList<Section> sections, Junction j0, Junction j1, Vehicle vehicle, Deque<Junction> bestPathJunctions, Deque<Section> bestPathSections);
    
}
