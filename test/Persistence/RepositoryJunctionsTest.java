/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Junction;
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
public class RepositoryJunctionsTest {
    
    public RepositoryJunctionsTest() {
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
     * Test of addJunction method, of class RepositoryJunctions.
     */
    @Test
    public void testAddJunction() {
        System.out.println("addJunction");
        Junction junction1 = new Junction("id1");
        Junction junction2 = new Junction("id2");
        Junction junction3 = new Junction("id3");
        Junction junction4 = new Junction("id4");
        RepositoryJunctions instance = new RepositoryJunctions();
        instance.addJunction(junction1);
        instance.addJunction(junction2);
        instance.addJunction(junction3);
        instance.addJunction(junction4);
        assertEquals(junction1.getId(), instance.getJunction("id1").getId());
        assertEquals(junction2.getId(), instance.getJunction("id2").getId());
        assertEquals(junction3.getId(), instance.getJunction("id3").getId());
        assertEquals(junction4.getId(), instance.getJunction("id4").getId());
    }
    
    /**
     * Test of getJunction method, of class RepositoryJunctions.
     */
    @Test
    public void testGetJunction() {
        System.out.println("getJunction");
        String id = "id1";
        RepositoryJunctions instance = new RepositoryJunctions();
        Junction junction = new Junction(id);
        instance.addJunction(junction);
        Junction expResult = junction;
        Junction result = instance.getJunction("id1");
        assertEquals(expResult, result);
    }
}