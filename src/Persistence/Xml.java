/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Controller.ControllerSection;
import Controller.ControllerSegments;
import Controller.JunctionController;
import Factory.EnergiesFactory;
import Factory.EngineFactory;
import Factory.GearsFactory;
import Factory.RegimeFactory;
import Factory.SimulationFactory;
import Factory.ThrottleFactory;
import Factory.TrafficPatternFactory;
import Factory.VehicleFactory;
import Factory.VelocityFactory;
import Model.Energy;
import Model.Engine;
import Model.Gear;
import Model.Junction;
import Model.Project;
import Model.Regime;
import Model.Section;
import Model.Segment;
import Model.Simulation;
import Model.Throttle;
import Model.TrafficPattern;
import Model.Vehicle;
import Model.Velocity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 1060708
 */
public class Xml implements IO {
    
    /**
     * Method that imports an xml file to a project
     * @return Project or null if not possible to crate the project
     */
    @Override
    public Project importNetworkFile(File f){
        boolean ifCorrupted = false;
        Project prj = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        ArrayList<Junction> junctions = new ArrayList<Junction>();
        ArrayList<Section> sections = new ArrayList<Section>();
        
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            String networkId="NoId";
            String networkDescription="NoDescription";
            String nodeId;
            
            NodeList networks = doc.getElementsByTagName("Network");
            if(networks.getLength()==0){
                ifCorrupted = true;
            }else{
                Element network = (Element) networks.item(0);
                networkId = network.getAttribute("id");
                networkDescription = network.getAttribute("description");
                
                /*node_list*/
                NodeList nodesList = network.getElementsByTagName("node_list");
                if(nodesList.getLength()== 0){
                    ifCorrupted = true;
                }else{
                    Element nodeList0 = (Element) nodesList.item(0);
                    NodeList junctionElementsList = nodeList0.getElementsByTagName("node");
                    if(junctionElementsList.getLength() == 0){
                        ifCorrupted = true;
                    }else{
                        for(int i = 0; i < junctionElementsList.getLength(); i++){
                            Element node = (Element) junctionElementsList.item(i);
                            nodeId = node.getAttribute("id");
                            if(nodeId.equalsIgnoreCase("")){
                                ifCorrupted = true;
                            }else{
                                junctions.add(new Junction(nodeId));
                            }
                        }
                    }
                }
                
                /*section_list*/
                NodeList sectionsList = network.getElementsByTagName("section_list");
                if(sectionsList.getLength()== 0){
                    ifCorrupted = true;
                }else{
                    Element nodeList0 = (Element) sectionsList.item(0);
                    sections = sectionsFromXml(nodeList0, junctions);
                    if(sections == null){
                        ifCorrupted = true;
                    }
                }
            }
            
            if(ifCorrupted == true){
                return null;
            }else{
                prj = new Project(networkId, networkDescription, junctions, sections);
                return prj;
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Method that reads the sections of an XML file
     * @param in scanner to read file
     * @param junctions, list of nodes of the section
     * @return list of sections
     */
    public static ArrayList<Section> sectionsFromXml(Element sectionsElement, ArrayList<Junction> junctions){
        boolean ifCorrupted = false;
        ArrayList<Section> sections = new ArrayList<Section>();
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        ArrayList<Segment> segments = new ArrayList<Segment>();
        ControllerSection csection = new ControllerSection();
        String frase = "";
        String beginJunction="";
        String endJunction="";
        String vec[];
        String road="";
        String typology="";
        String direction="";
        String toll="";
        String wind_direction="";
        String wind_speed="";
        
        /*section_list*/
        NodeList sectionsNodeList = sectionsElement.getElementsByTagName("road_section");
        if(sectionsNodeList.getLength()== 0){
            ifCorrupted = true;
        }else{
            for(int i = 0; i< sectionsNodeList.getLength(); i++){
                Element sectionElement = (Element) sectionsNodeList.item(i);
                
                beginJunction = sectionElement.getAttribute("begin");
                endJunction = sectionElement.getAttribute("end");
                
                /*road*/
                if((sectionElement.getElementsByTagName("road")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    road = getElementContent(sectionElement, "road");
                }
                
                /*typology*/
                if((sectionElement.getElementsByTagName("typology")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    typology = getElementContent(sectionElement, "typology");
                }
                
                /*direction*/
                if((sectionElement.getElementsByTagName("direction")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    direction = getElementContent(sectionElement, "direction");
                }
                
                /*toll*/
                if((sectionElement.getElementsByTagName("toll")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    toll = getElementContent(sectionElement, "toll");
                }
                
                /*wind_direction*/
                if((sectionElement.getElementsByTagName("wind_direction")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    wind_direction = getElementContent(sectionElement, "wind_direction");
                }
                
                /*wind_speed*/
                if((sectionElement.getElementsByTagName("wind_speed")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    wind_speed = getElementContent(sectionElement, "wind_speed");
                }
                
                /*segment_list*/
                NodeList segmentsNodeList = sectionElement.getElementsByTagName("segment_list");
                if(segmentsNodeList.getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segments = segmentsFromXml((Element) segmentsNodeList.item(0));
                    if(segments == null){
                        ifCorrupted = true;
                    }
                }
                Section s = csection.createSection(beginJunction, endJunction, road, typology, direction, toll, wind_speed, wind_direction, segments, vehicles, junctions);
                if(s == null){
                    ifCorrupted = true;
                }else{
                    sections.add(s);
                }
            }
        }
                
        if(ifCorrupted == true){
            return null;
        }else{
            return sections;
        }
    }
    
    /**
     * Method that reads segments of XML file
     * @param in scanner that is reading a file
     * @return list of segments, of a certain section
     */
    public static ArrayList<Segment> segmentsFromXml(Element segmentsList){
        boolean ifCorrupted= false;
        ArrayList<Segment> segments = new ArrayList<Segment>();
        String frase = "";
        String vec[];
        String segment_id="";
        String segment_height="";
        String segment_slope="";
        String segment_length="";
        String segment_rrc="";
        String segment_minVelocity="";
        String segment_maxVelocity="";
        String segment_numberVehicles="";
        ControllerSegments cs = new ControllerSegments();
        
        NodeList segmentsNodeList = segmentsList.getElementsByTagName("segment");
        if(segmentsNodeList.getLength()== 0){
            ifCorrupted = true;
        }else{
            for(int i = 0; i< segmentsNodeList.getLength(); i++){
                Element segmentElement = (Element) segmentsNodeList.item(i);
                segment_id = segmentElement.getAttribute("id");
                
               /*height*/
                if((segmentElement.getElementsByTagName("height")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_height = getElementContent(segmentElement, "height");
                }
                
                /*slope*/
                if((segmentElement.getElementsByTagName("slope")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_slope = getElementContent(segmentElement, "slope");
                }
                
                /*length*/
                if((segmentElement.getElementsByTagName("length")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_length = getElementContent(segmentElement, "length");
                }
                
                /*max_velocity*/
                if((segmentElement.getElementsByTagName("max_velocity")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_maxVelocity = getElementContent(segmentElement, "max_velocity");
                }
                
                /*min_velocity*/
                if((segmentElement.getElementsByTagName("min_velocity")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_minVelocity = getElementContent(segmentElement, "min_velocity");
                }
                
                /*number_vehicles*/
                if((segmentElement.getElementsByTagName("number_vehicles")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    segment_numberVehicles = getElementContent(segmentElement, "number_vehicles");
                }
                
                Segment s = cs.newSegment(segment_id, segment_height, segment_slope, segment_length, segment_minVelocity, segment_maxVelocity, segment_numberVehicles);
                if(s==null){
                    ifCorrupted = true;
                }else{
                    segments.add(s);
                }
            }
        }
        
        if(ifCorrupted == true){
            return null;
        }else{
            return segments;
        }
    }
    
    
    @Override
    public ArrayList<Vehicle> importVehiclesFile(File f){
        
        boolean ifCorrupted = false;
        ArrayList<Vehicle> vehiclesArray = new ArrayList();
        VehicleFactory vf = new VehicleFactory();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder; 
        try {
            String type="";
            String motorization="";
            String fuel="";
            String load="";
            String drag="";
            String frontal_area="";
            String rrc="";
            String wheel_size="";
            String mass="";
            Energy enrg = null;
            Engine eng = null;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            
            NodeList vehicles = doc.getElementsByTagName("vehicle");
            for(int i = 0; i < vehicles.getLength(); i++){
                Node vehic = vehicles.item(i);
                Element vehicle = (Element) vehic;
                String vehicleName = vehicle.getAttribute("name");
                String vehicleDescription = vehicle.getAttribute("description");
                
                /*type*/
                if((vehicle.getElementsByTagName("type")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    type = getElementContent(vehicle, "type");
                    if(type.isEmpty() || type.equalsIgnoreCase("")){
                        ifCorrupted = true;
                    }
                }
                
                /*motorization*/
                if((vehicle.getElementsByTagName("motorization")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    motorization = getElementContent(vehicle, "motorization");  
                }
                /*fuel*/
                if((vehicle.getElementsByTagName("fuel")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    fuel = getElementContent(vehicle, "fuel");   
                    if(fuel.isEmpty() || fuel.equalsIgnoreCase("")){
                        ifCorrupted = true;
                    }
                }
                EngineFactory ef = new EngineFactory();
                eng = ef.getEngine(motorization, fuel);
                
                /*mass*/
                if((vehicle.getElementsByTagName("mass")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    mass = getElementContent(vehicle, "mass");   
                }
                
                /*load*/
                if((vehicle.getElementsByTagName("load")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    load = getElementContent(vehicle, "load");   
                }
                /*drag*/
                if((vehicle.getElementsByTagName("drag")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    drag = getElementContent(vehicle, "drag");   
                }
                /*frontal_area*/
                if((vehicle.getElementsByTagName("frontal_area")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    frontal_area = getElementContent(vehicle, "frontal_area");   
                }
                
                /*rrc*/
                if((vehicle.getElementsByTagName("rrc")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    rrc = getElementContent(vehicle, "rrc");   
                }
                
                /*wheel_size*/
                if((vehicle.getElementsByTagName("wheel_size")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    wheel_size = getElementContent(vehicle, "wheel_size");   
                }
                
                /*velocity_limit_list*/
                NodeList velocity_limit_list = vehicle.getElementsByTagName("velocity_limit_list");
                ArrayList<Velocity> velocityList = new ArrayList();
                if(velocity_limit_list.getLength()>0){
                    Node velocitys0 = velocity_limit_list.item(0);
                    Element velocity0 = (Element) velocitys0;
                    NodeList listOfVelocities = velocity0.getElementsByTagName("velocity_limit");
                    for(int j = 0; j< listOfVelocities.getLength(); j++){
                        Velocity v = velocityFromXml((Element)listOfVelocities.item(j));
                        if(v == null){
                            ifCorrupted = true;
                        }else{
                            velocityList.add(v);
                        }
                    }
                }
                
                /*energy*/
                if((vehicle.getElementsByTagName("energy")).getLength() == 0){
                    ifCorrupted = true;
                }else{
                    NodeList energy_list = vehicle.getElementsByTagName("energy");
                    Node energies0 = energy_list.item(0);
                    Element energy0 = (Element) energies0;
                    enrg = energyFromXml(energy0);
                }
                
                if(ifCorrupted != true && eng != null && enrg != null && !type.equalsIgnoreCase("") && !motorization.equalsIgnoreCase("") && !fuel.equalsIgnoreCase("") && !load.equalsIgnoreCase("") && !(drag.equalsIgnoreCase("")) && !frontal_area.equalsIgnoreCase("") && !rrc.equalsIgnoreCase("") && !wheel_size.equalsIgnoreCase("") && !mass.equalsIgnoreCase("")){
                    Vehicle v = vf.createVehicle(vehicleName, vehicleDescription, type, load, drag, rrc, wheel_size, velocityList, enrg, mass, eng, frontal_area);
                    vehiclesArray.add(v);
                }else{
                    ifCorrupted = true;
                }
            }
            if(ifCorrupted!=true){
                return vehiclesArray;
            }else{
                JOptionPane.showMessageDialog(null, "Invalid vehicles file");
                return null;
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehiclesArray;
    }
    
    public static Velocity velocityFromXml(Element velocity_limit){
        boolean ifCorrupted = false;
        String segment_type ="";
        String limit="";
        
        /*segment_type*/
        if((velocity_limit.getElementsByTagName("segment_type")).getLength() == 0){
            ifCorrupted = true;
        }else{
            segment_type = getElementContent(velocity_limit, "segment_type");
        }
        
        /*limit*/
        if((velocity_limit.getElementsByTagName("limit")).getLength() == 0){
            ifCorrupted = true;
        }else{
            limit = getElementContent(velocity_limit, "limit");
        }
        
        if(ifCorrupted == false && !(segment_type.equalsIgnoreCase("")) && !(limit.equalsIgnoreCase(""))){
            VelocityFactory vf = new VelocityFactory();
            return vf.createVelocity(segment_type, limit);
        }else{
            return null;
        }
    }
    
    public static Energy energyFromXml(Element energy){
        boolean ifCorrupted = false;
        ArrayList<Throttle> throttlesList = new ArrayList();
        ArrayList<Gear> gearsList = new ArrayList();
        GearsFactory gf = new GearsFactory();
        EnergiesFactory ef = new EnergiesFactory();
        String min_rpm="";
        String max_rpm="";
        String final_drive_ratio="";
        String energy_regeneration_ratio ="";
        
        /*min_rpm*/
        if((energy.getElementsByTagName("min_rpm")).getLength() == 0){
            ifCorrupted = true;
        }else{
            min_rpm = getElementContent(energy, "min_rpm");
        }
        
        /*max_rpm*/
        if((energy.getElementsByTagName("max_rpm")).getLength() == 0){
            ifCorrupted = true;
        }else{
            max_rpm = getElementContent(energy, "max_rpm");
        }
        
        /*final_drive_ratio*/
        if((energy.getElementsByTagName("final_drive_ratio")).getLength() == 0){
            ifCorrupted = true;
        }else{
            final_drive_ratio = getElementContent(energy, "final_drive_ratio");
        }
        
        /*max_rpm*/
        if((energy.getElementsByTagName("energy_regeneration_ratio")).getLength() == 0){
            energy_regeneration_ratio = "0";
        }else{
            energy_regeneration_ratio = getElementContent(energy, "energy_regeneration_ratio");
        }
        
        /*gears_list*/
        NodeList gear_lists = energy.getElementsByTagName("gear_list");
        if(gear_lists.getLength() != 0){
            Node gear_lists0 = gear_lists.item(0);
            Element gear_list0 = (Element) gear_lists0;
            NodeList listOfgears = gear_list0.getElementsByTagName("gear");
            for(int j = 0; j< listOfgears.getLength(); j++){
                String gearId = ((Element) listOfgears.item(j)).getAttribute("id");
                String ratio = getElementContent((Element) listOfgears.item(j), "ratio");
                
                Gear g = gf.createGear(gearId, ratio);
                if(g != null){
                    gearsList.add(g);
                }else{
                    ifCorrupted = true;
                }
            }
        }
        
        /*throttle_list*/
        NodeList throttle_list = energy.getElementsByTagName("throttle_list");
        if(gear_lists.getLength() != 0){
            Node throttle_list0 = throttle_list.item(0);
            Element elementThrottle_list = (Element) throttle_list0;
            NodeList throttles = elementThrottle_list.getElementsByTagName("throttle");
            for(int j = 0; j< throttles.getLength(); j++){
                Throttle t = throttleFromXml((Element) throttles.item(j));
                if(t == null){
                    ifCorrupted =true;
                }else{
                    throttlesList.add(t);
                }
            }
        }else{
            ifCorrupted = true;
        }
        
        if(ifCorrupted==false && !(min_rpm.equalsIgnoreCase("")) && !(max_rpm.equalsIgnoreCase("")) && !(final_drive_ratio.equalsIgnoreCase(""))){
            Energy enrg = ef.createEnergy(min_rpm, max_rpm, final_drive_ratio, gearsList, throttlesList, energy_regeneration_ratio);
            return enrg;
        }else{
            return null;
        }
    }
    
    public static Throttle throttleFromXml(Element throttle){
        boolean ifCorrupted = false;
        ThrottleFactory tf = new ThrottleFactory();
        ArrayList<Regime> regimesList = new ArrayList();
        String throttleId = throttle.getAttribute("id");
        NodeList regimes = throttle.getElementsByTagName("regime");
        for(int j = 0; j< regimes.getLength(); j++){
            Regime r = regimeFromXml((Element) regimes.item(j));
            if(r == null){
                ifCorrupted = true;
            }else{
                regimesList.add(r);
            }
        }
        if(ifCorrupted == true){
            return null;
        }else{
            return tf.createThrottle(throttleId, regimesList);
        }
    }
    
    public static Regime regimeFromXml(Element regime){
        boolean ifCorrupted = false;
        RegimeFactory rf = new RegimeFactory();
        String torque="";
        String rpm_low="";
        String rpm_high="";
        String sfc = "";
        
        /*torque*/
        if((regime.getElementsByTagName("torque")).getLength() == 0){
            ifCorrupted = true;
        }else{
            torque = getElementContent(regime, "torque");
        }
        
        /*rpm_low*/
        if((regime.getElementsByTagName("rpm_low")).getLength() == 0){
            ifCorrupted = true;
        }else{
            rpm_low = getElementContent(regime, "rpm_low");
        }
        
        /*rpm_high*/
        if((regime.getElementsByTagName("rpm_high")).getLength() == 0){
            ifCorrupted = true;
        }else{
            rpm_high = getElementContent(regime, "rpm_high");
        }
        /*SFC*/
        if((regime.getElementsByTagName("SFC")).getLength() == 0){
            sfc = "0";
        }else{
            sfc = getElementContent(regime, "SFC");
        }
        
        if(ifCorrupted == true){
            return null;
        }else{
            Regime r = rf.createRegime(torque, rpm_low, rpm_high, sfc);
            return r;
        }
    }
    
        
    private static String getElementContent(Element elt, String tag){
        String content = "";
        NodeList list = elt.getElementsByTagName(tag);
        Element element0 = (Element) list.item(0);
        content = element0.getTextContent();
        return content;
    }
    
    
    @Override
    public ArrayList<Simulation> importSimulation(File f){
        boolean ifCorrupted = false;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder; 
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            NodeList simulations = doc.getElementsByTagName("Simulation");
            SimulationFactory sf = new SimulationFactory();
            
                /*simulations*/
            ArrayList<Simulation> simulationsList = new ArrayList();
            if(simulations.getLength() != 0){
                for(int i = 0; i < simulations.getLength(); i++){
                    Node simulationNode = simulations.item(i);
                    Element simulation = (Element) simulationNode;
                    String simulationId = simulation.getAttribute("id");
                    String simulationDescription = simulation.getAttribute("description");

                    NodeList trafficNodeList = simulation.getElementsByTagName("traffic_list");
                    Element trafficList0 = (Element) trafficNodeList.item(0);

                    /*Traffic pattern*/
                    ArrayList<TrafficPattern> trafficPatterns = new ArrayList();

                    TrafficPatternFactory tpf = new TrafficPatternFactory();

                    NodeList trafficPatternList = trafficList0.getElementsByTagName("traffic_pattern");
                    for(int j = 0; j < trafficPatternList.getLength(); j++){
                        Element trafficPattern = (Element) trafficPatternList.item(j);
                        String nodeBegin = trafficPattern.getAttribute("begin");
                        String nodeEnd = trafficPattern.getAttribute("end");

                        /*vehicle*/
                        String vehicle = getElementContent(trafficPattern, "vehicle");

                        /*arrival_rate*/
                        String arrival_rate = getElementContent(trafficPattern, "arrival_rate");
                        TrafficPattern t = tpf.createTrafficPattern(nodeBegin, nodeEnd, vehicle, arrival_rate);
                        if(t == null){
                            ifCorrupted = true;
                        }else{
                            trafficPatterns.add(t);
                        }
                    }
                    Simulation s = sf.createSimulation(simulationId, simulationDescription, trafficPatterns);
                    if(s == null){
                        ifCorrupted = true;
                    }else{
                        simulationsList.add(s);
                    }
                }
            }else{
                ifCorrupted = true;
            }
            
            if(ifCorrupted == false){
                return simulationsList;
            }else{
                return null;
            }
        } catch (ParserConfigurationException ex) { 
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
               
    }
    
    
    

    private static String spliting(String tag, String setence){
        String vec[];
        vec = setence.split("<"+tag+">");
        vec[1]=vec[1].replaceAll("\"", "");
        vec = vec[1].split("</"+tag+">");
        return vec[0];
    }
    
}

