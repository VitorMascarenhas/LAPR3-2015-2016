/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author rekgnyz
 */
public class Wind {

    private float angle;
    private float speed;
    
    public float getSpeed() {
        return this.speed;
    }
    
    public float getAngle() {
        return this.angle;
    }
    
    public Wind(float speed, float angle){
        this.speed = speed;
        this.angle = angle;
    }
    
    public String toString(){
        return "Wind speed: " + speed + " - Wind angle: " + angle;
    }
}