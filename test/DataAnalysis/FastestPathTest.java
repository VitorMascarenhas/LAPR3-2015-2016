/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import Model.Junction;
import Model.RoadNetwork;
import Model.Section;
import Model.Segment;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 11011_000
 */
public class FastestPathTest {

    Junction n0;
    Junction n1;
    Junction n2;
    Junction n3;
    Section s0;
    Section s1;
    Section s2;
    Section s3;
    Section s4;
    Section s5;
    Section s6;
    ArrayList<Segment> segments;
    ArrayList<Segment> segments2;    
    
    public FastestPathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        segments=new ArrayList<>();
        segments2=new ArrayList<>();
        Segment dummy = new Segment();
        dummy.setLength(100);
        dummy.setMaximumVelocity(50);
        segments.add(dummy);
        for(int i=0; i<20; i++)
            segments2.add(dummy);
        
        n0 = new Junction("n0");
        n1 = new Junction("n1");
        n2 = new Junction("n2");
        n3 = new Junction("n3");
        s0 = new Section(n0, n1, "", Section.Typology.CONTROLLED.toString(), Section.Direction.BIDIRECTIONAL.toString(), 0, null, segments, null);
        s1 = new Section(n1, n2, "", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, null, segments, null);
        s2 = new Section(n2, n0, "", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s3 = new Section(n0, n2, "", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s4 = new Section(n2, n3, "", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s5 = new Section(n0, n3, "", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments2, null);
        s6 = new Section(n3, n0, "", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBestPath method, of class FastestPath.
     */
    @Test
    public void testGetBestPath() {

        ArrayList<Junction> junctions = new ArrayList<>();
        ArrayList<Section> sections = new ArrayList<>();
        
        junctions.add(n0);
        junctions.add(n1);
        junctions.add(n2);
        junctions.add(n3);
        
        sections.add(s0);
        sections.add(s1);
        sections.add(s2);
        sections.add(s3);
        sections.add(s4);
        sections.add(s5);
        sections.add(s6);
        
        BestPathStrategy instance = new FastestPath();
        
        RoadNetwork roadNetwork0 = new RoadNetwork();
        roadNetwork0.createRoadNetwork(junctions, sections, null, "travelTime"); //VEHICLE MISSING!!!!
        Deque<Junction> expResult = new LinkedList<>();
        Deque<Junction> result = new LinkedList<>();
        expResult.add(n0);
        expResult.add(n2);
        //Doesn't reflect the final changes to the methods. Needs vehicle
        assertEquals("The shortest road only takes 2 hours to get from junction 0 to junction 2.", 2, instance.getBestPath(junctions, sections, n0, n2, null, result), 0.01f);
        assertEquals("The shortest road goes from junction 0 to junction 2.", expResult, result);
        //Doesn't reflect the final changes to the methods. Needs vehicle
        RoadNetwork instance1 = new RoadNetwork();
        instance1.createRoadNetwork(junctions, sections, null, "travelTime"); //VEHICLE MISSING!!!!
        Deque<Junction> expResult2 = new LinkedList<>();
        Deque<Junction> result2 = new LinkedList<>();
        result=null;
        expResult2.add(n0);
        expResult2.add(n2);
        expResult2.add(n3);
        assertEquals("The shortest road only takes 4 hours to get from junction 0 to junction 3.", 4, instance.getBestPath(junctions, sections, n0, n3, null, result2), 0.01f);
        assertEquals("The shortest road goes from junction 0 to 2 and to 3.", expResult2, result2);
    }
    
}
