/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class Energy {
    
    private int minRpm;
    private int maxRpm;
    private float finalDriveRatio;
    private ArrayList<Gear> gearList;
    private ArrayList<Throttle> throttleList;
    private float energyRegenerationRatio;
    
    public Energy(int minRpm, int maxRpm, float finalDriveRatio, ArrayList<Gear> gearList, ArrayList<Throttle> throttleList, float energyRegenerationRatio) {
        this.minRpm = minRpm;
        this.maxRpm = maxRpm;
        this.finalDriveRatio = finalDriveRatio;
        this.gearList = gearList;
        this.throttleList = throttleList;
        this.energyRegenerationRatio = energyRegenerationRatio;
    }

    /**
     * @return the minRpm
     */
    public int getMinRpm() {
        return minRpm;
    }

    /**
     * @return the maxRpm
     */
    public int getMaxRpm() {
        return maxRpm;
    }

    /**
     * @return the finalDriveRatio
     */
    public float getFinalDriveRatio() {
        return finalDriveRatio;
    }
    
    public Throttle getThrottle(int throttle) {
        for(Throttle t : getThrottleList())
            if(t.getId()==throttle)
                return t;
        return null;
    }
    
    public float getGearRatio(int kTh) {
        for(Gear g : gearList)
            if(g.getId()==kTh)
                return g.getRatio();
        return -1.0f;
    }
    
    public int getFinalGear() {
        return gearList.get(gearList.size()-1).getId();
    }
    
    public boolean equals(Energy e){
        if(this.minRpm == e.minRpm && this.maxRpm ==e.maxRpm && this.finalDriveRatio == e.finalDriveRatio  && this.containsSameGears(e.gearList) && this.containsSameThrottles(e.getThrottleList()) && this.getEnergyRegenerationRatio() == e.getEnergyRegenerationRatio()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return the gearList
     */
    public ArrayList<Gear> getGearList() {
        return gearList;
    }
    
    public boolean containsSameGears(ArrayList<Gear> gears){
        boolean exist = true;
        for(Gear v : gears){
            exist = existGear(v);
        }
        return exist;
    }
    
    public boolean existGear(Gear v){
        int cont= 0;
        for(Gear v2 : this.gearList){
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
    
    public boolean containsSameThrottles(ArrayList<Throttle> throttles){
        boolean exist = true;
        for(Throttle v : throttles){
            exist = existTrhottle(v);
        }
        return exist;
    }
    
    public boolean existTrhottle(Throttle v){
        int cont = 0;
        for(Throttle v2 : this.getThrottleList()){
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

    /**
     * @return the energyRegenerationRatio
     */
    public float getEnergyRegenerationRatio() {
        return energyRegenerationRatio;
    }

    /**
     * @param energyRegenerationRatio the energyRegenerationRatio to set
     */
    public void setEnergyRegenerationRatio(float energyRegenerationRatio) {
        this.energyRegenerationRatio = energyRegenerationRatio;
    }

    /**
     * @return the throttleList
     */
    public ArrayList<Throttle> getThrottleList() {
        return throttleList;
    }

    /**
     * @param throttleList the throttleList to set
     */
    public void setThrottleList(ArrayList<Throttle> throttleList) {
        this.throttleList = throttleList;
    }
}