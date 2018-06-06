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
public class Section {

    public enum Direction {

        DIRECT,
        REVERSE,
        BIDIRECTIONAL;
        
    }

    public enum Typology {
        REGULAR,
        URBAN,
        EXPRESS,
        CONTROLLED,
        HIGHWAY;
    }

    private Junction startingNode;
    private Junction endingNode;
    private String road;
    private Typology typology;
    private Direction direction;
    private float toll;
    private Wind wind;
    private ArrayList<Segment> segments;
    private ArrayList<Vehicle> vehicles;
    
    /**
     * @return the startingNode
     */
    public Junction getStartingNode() {
        return startingNode;
    }

    /**
     * @return the endingNode
     */
    public Junction getEndingNode() {
        return endingNode;
    }

    /**
     * @return the road
     */
    public String getRoad() {
        return road;
    }

    /**
     * @return the typology
     */
    public Typology getTypology() {
        return typology;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return the toll
     */
    public float getToll() {
        return toll;
    }

    /**
     * @return the wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @return the segments
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

    /**
     * @return the vehicles
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    
    public Section(Junction startingNode, Junction endingNode, String road, String typology, String direction, float toll, Wind wind, ArrayList<Segment> segments, ArrayList<Vehicle> vehicles) {
        this.startingNode = startingNode;
        this.endingNode = endingNode;
        this.road = road;
        this.typology = Typology.valueOf(typology.toUpperCase());
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.toll = toll;
        this.wind = wind;
        this.segments = segments;
        this.vehicles = vehicles;
    }
    
    public double getLength() {
        
        double totalLength=0;
        
        for(Segment segment : segments)
            totalLength+=segment.getLength();
        
        return totalLength;
    }
    
    public double getTravelTime(Vehicle vehicle) {
        double travelTime=0;
        for(Segment segment : getSegments())
            travelTime+=segment.getLength()/segment.getMaximumVelocity(vehicle.returnVelocityLimitFromSegment(typology.toString()));

        return travelTime*60;
    }
    
    public Section getMirroredSection(){
        
        Wind mWind = new Wind(0-wind.getAngle(), 0-wind.getSpeed());
        ArrayList<Segment> mSegments = new ArrayList<>();
        
        for (Segment segment : segments) {
            mSegments.add(segment.getMirroredSegment());
        }
        
        Section mirrored = new Section(endingNode, startingNode, road, this.typology.toString(), this.direction.toString(), toll, mWind, mSegments, vehicles);
           
        return mirrored;
    }
       
    @Override
    public String toString(){
        return startingNode.toString() + " - " + endingNode.toString() + " - Road: " + road + " - Typology: " + typology + " - Direction: " + direction.toString() + " - Toll: " + toll + " - " + wind.toString();
    }
    
    public boolean equals(Section s){
        if(s.startingNode.equals(this.startingNode) && s.endingNode.equals(this.endingNode) && this.road.equalsIgnoreCase(s.road)){
            return true;
        }else{
            return false;
        }
    }
}