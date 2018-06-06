/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Factory.EngineFactory;
import Model.Energy;
import Model.Engine;
import Model.Gear;
import Model.Regime;
import Model.Throttle;
import Model.Vehicle;
import Model.Velocity;
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
public class VehicleControllerTest {
    
    public VehicleControllerTest() {
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
     * Test of importVehicle method, of class VehicleController.
     */
    @Test
    public void testImportVehicle() {
        
        String name = "LaFerrari";
        String description = "Ferrari teste";
        String type = "car";
        String fuel = "gasoline";
        float mass = 1641;
        float load = 600;
        float drag = 1.3f;
        float frontalArea = 1.1f;
        float rrc = 0.05f;
        float wheelSize = 0.99f;

        //velocity
        String segmentType = "Highway";
        float limit = 110;
        Velocity vel1 = new Velocity(segmentType, limit);
        ArrayList<Velocity> velocities = new ArrayList();
        velocities.add(vel1);

        //gears
        int id1 = 1;
        float ratio1 = 5.6f;
        Gear gear1 = new Gear(id1, ratio1);
        int id2 = 2;
        float ratio2 = 5.0f;
        Gear gear2 = new Gear(id2, ratio2);
        int id3 = 3;
        float ratio3 = 4.4f;
        Gear gear3 = new Gear(id3, ratio3);
        int id4 = 4;
        float ratio4 = 3.9f;
        Gear gear4 = new Gear(id4, ratio4);
        int id5 = 5;
        float ratio5 = 3.2f;
        Gear gear5 = new Gear(id5, ratio5);
        int id6 = 6;
        float ratio6 = 2.3f;
        Gear gear6 = new Gear(id6, ratio6);
        int id7 = 7;
        float ratio7 = 1.1f;
        Gear gear7 = new Gear(id7, ratio7);
        ArrayList<Gear> gears = new ArrayList<>();
        gears.add(gear1);
        gears.add(gear2);
        gears.add(gear3);
        gears.add(gear4);
        gears.add(gear5);
        gears.add(gear6);
        gears.add(gear7);

        
        //Throttle
        int tid1 = 25;
        int tid2 = 50;
        int tid3 = 100;
        int torque1 = 120;
        int rpm_low1 = 2000;
        int rpm_high1 = 3500;
        float sfc1 = 600;
        Regime regime1 = new Regime(torque1, rpm_low1, rpm_high1, sfc1);
        int torque2 = 150;
        int rpm_low2 = 2400;
        int rpm_high2 = 4000;
        float sfc2 = 650;
        Regime regime2 = new Regime(torque2, rpm_low2, rpm_high2, sfc2);
        ArrayList<Regime> regimes = new ArrayList<>();
        regimes.add(regime1);
        regimes.add(regime2);
        Throttle throttle1 = new Throttle(tid1, regimes);
        ArrayList<Regime> regimes2 = new ArrayList<>();

        int torque3 = 95;
        int rpm_low3 = 3500;
        int rpm_high3 = 5500;
        float sfc3 = 500;
        Regime regime3 = new Regime(torque3, rpm_low3, rpm_high3, sfc3);
        int torque4 = 90;
        int rpm_low4 = 4500;
        int rpm_high4 = 5500;
        float sfc4 = 650;
        Regime regime4 = new Regime(torque4, rpm_low4, rpm_high4, sfc4);
        regimes2.add(regime3);
        regimes2.add(regime4);
        Throttle throttle2 = new Throttle(tid2, regimes2);

        int torque5 = 80;
        int rpm_low5 = 5500;
        int rpm_high5 = 7000;
        float sfc5 = 400;
        Regime regime5 = new Regime(torque5, rpm_low5, rpm_high5, sfc5);

        int torque6 = 70;
        int rpm_low6 = 7300;
        int rpm_high6 = 9000;
        float sfc6 = 60;
        Regime regime6 = new Regime(torque6, rpm_low6, rpm_high6, sfc6);
        ArrayList<Regime> regimes3 = new ArrayList<>();

        regimes3.add(regime5);
        regimes3.add(regime6);
        Throttle throttle3 = new Throttle(tid3, regimes3);

        ArrayList<Throttle> throttles = new ArrayList<>();
        throttles.add(throttle1);
        throttles.add(throttle2);
        throttles.add(throttle3);
        //energy
        int minRpm = 2500;
        int maxRpm = 9000;
        float finalDriveRatio = 6.4f;
        float energyRegeneration = 6.4f;
        Energy energy1 = new Energy(minRpm, maxRpm, finalDriveRatio, null, null, energyRegeneration);
        EngineFactory eg = new EngineFactory();
        VehicleController instance = new VehicleController();
        
        
        Vehicle expResult = instance.importVehicle(name, description, type, type, load+"", drag+"", rrc+"", wheelSize+"", velocities, energy1, mass+"", eg.getEngine("COMBUSTION", "gasoline"), frontalArea+"");
        Vehicle result = new Vehicle(name, description, type, load, drag, rrc, wheelSize, velocities, energy1, mass, eg.getEngine("COMBUSTION", "gasoline"), frontalArea);
        //System.out.println("" + expResult.getType() + " " + result.getType());
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getDescription(), result.getDescription());
        assertEquals(expResult.getType(), result.getType());
        assertEquals(expResult.getLoad(), result.getLoad(), 0.001f);
        assertEquals(expResult.getDrag(), result.getDrag(), 0.001f);
        assertEquals(expResult.getMass(), result.getMass(), 0.001f);
        assertEquals(expResult.getRrc(), result.getRrc(), 0.001f);
        assertEquals(expResult.getWheelSize(), result.getWheelSize(), 0.001f);
    }
}