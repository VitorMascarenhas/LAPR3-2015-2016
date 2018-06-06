/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataAnalysis.CarPhysics;
import Graph.Edge;
import Graph.Graph;
import java.util.ArrayList;

/**
 *
 * @author 1081320
 */
public class RoadNetwork {

    private Graph<Junction, Section> roadNetwork;
    private CarPhysics physics = new CarPhysics();

    public RoadNetwork() {
        this.roadNetwork = new Graph<>(true);
    }

    //Default Graph without vehicles
    /*public void createRoadNetwork(ArrayList<Junction> junctions, ArrayList<Section> sections) {
        this.createJunctionNodes(junctions);
        this.createSectionEdges(sections);
    }*/

    // Graph with given weight type and with vehicles 
    public void createRoadNetwork(ArrayList<Junction> junctions, ArrayList<Section> sections, Vehicle vehicle, String weigth) {
        this.createJunctionNodes(junctions);
        this.createSectionEdges(sections, vehicle, weigth);
    }


    private void createJunctionNodes(ArrayList<Junction> junctions) {

        for (Junction junction : junctions) {
            roadNetwork.insertVertex(junction);
        }

    }

/*    private void createSectionEdges(ArrayList<Section> sections) {        
        for (Section section : sections){
            roadNetwork.insertEdge(section.getStartingNode(), section.getEndingNode(), section, section.getLength());
            if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                    Section mirror = section.getMirroredSection();
                    roadNetwork.insertEdge(mirror.getStartingNode(), mirror.getEndingNode(), mirror, mirror.getLength());
                }
        }
    }*/

    
    private void createSectionEdges(ArrayList<Section> sections, Vehicle vehicle, String weigth) {        
        switch(weigth) {
            case "sectionLength":
                for (Section section : sections){
                    roadNetwork.insertEdge(section.getStartingNode(), section.getEndingNode(), section, section.getLength());
                    if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                            Section mirror = section.getMirroredSection();
                            roadNetwork.insertEdge(mirror.getStartingNode(), mirror.getEndingNode(), mirror, mirror.getLength());
                        }
                }
                break;
            case "travelTime":
                for (Section section : sections){
                    roadNetwork.insertEdge(section.getStartingNode(), section.getEndingNode(), section, section.getTravelTime(vehicle));
                    if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                            Section mirror = section.getMirroredSection();
                            roadNetwork.insertEdge(mirror.getStartingNode(), mirror.getEndingNode(), mirror, mirror.getTravelTime(vehicle));
                        }
                }
            break;
            case "workMostEfficient":
                double vWork=0.0f;
                Wind wind;
                float velocity = 0.0f;
                
                for (Section section : sections){
                    wind = section.getWind();
                    for (Segment segment : section.getSegments()){
                        velocity = segment.getMaximumVelocity(vehicle.returnVelocityLimitFromSegment(section.getTypology().toString()));                     
                        vWork = (double) physics.theoricalVehicleWork(vehicle, velocity, segment, wind);
                        roadNetwork.insertEdge(section.getStartingNode(), section.getEndingNode(), section, vWork);
                        if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                            Section mirror = section.getMirroredSection();
                            for (Segment mSegment : mirror.getSegments()){
                                velocity = vehicle.returnVelocityLimitFromSegment(mirror.getTypology().toString());                        
                                vWork = (float) physics.theoricalVehicleWork(vehicle, velocity, mSegment, mirror.getWind());                            
                                roadNetwork.insertEdge(mirror.getStartingNode(), mirror.getEndingNode(), mirror, vWork);
                            }
                        }
                    }
                }
            break;
        }
    }
    
    public Graph getRoadNetwork() {
        return this.roadNetwork;
    }

    public int getNumJunctions() {
        return this.roadNetwork.numVertices();
    }

    public int getNumSections() {
        return this.roadNetwork.numEdges();
    }

    public ArrayList<Section> getSections(){
        Section s;
        ArrayList<Section> sections = new ArrayList();
        for(Edge e : roadNetwork.edges()){
            s = (Section) e.getElement();
            sections.add(s);
        }
        return sections;
    }

}
