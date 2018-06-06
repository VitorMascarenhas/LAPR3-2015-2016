/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author rekgnyz
 */
public class Throttle {
    int id;
    private ArrayList<Regime> regimes;
    
    public Throttle(int id, ArrayList<Regime> regimes){
        this.id = id;
        this.regimes = regimes;
    }
    
    public int getTorque(int engineSpeed) {
        for(Regime regime : getRegimes())
            if(regime.betweenRPM(engineSpeed))
                return regime.getTorque();
        return -1;
    }

    public boolean equals(Throttle t){
        if(this.id == t.id && this.containsSameRegimes(t.getRegimes())){
            return true;
        }else{
            return false;
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean containsSameRegimes(ArrayList<Regime> regimes){
        boolean exist = true;
        for(Regime v : regimes){
            exist = existRegime(v);      
        }
        return exist;
    }
    
    public boolean existRegime(Regime v){
        int cont = 0;
        for(Regime v2 : this.getRegimes()){
            if((v.equals(v2))){
               cont++; 
            }
        }
        if(cont == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @return the regimes
     */
    public ArrayList<Regime> getRegimes() {
        return regimes;
    }
}
