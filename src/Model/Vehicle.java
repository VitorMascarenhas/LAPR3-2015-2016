/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Velocity.Typology;
import java.util.ArrayList;

/**
 *
 * @author 1081320
 */
public class Vehicle {
    
    private String name;
    private String description;
    private VehicleType type;
    private float load;
    private float drag;
    private float rrc;
    private float wheelSize;
    private ArrayList<Velocity> velocities;
    private Energy energy;
    private float mass;
    private Engine engine;
    private float frontal_area;
    
    //Construtor Temporário
    public Vehicle(String name, String description, String vehicleType, float load, float drag, float rrc, float wheelSize, ArrayList<Velocity> velocities, Energy energy, float mass, Engine engine, float frontal_area) {
        this.name = name;
        this.description = description;
        this.type = VehicleType.valueOf(vehicleType.toUpperCase());
        this.load = load;
        this.drag = drag;
        this.rrc = rrc;
        this.wheelSize = wheelSize;
        this.velocities = velocities;
        this.energy = energy;
        this.mass = mass;
        this.engine = engine;
        this.frontal_area = frontal_area;
    }
    
    public Vehicle() {}
    
    public Vehicle(String name, String description, VehicleType type, float load, float drag, float rrc, float wheelSize, ArrayList<Velocity> velocity, Energy energy, float mass, Engine engine, float frontal_area) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.load = load;
        this.drag = drag;
        this.rrc = rrc;
        this.wheelSize = wheelSize;
        this.velocities = velocity;
        this.energy = energy;
        this.mass = mass;
        this.engine = engine;
        this.frontal_area = frontal_area;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * @return the type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * @return the load
     */
    public float getLoad() {
        return load;
    }

    /**
     * @return the drag
     */
    public float getDrag() {
        return drag;
    }

    /**
     * @return the rrc
     */
    public float getRrc() {
        return rrc;
    }

    /**
     * @return the wheelSize
     */
    public float getWheelSize() {
        return wheelSize;
    }

    /**
     * @return the velocities
     */
    public ArrayList<Velocity> getVelocities() {
        return velocities;
    }

    /**
     * @return the energy
     */
    public Energy getEnergy() {
        return energy;
    }

    /**
     * @return the mass
     */
    public float getMass() {
        return mass;
    }

    /**
     * @return the engine
     */
    public Engine getEngine() {
        return engine;
    }
    
    public float getTorque(int throttle, int rpm) {
        Throttle t = energy.getThrottle(throttle); 
        return t.getTorque(rpm);
    }
    
    public float getFinalRatio() {
        return energy.getFinalDriveRatio();
    }
    
    public float getGearRatio(int kTh) {
        return energy.getGearRatio(kTh);
    }
    
    public float getFrontalArea() {
        return frontal_area;
    }
    
    public enum VehicleType {
        CAR, TRUCK, TRACTOR, SEMITRAILER, MOTORCYCLE;
    }
    
    public boolean equals(Vehicle v){
        if(this.description.equalsIgnoreCase(v.description) && this.drag == v.drag && this.energy.equals(v.energy) && this.engine.equals(v.engine) && this.frontal_area == v.frontal_area && this.load == v.load && this.mass ==v.mass && this.rrc == v.rrc && this.type==v.type && this.containsSameVelocities(v.velocities) && this.wheelSize==v.wheelSize){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String toString() {
        return name + "__" + description + "__" + type.toString();
    }
    
    public float returnVelocityLimitFromSegment(String in){
        float res = -1;
        for(Velocity v : this.velocities){
            if(Typology.valueOf(in).equals(v.getTypology())){
                res = v.getLimit();
            }
        }
        return res;
    }
    
    public void repleaceVehicleName(String name, int cont){
        this.name = name+cont;
    }
    
    public boolean containsSameVelocities(ArrayList<Velocity> velocities){
        boolean exist = true;
        for(Velocity v : velocities){
            exist = existVelocity(v);
        }
        return exist;
    }
    
    public boolean existVelocity(Velocity v){
        int cont = 0;
        for(Velocity v2 : this.velocities){
            if((v.equals(v2))){
               cont++;
            }
        }
        if(cont == 0){
            return false;
        }else{
            return true;
        }
    }

}
