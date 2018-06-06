/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Graph.Graph;
import Graph.Vertex;
import Model.RoadNetwork;
import Model.Junction;
import Model.Section;
import Model.Segment;
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
 * @author 11011_000
 */
public class RoadNetworkTest {

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

    public RoadNetworkTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        segments = new ArrayList<>();
        segments2 = new ArrayList<>();
        Segment dummy = new Segment();
        dummy.setLength(1);
        segments.add(dummy);
        segments2.add(dummy);
        for (int i = 0; i < 20; i++) {
            segments2.add(dummy);
        }

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
     * Test of createRoadNetwork method, of class RoadNetwork.
     */
    @Test
    public void testCreateRoadNetwork() {
        System.out.println("*** Create Road Network Tests ***");

        ArrayList<Junction> junctions = new ArrayList<>();
        ArrayList<Section> sections = new ArrayList<>();

        RoadNetwork instance = new RoadNetwork();
        assertEquals("The RoadNetWork has no junctions.", 0, instance.getNumJunctions());
        assertEquals("The RoadNetWork has no sections.", 0, instance.getNumSections());

        junctions.add(n0);
        junctions.add(n1);
        sections.add(s0);
        RoadNetwork instance0 = new RoadNetwork();
        instance0.createRoadNetwork(junctions, sections, null, "sectionLength");
        assertEquals("The RoadNetWork has 2 junctions.", 2, instance0.getNumJunctions());
        assertEquals("The RoadNetWork has 1 section.", 1, instance0.getNumSections());

        junctions.add(n1);
        junctions.add(n2);
        sections.add(s1);
        RoadNetwork instance1 = new RoadNetwork();
        instance1.createRoadNetwork(junctions, sections, null, "sectionLength");
        assertEquals("The RoadNetWork has 3 junctions.", 3, instance1.getNumJunctions());
        assertEquals("The RoadNetWork has 2 sections.", 2, instance1.getNumSections());

        sections.add(s2);
        RoadNetwork instance2 = new RoadNetwork();
        instance2.createRoadNetwork(junctions, sections, null, "sectionLength");
        assertEquals("The RoadNetWork has 3 junctions.", 3, instance2.getNumJunctions());
        assertEquals("The RoadNetWork has 3 sections.", 3, instance2.getNumSections());

    }

    @Test
    public void testCreateRoadNetworkShortest() {
        System.out.println("ROAD NETWORK - SHORTEST TEST");

        // Section 1 SETUP - Weight (length) = 30 - [N0 -> N1]
        Segment seg1 = new Segment(1, 0, 0, 10, 50, 90, 5);
        Segment seg2 = new Segment(2, 0, 0, 15, 50, 90, 5);
        Segment seg3 = new Segment(3, 0, 0, 5, 50, 90, 5);
        ArrayList<Segment> Section1Segs = new ArrayList<>();
        Section1Segs.add(seg1);
        Section1Segs.add(seg2);
        Section1Segs.add(seg3);
        Section s1 = new Section(n0, n1, "fury road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section1Segs, null);

        // Section 2 SETUP - Weight (length) = 15 - [N0 -> N1]
        Segment seg4 = new Segment(1, 0, 0, 5, 50, 90, 5);
        Segment seg5 = new Segment(2, 0, 0, 5, 50, 90, 5);
        Segment seg6 = new Segment(3, 0, 0, 5, 50, 90, 5);
        ArrayList<Segment> Section2Segs = new ArrayList<>();
        Section1Segs.add(seg4);
        Section1Segs.add(seg5);
        Section1Segs.add(seg6);
        Section s2 = new Section(n0, n1, "chill road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section2Segs, null);

        // Section 3 SETUP - Weight (length) = 25 - [N0 -> N1]
        Segment seg7 = new Segment(1, 0, 0, 3, 50, 90, 5);
        Segment seg8 = new Segment(2, 0, 0, 2, 50, 90, 5);
        Segment seg9 = new Segment(3, 0, 0, 20, 50, 90, 5);
        ArrayList<Segment> Section3Segs = new ArrayList<>();
        Section1Segs.add(seg7);
        Section1Segs.add(seg8);
        Section1Segs.add(seg9);
        Section s3 = new Section(n0, n1, "fury road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section3Segs, null);

        // Section 4 SETUP - Weight (length) = 90 - [N1 -> N2]
        Segment seg10 = new Segment(1, 0, 0, 30, 50, 90, 5);
        Segment seg11 = new Segment(2, 0, 0, 30, 50, 90, 5);
        Segment seg12 = new Segment(3, 0, 0, 30, 50, 90, 5);
        ArrayList<Segment> Section4Segs = new ArrayList<>();
        Section1Segs.add(seg10);
        Section1Segs.add(seg11);
        Section1Segs.add(seg12);
        Section s4 = new Section(n1, n2, "chill road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section4Segs, null);

        // Section 5 SETUP - Weight (length) = 100 - [N1 -> N2]
        Segment seg13 = new Segment(1, 0, 0, 30, 50, 90, 5);
        Segment seg14 = new Segment(2, 0, 0, 40, 50, 90, 5);
        Segment seg15 = new Segment(3, 0, 0, 30, 50, 90, 5);
        ArrayList<Segment> Section5Segs = new ArrayList<>();
        Section1Segs.add(seg13);
        Section1Segs.add(seg14);
        Section1Segs.add(seg15);
        Section s5 = new Section(n1, n2, "fury road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section5Segs, null);

        // Section 6 SETUP - Weight (length) = 100 - [N2 -> N0]
        Segment seg16 = new Segment(1, 0, 0, 8, 50, 90, 5);
        Segment seg17 = new Segment(2, 0, 0, 1, 50, 90, 5);
        Segment seg18 = new Segment(3, 0, 0, 2015, 50, 90, 5);
        ArrayList<Segment> Section6Segs = new ArrayList<>();
        Section1Segs.add(seg16);
        Section1Segs.add(seg17);
        Section1Segs.add(seg18);
        Section s6 = new Section(n2, n0, "lonely road", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, new Wind(20, 12), Section6Segs, null);

        System.out.println("SEGMENTS & SECTIONS SETUP - COMPLETE!");

        System.out.println("Building Network...");
        ArrayList<Section> roadSections = new ArrayList<>();
        roadSections.add(s1);
        roadSections.add(s2);
        roadSections.add(s3);
        roadSections.add(s4);
        roadSections.add(s5);
        roadSections.add(s6);

        ArrayList<Junction> roadJunctions = new ArrayList<>();
        roadJunctions.add(n0);
        roadJunctions.add(n1);
        roadJunctions.add(n2);

        //Doesn't reflect the final changes to the methods. Needs vehicle
        RoadNetwork roadMap = new RoadNetwork();
        roadMap.createRoadNetwork(roadJunctions, roadSections);
        Graph roadGraph = roadMap.getRoadNetwork();

        Vertex vn0 = roadGraph.getVertex(n0);
        Vertex vn1 = roadGraph.getVertex(n1);
        Vertex vn2 = roadGraph.getVertex(n2);

        System.out.println("Testing...");
               
        assertEquals(s2.toString(), roadGraph.getEdge(vn0, vn1).getElement(), s2);
        assertEquals(s4.toString(), roadGraph.getEdge(vn1, vn2).getElement(), s4);
        assertEquals(s6.toString(), roadGraph.getEdge(vn2, vn0).getElement(), s6);

        System.out.println("Done!");

    }

}
