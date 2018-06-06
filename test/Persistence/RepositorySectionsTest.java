/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Junction;
import Model.Section;
import Model.Segment;
import Model.Vehicle;
import Model.Wind;
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
public class RepositorySectionsTest {
    
    public RepositorySectionsTest() {
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
     * Test of addSection method, of class RepositorySections.
     */
    @Test
    public void testAddSection() {
        System.out.println("addSection");
        Junction j1 = new Junction("id1");
        Junction j2 = new Junction("id2");
        
        String road = "Pinto da Costa";
        String tipolagy = "URBAN";
        String direction = "REVERSE";
        float toll = 5.60f;
        float speed = 10f;
        float angle = 15f;
        Wind wind = new Wind(speed, angle);
        
        Segment s1 = new Segment(1, 0.5f, 1f, 13f, 30, 120, 150);
        Segment s2 = s1;
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(s1);
        
        Vehicle v1 = new Vehicle();
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Vehicle v2 = v1;
        vehicles.add(v1);
        
        Section section = new Section(j1, j2, road, tipolagy, direction, toll, wind, segments, vehicles);
        
        Section section2 = new Section(j1, j2, road, tipolagy, direction, toll, wind, segments, vehicles);
        
        RepositorySections instance = new RepositorySections();
        instance.addSection(section);
        assertEquals(section, instance.getSection(j1, j2));
    }
}