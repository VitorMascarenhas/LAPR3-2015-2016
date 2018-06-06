/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Combustion;
import Model.Combustion.FuelType;
import Model.Electric;
import Model.Engine;
import Model.Hybrid;
/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class EngineFactory {
    
    public EngineFactory() {
    }
    
    public Engine getEngine(String typeEngine, String type) {
        if(!(type.equalsIgnoreCase(""))){
            if(typeEngine.equalsIgnoreCase("combustion")) {
                return new Combustion(type);
            }
            if(typeEngine.equalsIgnoreCase("electric")) {
                return new Electric(type);
            }
            if(typeEngine.contains(" ")) {
                return new Hybrid(type);
            }
            return null;
        }else{
            return null;
        }
    }
}