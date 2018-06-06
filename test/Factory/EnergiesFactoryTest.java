/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Factory.EnergiesFactory;
import Model.Energy;
import Model.Gear;
import Model.Throttle;
import java.util.ArrayList;
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
public class EnergiesFactoryTest {
    
    public EnergiesFactoryTest() {
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
     * Test of createEnergy method, of class EnergiesFactory.
     */
    @Test
    public void testCreateEnergy() {
        System.out.println("createEnergy");
        String torque = "10";
        String rpm = "5500";
        String consumption = "10.6";
        String minRpm = "1000";
        String maxRpm = "5500";
        String finalDriveRatio = "12.3";
        String energyRegenerationRatio = "0.0";
        ArrayList<Gear> gearList = new ArrayList<>();
        Gear g = new Gear(1, 2000);
        gearList.add(g);
        EnergiesFactory instance = new EnergiesFactory();
        Energy expResult = new Energy(1000, 5500, 12.3f, null, null, 0.0f);
        Energy result = instance.createEnergy(minRpm, maxRpm, finalDriveRatio, null, null, energyRegenerationRatio);
        assertEquals(expResult.getMinRpm(), result.getMinRpm());
        assertEquals(expResult.getMaxRpm(), result.getMaxRpm());
        assertEquals(expResult.getFinalDriveRatio()+"", result.getFinalDriveRatio()+"");
        assertEquals(expResult.getEnergyRegenerationRatio()+"", result.getEnergyRegenerationRatio()+"");
    }
}