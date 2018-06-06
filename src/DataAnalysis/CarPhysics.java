/* * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import Model.Section;
import Model.Segment;
import Model.Vehicle;
import Model.Velocity;
import Model.Wind;

/**
 *
 * @author 1081320
 */
public class CarPhysics {

    double g = 9.81; // gravity force
    double p = 1.225; // Wind density
    
    /*FORCE APPLIED TO A VEHICLE ON A FLAT SURFACE
     *
     * F = net	force	acting	on	the	car	(N)
     * Γ(ω) = torque	function	of	the	engine	given	an	engine	speed	ω	in	s-1 (Nm)
     * G = final	drive	ratio	(no	unit)
     * gk = k-th	gear	ratio	(no	unit)
     * r = radius	of	tire	(m)
     * crr = rolling	resistance	coefficient	(no	unit)
     * m = mass	of	the	car	(kg)
     * g = gravity	(ms-2)
     * cd = drag	(air	resistance)	coefficient	(no	unit)
     * A = frontal	area	of	the	car	(m2)
     * ρ = density	of	air	(kgm-3)
     * v = velocity	of	car	(ms-1)
     *
     */
    
    public double motorForce(Vehicle vehicle, int throttle, int rpm, int kth){

        float torque = vehicle.getTorque(throttle, rpm);
        float G = vehicle.getFinalRatio();
        float gk = vehicle.getGearRatio(kth);
        float r = vehicle.getWheelSize();
        
        return (torque*G*gk)/r;
    }
    
    
    public double rrc(Vehicle vehicle, Segment segment) {
        double crr = vehicle.getRrc();
        double mass = vehicle.getMass();
        double angle = Math.cos(Math.toRadians(slopeDegrees(segment.getSlope())));
        return crr * mass * g * angle;
    }
    
    public double drag(Vehicle vehicle, double vVelocity, Wind wind) {
        double coefDrag=vehicle.getDrag();
        double A = vehicle.getFrontalArea();
        double v=relativeVelocity(vVelocity, wind);
        double res = (coefDrag * A * p * (v*v)*0.5);
        return res;
    }
    
    private double relativeVelocity(double vVelocity, Wind wind) {
        double relVelocity=vVelocity - wind.getSpeed()*Math.cos(Math.toRadians(wind.getAngle()));
        return (relVelocity>30?relVelocity:30);
    }
    
    private double gravForce(Vehicle vehicle, Segment segment) {
        double m = vehicle.getMass();
        double angleSin = Math.sin(slopeDegrees(segment.getSlope()));
        double result = m *g*angleSin;
        return result;
    }
    
    // Converts slope % to degrees
    private double slopeDegrees(double slope) {
        double degrees = Math.atan(slope/100);
        return degrees;
    }

    public double theoricalVehicleWork(Vehicle vehicle, float vVelocity, Segment segment, Wind wind) {
        double rrc = rrc(vehicle,segment);
        double drag = drag(vehicle, vVelocity, wind);
        double gravForce = gravForce(vehicle, segment);
        return gravForce + rrc + drag;
    }
                
}
