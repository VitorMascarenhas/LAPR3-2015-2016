/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import DataAnalysis.BestPathStrategy;
import DataAnalysis.ShortestPath;
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
public class BestPathFactoryTest {
    
    public BestPathFactoryTest() {
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
     * Test of GetStrategy method, of class BestPathFactory.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetStrategy() throws Exception {
        System.out.println("*** Best Path Strategy Factory tests ****");
        String type = "ShortestPath";
        BestPathFactory instance = BestPathFactory.getInstance();
        BestPathStrategy result = instance.GetStrategy(type);
        assertTrue(result instanceof ShortestPath);
    }
    
}
