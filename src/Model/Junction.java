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
public class Junction {
    
    private String id;
    
    public Junction(String id){
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String toString(){
        return "Junction " +id; 
    }
    
    public boolean equals(Junction j){
        if(this.id.equalsIgnoreCase(j.id)){
            return true;
        }else{
            return false;
        }
    }
}