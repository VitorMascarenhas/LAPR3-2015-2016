/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import DataAnalysis.CarPhysics;
import Model.Junction;
import Model.Project;
import Model.Section;
import Model.Segment;
import Model.Vehicle;
import Model.Wind;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suq-madik
 */
public class Html {
    public static void exportSimulation(Project p, double result, Deque<Junction> bestPathJunctions, Deque<Section> bestPathSections, Vehicle vehicle) throws IOException{
        String pro = prologo(p.toString());
        String epi = epilogo();
        String center = center(p);
        String bestPath = bestPath(p, result, bestPathJunctions, bestPathSections, vehicle);
        String html = pro + center + bestPath + epi;
        File htmlFile = new File(p.getId()+p.getDescription()+".html");
        try (Formatter fo = new Formatter ()) {
            fo.format(html);
            fo.close();
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Html.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String prologo(String title){
        String pro = " <!DOCTYPE html>\n" +
                     " <html>\n" +
                     "  <head>\n" +
                     "      <title>"+ title +"</title>\n" +
                     "  </head>\n" +
                     "  <body>\n";
        return pro;
    }
    
    public static String epilogo(){
        String epi = "  </body>\n" +
                     "</html> ";
        return epi;
    }
    
    public static String center(Project p){
        String htmlCenter = "<h5>ROAD NETWORK</h5>";
        htmlCenter += "<table border=\"1\">" +
                            "  <tr>\n" +
                            "    <th>Junctions</th>\n" +
                            "  </tr>\n";
        
        for(Junction j : p.getJunctions()){
            htmlCenter +=   "  <tr>\n" +
                            "    <td>"+ j.toString() + "</td>\n" +
                            "  </tr>"; 
        }
        htmlCenter +="</table>";
        
        
        htmlCenter += "<table border=\"1\">" +
                        "  <tr>\n" +
                        "    <th>Sections</th>\n" +
                        "    <th>Segments</th>\n" +
                        "  </tr>\n";
        for(Section s : p.getSections()){
            htmlCenter +=   "  <tr>\n" +
                            "    <td>" + s.toString() +"</td>\n" +
                            "    <td>" + htmlSegments(s) + "</td>\n" +
                            "  </tr>\n";
            
        }
        htmlCenter +="</table>";
        return htmlCenter;
    }
    
    public static String htmlSegments(Section s){
        String ret = "";
        for(Segment z : s.getSegments()){
            ret += z.toString() + "</br>";
        }
        return ret;
    }
    
    public static String bestPath(Project model, double result, Deque<Junction> bestPathJunctions, Deque<Section> bestPathSections, Vehicle vehicle){
        CarPhysics physics = new CarPhysics();
        String ret = "";
        float distance = 0;
        float travelTime=0;
        double energy=0.0;
        float toll=0;
        float velocity = 0.0f;
        Wind wind;

        ArrayList<String> path = new ArrayList();
        for(Section sec : bestPathSections){
            distance+=sec.getLength();
            travelTime+=sec.getTravelTime(vehicle);
            wind = sec.getWind();
            toll+=sec.getToll();

            for(Segment segment : sec.getSegments()){
                    velocity = segment.getMaximumVelocity(vehicle.returnVelocityLimitFromSegment(sec.getTypology().toString()));                     
                    energy = (double)physics.theoricalVehicleWork(vehicle, velocity, segment, wind);
            }
            path.add("from " + sec.getStartingNode().getId() + " take " + sec.getRoad() + "  to " + sec.getEndingNode().getId() + " for " + String.format("%.1f",sec.getLength()) + " km");
        }
        
        ret+= "<h5>PATH</h5>";
        
        /*Imprime caminho*/
        ret += "<table border=\"1\">\n" +
            "  <tr>\n" +
            "	<th>Order</th>\n" +
            "    <th>Section</th>\n" +
            "  </tr>";
        
        int cont = 1;
        
        for(String s : path){
            ret += "  <tr>\n" +
                    "    <td>"+cont+"</td>\n" +
                    "    <td>"+s+"</td> \n" +
                    "  </tr>";
            cont++;
        }
   
        ret += "</table>";
        
        
        /*Imprime resultados*/
        ret+= "<h5>RESULTS</h5>";
        
        ret+="<table border=\"1\">\n" +
                    "  <tr>\n" +
                    "    <th>Distance</th>\n" +
                    "    <th>Travel Time</th> \n" +
                    "    <th>Energy</th>\n" +
                    "	<th>Toll</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>"+distance+" km</td>\n" +
                    "    <td>"+travelTime+" m</td> \n" +
                    "    <td>"+energy+" J</td>\n" +
                    "	<td>"+toll+" &euro;</td>\n" +
                    "  </tr>\n" +
                    "</table>";
        
        return ret;
    }
    
}
