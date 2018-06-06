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
import Persistence.RepositorySections;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class ControllerSectionTest {
    
    public ControllerSectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createSection method, of class ControllerSection.
     */
    @Test
    public void testCreateSection() {
        System.out.println("createSection");
        String jOrig = "j01";
        String jDest = "j02";
        String road = "road";
        String typology = "URBAN";
        String direction = "REVERSE";
        String toll = "5";
        String speed = "20";
        String angle = "60";
        ArrayList<Segment> segments = new ArrayList<>();
        Segment s = new Segment();
        segments.add(s);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Vehicle v = new Vehicle();
        vehicles.add(v);
        ArrayList<Junction> junctions = new ArrayList<>();
        Junction j1 = new Junction(jOrig);
        Junction j2 = new Junction(jDest);
        junctions.add(j1);
        junctions.add(j2);
        Wind w = new Wind(Integer.parseInt(speed), Integer.parseInt(angle));
        ControllerSection instance = new ControllerSection();
        Section expResult = new Section(j1, j2, road, typology, direction, Float.parseFloat(toll), w, segments, vehicles);
        Section result = instance.createSection(jOrig, jDest, road, typology, direction, toll, speed, angle, segments, vehicles, junctions);
        assertEquals(expResult.getStartingNode().getId(), result.getStartingNode().getId());
        assertEquals(expResult.getEndingNode().getId(), result.getEndingNode().getId());
        assertEquals(expResult.getRoad(), result.getRoad());
        assertEquals(expResult.getDirection(), result.getDirection());
        assertEquals(expResult.getTypology(), result.getTypology());
        assertEquals(expResult.getWind().getSpeed(), result.getWind().getSpeed(), 0.001f);
        assertEquals(expResult.getWind().getAngle(), result.getWind().getAngle(), 0.001f);
    }
}