/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.TrafficPattern;

/**
 *
 * @author rekgnyz
 */
public class TrafficPatternFactory {
    public TrafficPatternFactory(){}
    
    public TrafficPattern createTrafficPattern(String beginNode, String endNode, String vehicleName, String arrivalRate){
        
        
        if(!(beginNode.equalsIgnoreCase("")) || !(endNode.equalsIgnoreCase("")) || !(vehicleName.equalsIgnoreCase("")) || !(arrivalRate.equalsIgnoreCase(""))){
            /*ArrivelRate Parse*/
            String vec[];
            String vec2[];
            vec = arrivalRate.split(" ");
            int value = Integer.parseInt(vec[0]);

            /*Converte para segundos*/
            if(vec[1].contains("m")){
                value = value*60;
            }
            if(vec[1].contains("h")){
                value = value*3600;
            }

            return new TrafficPattern(beginNode, endNode, vehicleName, value);
        }else{
            return null;
        }
    }
}
