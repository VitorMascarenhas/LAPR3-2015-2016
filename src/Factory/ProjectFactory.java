/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import Persistence.IO;

/**
 *
 * @author 1060708
 */
public class ProjectFactory {
    
    private static ProjectFactory fabrica = null;
    private ProjectFactory() { }
    // Singleton
    public static synchronized ProjectFactory getInstance() {
        if (fabrica == null)   fabrica = new ProjectFactory();
            return fabrica;
    }
    /**
     * Method that allows to get a startegy of 
     * @param fileType
     * @return 
     */
    public IO getEstrategia(String fileType) {
        String strClassName = null;
        try {
            if(fileType.equalsIgnoreCase("XML")){
                strClassName="Persistence.Xml";
            }
        } catch (Exception ex) { strClassName = "Persistence.Xml"; }        
        try {
            return (IO) Class.forName(strClassName).newInstance();
        } catch (Exception ex) { return  null; }
    }
    
}
