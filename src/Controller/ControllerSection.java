/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package Controller;



import Model.Junction;
import Model.Section;

import Model.Segment;

import Model.Vehicle;

import Model.Wind;

import java.util.ArrayList;




/**

 *

 * @author vitoralexandremascarenhasmascarenhas

 */

public class ControllerSection {

    

    public ControllerSection() {

    }

    

    public Section createSection(String jOrig, String jDest, String road, String typology, String direction, String toll, String speed, String angle, ArrayList<Segment> segments, ArrayList<Vehicle> vehicles, ArrayList<Junction> junctions) {

         if(!(jOrig.equalsIgnoreCase("")) || !(jDest.equalsIgnoreCase("")) || !(road.equalsIgnoreCase("")) || !(typology.equalsIgnoreCase("")) || !(direction.equalsIgnoreCase("")) || !(toll.equalsIgnoreCase("")) || !(speed.equalsIgnoreCase("")) || !(angle.equalsIgnoreCase("")) || segments!=null || junctions!=null){
            
            String vec[];
            if(road.contains("\"")){
                 vec = road.split("\"");
                 road = vec[1];
            }
             
            vec = speed.split("m/s");
            float spd = Float.parseFloat(vec[0]);
            spd = spd*3600/1000;

            float ang = Float.parseFloat(angle);

            Wind wind = new Wind(spd, ang);

            String typo="";

            if(typology.contains("urban")||typology.contains("URBAN")){

                typo = "URBAN";

            }else{ 

                if(typology.contains("regular")||typology.contains("REGULAR")){

                    typo = "REGULAR";

                }else{ 

                    if(typology.contains("express")||typology.contains("EXPRESS")){

                        typo = "EXPRESS";

                    }else{ 

                        if(typology.contains("highway")||typology.contains("HIGHWAY")){

                            typo = "HIGHWAY";

                        }

                    }

                }

            }
                
            Float tll = Float.parseFloat(toll);

            Section section = new Section(getJunctionById(junctions, jOrig), getJunctionById(junctions, jDest), road, typo, direction.toUpperCase(), tll, wind, segments, vehicles);

            return section;

         }else{
             return null;
         }
    }
    
    private static Junction getJunctionById(ArrayList<Junction> junctions, String id){
        int index = -1;
        for(Junction j : junctions){
            if(j.getId().equalsIgnoreCase(id)){
                index = junctions.indexOf(j);
            }
        }
        if(index == -1){
            return null;
        }else{
            return junctions.get(index);
        }
    }

}