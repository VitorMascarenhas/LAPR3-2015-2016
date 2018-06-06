/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author 11011_000
 */
public class Project {
    
    private String id;
    private String description;
    private ArrayList<Junction> junctions;
    private ArrayList<Section> sections;
    //private RoadNetwork roadNetwork = new RoadNetwork();
    private ArrayList<Vehicle> vehicles = new ArrayList();
    
    public Project(String id, String description, ArrayList<Junction> junctions, ArrayList<Section> sections) {
        this.id=id;
        this.description=description;
        this.junctions=junctions;
        this.sections=sections;
    }
    
    @Override
    public String toString(){
        return "id: " + getId() + " description: " + getDescription(); 
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id=id;
    }
    
    public void setDescription(String description) {
        this.description=description;
    }
    
    public ArrayList<Junction> getJunctions(){
        return junctions;
    }
    
    public ArrayList<Section> getSections(){
        return sections;
    }

    public ArrayList<Vehicle> getVehicles(){
        return vehicles;
    }

    
    /**
     * @return the roadNetwork
     */
    /*public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }*/

    /**
     * Method used to add vehicles
     * @param vehicles the vehicles to set
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        for(Vehicle v : vehicles){
            if(checkVehicleExistence(v)== false){
                checkVehicleNameExistance(v);
                this.vehicles.add(v);
            }
        }
    }
    
    /**
     * Method to check if an vehicle already exist in the vehicle list
     * @param veic
     * @return 
     */
    public boolean checkVehicleExistence(Vehicle veic){
        int cont = 0;
        String name = veic.getName();
        
        for(Vehicle v: this.vehicles){
            if(v.equals(veic)){
                cont++;
            }
        }
        
        if(cont == 0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Method to check if an vehicle name is already in use
     * @param veic 
     */
    public void checkVehicleNameExistance(Vehicle veic){
        int cont = 1;
        int cont2;
        String name = veic.getName();
        boolean verify = false;
        
        do{
            cont2 = cont;
            for(Vehicle v : this.vehicles){
                if(v.getName().equalsIgnoreCase(veic.getName())){
                    veic.repleaceVehicleName(name, cont);
                    cont++;
                }
            }
        }while(cont2 != cont);
    }
    
    /**
     * Method to check if the section already exist in the network
     * @param sec
     * @return 
     */
    public boolean checkSectionExistence(Section sec){
        int cont = 0;
        for(Section s: this.sections){
            if(s.equals(sec)){
                cont++;
            }
        }
        
        if(cont == 0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Method add a new sections to an existent project
     * @param sections 
     */
    public void setSections(ArrayList<Section> sections) {
        for(Section s : sections){
            if(checkSectionExistence(s)== false){
                this.sections.add(s);
            }
        }
    }
    
    
    public void setJunctions(ArrayList<Junction> junctions) {
        for(Junction j : junctions){
            if(checkJunctionExistence(j)== false){
                this.junctions.add(j);
            }
        }
    }
    
     public boolean checkJunctionExistence(Junction junc){
        int cont = 0;
        for(Junction j: this.junctions){
            if(j.equals(junc)){
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
