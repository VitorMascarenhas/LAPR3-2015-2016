/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author rekgnyz
 */
public class Segment {
    
    private int segmentIndex;
    private double initialHeight;
    private float slope;
    private float length;
    private int minimumVelocity;
    private int maximumVelocity;
    private int maximumVehiclesNumber;

    //Dummy constructor for unit tests;
    public Segment() {
        this.length = 0;
    }

    public Segment(int segmentIndex, double initialHeight, float slope, float length, int minimumVelocity, int maximumVelocity, int maximumVehiclesNumber) {
        this.segmentIndex = segmentIndex;
        this.initialHeight = initialHeight;
        this.slope = slope;
        this.length = length;
        this.minimumVelocity = minimumVelocity;
        this.maximumVelocity = maximumVelocity;
        this.maximumVehiclesNumber = maximumVehiclesNumber;
    }
    
    public int getSegmentIndex() {
        return this.segmentIndex;
    }
    
    public double getInitialHeight() {
        return this.initialHeight;
    }
    
    public float getSlope() {
        return this.slope;
    }
    
    public float getLength() {
        return this.length;
    }
    
    public int getMinimumVelocity() {
        return this.minimumVelocity;
    }
    
    public int getMaximumVelocity() {
        return this.maximumVelocity;
    }
    
    public int getMaximumVehiclesNumber() {
        return this.maximumVehiclesNumber;
    }
    
    public void setLength(float length) {
        this.length = length;
    }

    public float getMaximumVelocity(float maxVehicle) {
        if(maxVehicle>0)
            return (maximumVelocity < maxVehicle ? maximumVelocity : maxVehicle);
        else
            return maximumVelocity;
    }

    public void setMaximumVelocity(int maximumVelocity) {
        this.maximumVelocity = maximumVelocity;
    }

    public Segment getMirroredSegment() {

        float mSlope = 0 - this.slope;
        double finalHeight;
        double temp = (Math.sin(Math.toRadians(slope)) * length);
        if (slope < 0) {
            finalHeight = initialHeight - temp;
        } else {
            finalHeight = initialHeight + temp;
        }

        Segment mirrored = new Segment(segmentIndex, finalHeight, mSlope, length, minimumVelocity, maximumVelocity, maximumVehiclesNumber);

        return mirrored;
    }

    @Override
    public String toString() {
        return "Index: " + segmentIndex + " - Height: " + initialHeight + " - Slope: " + slope + " - length: " + length + " - Minimum Velocity: " + minimumVelocity + " - Maximum Velocity: " + maximumVelocity + " - Maximum Vehicles Number: " + maximumVehiclesNumber;
    }
}
