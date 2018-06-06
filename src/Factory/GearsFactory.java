/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Gear;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class GearsFactory {
    
    public GearsFactory() {
    }
    
    public Gear createGear(String id, String ratio) {
        
        if(id.equalsIgnoreCase("") || ratio.equalsIgnoreCase("") || id.isEmpty() || ratio.isEmpty()){
            return null;
           
        }else{
            int ident = Integer.parseInt(id);
            float rt = Float.parseFloat(ratio);
            Gear g = new Gear(ident, rt);
            return g;
        }
    }
}