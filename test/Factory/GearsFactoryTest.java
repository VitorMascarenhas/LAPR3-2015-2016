/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Factory.GearsFactory;
import Model.Gear;
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
public class GearsFactoryTest {
    
    public GearsFactoryTest() {
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
     * Test of createGear method, of class GearsFactory.
     */
    @Test
    public void testCreateGear() {
        System.out.println("createGear");
        String id = "01";
        String ratio = "2500";
        int id1 = Integer.parseInt(id);
        int ratio1 = Integer.parseInt(ratio);
        GearsFactory instance = new GearsFactory();
        Gear expResult = new Gear(id1, ratio1);
        Gear result = instance.createGear(id, ratio);
        System.out.println("" + result.getId());
        System.out.println("" + expResult.getRatio());
        System.out.println("" + result.getId());
        System.out.println("" + result.getRatio());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getRatio(), result.getRatio());
    }
}