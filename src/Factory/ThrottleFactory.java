/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Regime;
import Model.Throttle;
import java.util.ArrayList;

/**
 *
 * @author rekgnyz
 */
public class ThrottleFactory {
    public ThrottleFactory(){
    }
    
    public Throttle createThrottle(String id, ArrayList<Regime> regimes){
        return new Throttle(Integer.parseInt(id), regimes);
    }
}
