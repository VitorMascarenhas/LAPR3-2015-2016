/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package Controller;



import Model.Segment;

import java.util.ArrayList;



/**

 *

 * @author vitoralexandremascarenhasmascarenhas

 */

public class ControllerSegments {

    

    public ControllerSegments() {

    }

    

    public Segment newSegment(String segmentIndex, String initialHeigth, String slope, String length, String minimumVelocity, String maxVelocity, String maxVehiclesNumber) {
        
        if(!(segmentIndex.equalsIgnoreCase("")) || !(initialHeigth.equalsIgnoreCase("")) || !(slope.equalsIgnoreCase("")) || !(length.equalsIgnoreCase("")) || !(minimumVelocity.equalsIgnoreCase("")) || !(maxVelocity.equalsIgnoreCase("")) || !(maxVehiclesNumber.equalsIgnoreCase(""))){
            int segmIndex = Integer.parseInt(segmentIndex);

            double initHeight = Double.parseDouble(virgulaParaPonto(initialHeigth));

            String vec[] = slope.split("%");
            float slp = Float.parseFloat(virgulaParaPonto(vec[0]));

            vec = length.split("Km");
            float lgth = Float.parseFloat(virgulaParaPonto(vec[0].trim()));

            vec = minimumVelocity.split("Km/h");
            int minVel = Integer.parseInt(vec[0].trim());

            vec = maxVelocity.split("Km/h");
            int maxVel = Integer.parseInt(vec[0].trim());

            int maxVehicles = Integer.parseInt(maxVehiclesNumber);
            
            Segment segment = new Segment(segmIndex, initHeight, slp, lgth, minVel, maxVel, maxVehicles);
            return segment;
            
        }else{
            return null;
        }

    }
    
    private static String virgulaParaPonto(String s){
        String vec[];
        if(s.contains(",")){
            vec = s.split(",");
            s=vec[0]+"."+vec[1];
        }
        return s;
    }

}