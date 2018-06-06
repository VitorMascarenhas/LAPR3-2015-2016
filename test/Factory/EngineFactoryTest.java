    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Engine;
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
public class EngineFactoryTest {
    
    public EngineFactoryTest() {
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
     * Test of getEngine method, of class EngineFactory.
     */
    @Test
    public void testGetEngine() {
        System.out.println("getEngine");
        String typeEngine = "Combustion";
        String type = "GASOLINE";
        EngineFactory instance = new EngineFactory();
        Engine expResult = instance.getEngine("Combustion", "GASOLINE");
        Engine result = instance.getEngine(typeEngine, type);
        assertEquals(expResult.getFuelType(), result.getFuelType());
    }
}