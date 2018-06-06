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
import Model.Vehicle;
import Model.Wind;
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
public class ShortestPathTest {

    Junction n0;
    Junction n1;
    Junction n2;
    Junction n3;
    Junction n4;

    Section s1;
    Section s2;
    Section s3;
    Section s4;
    Section s5;
    Section s6;

    ArrayList<Segment> sege01=new ArrayList<>();
    ArrayList<Segment> sege02=new ArrayList<>();
    ArrayList<Segment> sege03=new ArrayList<>();
    ArrayList<Segment> sege04=new ArrayList<>();
    ArrayList<Segment> sege05=new ArrayList<>();
    ArrayList<Segment> sege06=new ArrayList<>();

    ArrayList<Junction> junctions = new ArrayList<>();
    ArrayList<Section> sections = new ArrayList<>();

    
    Vehicle vehicle = new Vehicle();
    
    public ShortestPathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        n0 = new Junction("n0");
        n1 = new Junction("n1");
        n2 = new Junction("n2");
        n3 = new Junction("n3");
        n4 = new Junction("n4");
        
        sege01.add(new Segment(01, 100, 3.0f, 3.2f, 0, 90, 250));
    	sege01.add(new Segment(02, 196, -1.5f, 6.4f, 0, 90, 200));
    	s1=new Section(n0,n2, "E01", Section.Typology.REGULAR.toString(),Section.Direction.BIDIRECTIONAL.toString(), 0, new Wind(5,20), sege01, null);

	sege02.add(new Segment(01, 100, 1.5f, 10, 0, 90, 350));
    	sege02.add(new Segment(02, 150, 1.0f, 5, 0, 90, 200));
    	s2=new Section(n2,n3, "E01", Section.Typology.REGULAR.toString(), Section.Direction.BIDIRECTIONAL.toString(), 0, new Wind(3,-5), sege02, null);

	sege03.add(new Segment(01, 200, 2.0f, 10, 0, 90, 300));
	sege03.add(new Segment(02, 400, -2.5f, 10, 0, 90, 250));
    	s3=new Section(n3,n4, "E01", Section.Typology.REGULAR.toString(), Section.Direction.BIDIRECTIONAL.toString(), 0, new Wind(5,-5), sege03, null);

    	sege04.add(new Segment(01, 100, 0, 25, 50, 120, 2000));
    	s4=new Section(n0,n1, "A01", Section.Typology.HIGHWAY.toString(), Section.Direction.BIDIRECTIONAL.toString(), 12, new Wind(3,-5), sege04, null);

	sege05.add(new Segment(01, 100, 0.5f, 20, 50, 120, 1500));
    	s5=new Section(n1,n3, "A01", Section.Typology.HIGHWAY.toString(), Section.Direction.BIDIRECTIONAL.toString(), 4, new Wind(3,-5), sege05, null);

	sege06.add(new Segment(01, 100, 2.5f, 10, 0, 90, 150));
	sege06.add(new Segment(02, 350, -4.0f, 5, 0, 90, 100));
    	s6=new Section(n2,n4, "E06", Section.Typology.REGULAR.toString(), Section.Direction.BIDIRECTIONAL.toString(), 0, new Wind(10,-15), sege06, null);
        
        junctions.add(n0);
        junctions.add(n1);
        junctions.add(n2);
        junctions.add(n3);
        
        sections.add(s1);
        sections.add(s2);
        sections.add(s3);
        sections.add(s4);
        sections.add(s5);
        sections.add(s6);
          
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBestPath method, of class ShortestPath with 1 hop.
     */
    @Test
    public void testGetBestPath1Hop() {

        BestPathStrategy instance = new ShortestPath();
        
        RoadNetwork roadNetwork0 = new RoadNetwork();
        roadNetwork0.createRoadNetwork(junctions, sections, vehicle, "sectionLength");
        Deque<Section> expResultSec = new LinkedList<>();
        Deque<Junction> resultJunc = new LinkedList<>();
        Deque<Section> resultSec = new LinkedList<>();
        expResultSec.add(s1);
             
        assertEquals("The shortest road only takes 9.6 km from junction 0 to junction 2.", 9.6f, instance.getBestPath(junctions, sections, n0, n2, vehicle, resultJunc, resultSec), 0.01f);
        assertEquals("The shortest road takes the road E01.", expResultSec, resultSec);
    }

    /**
     * Test of getBestPath method, of class ShortestPath with 3 hops.
     */
    @Test
    public void testGetBestPath3Hop() {

        BestPathStrategy instance = new ShortestPath();
        
        RoadNetwork roadNetwork0 = new RoadNetwork();
        roadNetwork0.createRoadNetwork(junctions, sections, vehicle, "sectionLength");
        Deque<Section> expResultSec = new LinkedList<>();
        Deque<Junction> resultJunc = new LinkedList<>();
        Deque<Section> resultSec = new LinkedList<>();
        expResultSec.add(s1);
        expResultSec.add(s6);
             
        assertEquals("The shortest road only takes 24.6 km from junction 0 to junction 4.", 24.6f, instance.getBestPath(junctions, sections, n0, n4, vehicle, resultJunc, resultSec), 0.01f);
        assertEquals("The shortest road takes the road E01 and then the E06.", expResultSec, resultSec);    
    }
    
}
