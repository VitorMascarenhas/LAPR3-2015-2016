/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Regime;

/**
 *
 * @author rekgnyz
 */
public class RegimeFactory {
    public RegimeFactory(){
    }
    
    public Regime createRegime(String torque, String rpm_low, String rpm_high, String sfc){
        if(torque.equalsIgnoreCase("") || rpm_low.equalsIgnoreCase("") || rpm_high.equalsIgnoreCase("")){
            return null;
        }else{
            int torq = Integer.parseInt(torque);
            int rpmLow = Integer.parseInt(rpm_low);
            int rpmHigh = Integer.parseInt(rpm_high);
            float sfc_ = Float.parseFloat(sfc);

            return new Regime(torq, rpmLow, rpmHigh, sfc_);
        }
    }
}
