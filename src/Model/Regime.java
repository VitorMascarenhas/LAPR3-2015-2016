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
public class Regime {
    private int torque;
    private int rpm_low;
    private int rpm_high;
    private float sfc;
    
    public Regime(int torque, int rpm_low, int rpm_high, float sfc){
        this.torque = torque;
        this.rpm_low = rpm_low;
        this. rpm_high = rpm_high;
        this.sfc = sfc;
    }
    
    public int getTorque() {
        return this.torque;
    }
    
    public boolean betweenRPM(int engineSpeed) {
        if (engineSpeed>=this.getRpm_low() && engineSpeed<=this.getRpm_high())
            return true;
        return false;
    }
    
    public boolean equals(Regime r){
        if(this.torque == r.torque && this.getRpm_low() == r.getRpm_low() && this.getRpm_high() == r.getRpm_high() && this.getSfc() == r.getSfc()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return the rpm_low
     */
    public int getRpm_low() {
        return rpm_low;
    }

    /**
     * @return the rpm_high
     */
    public int getRpm_high() {
        return rpm_high;
    }

    /**
     * @return the sfc
     */
    public float getSfc() {
        return sfc;
    }
}
