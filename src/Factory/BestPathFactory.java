/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import DataAnalysis.BestPathStrategy;

/**
 *
 * @author 11011_000
 */
public class BestPathFactory {
    
    private static BestPathFactory instance = new BestPathFactory();
    
    private BestPathFactory() {
    }
    
    public static BestPathFactory getInstance() {
        if (instance==null) instance=new BestPathFactory();
        return instance;
    }
    
    public BestPathStrategy GetStrategy(String beststrategy) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return (BestPathStrategy) Class.forName("DataAnalysis." + beststrategy).newInstance();
    }
    
}
