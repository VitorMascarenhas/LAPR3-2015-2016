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
public class TrafficPattern {
    private String beginNode;
    private String endNode;
    private String vehicleName;
    private int arrivalRate;
    
    public TrafficPattern(String beginNode,String endNode, String vehicleName, int arrivalRate){
        this.beginNode = beginNode;
        this.endNode = endNode;
        this.vehicleName = vehicleName;
        this.arrivalRate = arrivalRate;
    }
}
