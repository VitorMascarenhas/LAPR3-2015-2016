/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Segment;
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
public class ControllerSegmentsTest {
    
    public ControllerSegmentsTest() {
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
     * Test of addSegment method, of class ControllerSegments.
     */
    @Test
    public void testNewSegment() {
        System.out.println("addSegment");
        String segmentIndex = "1";
        String initialHeigth = "10";
        String slope = "12";
        String length = "14";
        String rrc = "0.5";
        String minimumVelocity = "30";
        String maxVelocity = "120";
        String maxVehiclesNumber = "400";
        ControllerSegments instance = new ControllerSegments();
        Segment expResult = instance.newSegment(segmentIndex, initialHeigth, slope, length, minimumVelocity, maxVelocity, maxVehiclesNumber);
        Segment result = expResult;
                
        assertEquals(expResult, result);
    }
}
