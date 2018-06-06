/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Model.Velocity;

/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class VelocityFactory {
    
    public VelocityFactory() {
    }
    
    public Velocity createVelocity(String type, String limit) {
        
        float lim = Float.parseFloat(limit);
        Velocity vel = new Velocity(type, lim);
        return vel;
    }
}