/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import Graph.Edge;
import Graph.GraphAlgorithms;
import Model.Junction;
import Model.RoadNetwork;
import Model.Section;
import Model.Vehicle;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

/**
 *
 * @author 11011_000
 */
public class ShortestPath implements BestPathStrategy {

    @Override
    public double getBestPath(ArrayList<Junction> junctions, ArrayList<Section> sections, Junction j0, Junction j1, Vehicle vehicle, Deque<Junction> bestPathJunctions, Deque<Section> bestPathSections) {

        RoadNetwork roadNetwork = new RoadNetwork();
        roadNetwork.createRoadNetwork(junctions, sections, vehicle, "sectionLength");
        
        double dist = GraphAlgorithms.shortestPath(roadNetwork.getRoadNetwork(), j0, j1, bestPathJunctions);
        
        Iterator<Junction> it=bestPathJunctions.iterator();
        Edge edge;
        
        while(bestPathJunctions.size()>1) {
            Junction orig = bestPathJunctions.removeFirst();
            Junction dest = bestPathJunctions.getFirst();
            edge = roadNetwork.getRoadNetwork().getEdge(roadNetwork.getRoadNetwork().getVertex(orig), roadNetwork.getRoadNetwork().getVertex(dest));
            bestPathSections.add((Section) edge.getElement());
        }
        
        return dist;
    }

}
