/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Junction;
import Persistence.RepositoryJunctions;
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
public class JunctionControllerTest {
    
    public JunctionControllerTest() {
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
     * Test of createJunction method, of class JunctionController.
     */
    @Test
    public void testCreateJunction() {
        System.out.println("createJunction");
        String id = "j01";
        JunctionController instance = new JunctionController();
        instance.createJunction(id);
        Junction j = new Junction("j01");
        String str1 = j.getId();
        RepositoryJunctions repJ = new RepositoryJunctions();
        Junction j1 = repJ.getJunction("j01");
        String str2 = j1.getId();
        assertEquals(str1, str2);
    }
}