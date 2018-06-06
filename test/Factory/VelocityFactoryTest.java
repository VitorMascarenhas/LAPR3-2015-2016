/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Factory.VelocityFactory;
import Model.Velocity;
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
public class VelocityFactoryTest {
    
    public VelocityFactoryTest() {
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
     * Test of createVelocity method, of class VelocityFactory.
     */
    @Test
    public void testCreateVelocity() {
        System.out.println("createVelocity");
        String type = "URBAN";
        String limit = "100";
        float lmt = Float.parseFloat(limit);
        VelocityFactory instance = new VelocityFactory();
        Velocity expResult = new Velocity(type, lmt);
        Velocity result = instance.createVelocity(type, limit);
        assertEquals(expResult.getTypology(), result.getTypology());
        assertEquals(expResult.getLimit(), result.getLimit(), 0);
    }
}