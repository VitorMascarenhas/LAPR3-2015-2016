/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Energy;
import Model.Gear;
import Model.Throttle;
import java.util.ArrayList;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class EnergiesFactory {
    
    public EnergiesFactory() {
    }
    
    public Energy createEnergy( String minRpm, String maxRpm, String finalDriveRatio, ArrayList<Gear> gearList, ArrayList<Throttle> throttleList, String energyRegenerationRatio) {
        
   
        int mnRpm = Integer.parseInt(minRpm);
        int mxRpm = Integer.parseInt(maxRpm);
        float fnlDR = Float.parseFloat(finalDriveRatio);
        float err = Float.parseFloat(energyRegenerationRatio);
        
        Energy energy = new Energy(mnRpm, mxRpm, fnlDR, gearList, throttleList, err);
        
        return energy;
    }
}