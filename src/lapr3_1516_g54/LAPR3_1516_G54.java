/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr3_1516_g54;

import Controller.MainController;
import java.io.FileNotFoundException;


/**
 *
 * @author 1081320
 */
public class LAPR3_1516_G54 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
//        ProjectFactory fabrica = ProjectFactory.getInstance();
//        IO estrategia = fabrica.getEstrategia("XML");
//        Project p = estrategia.importFile();
        /*ImportProject p = new ImportProject();
        Project prj = RepositoryProjects.getProjects().get(0);
        Html.exportSimulation(prj);*/
        
        //DBConnection db = new DBConnection("10.0.2.15", "SYSTEM", "*******");
        
        //db.insert("insert into Junction values('j01'')");
        
        MainController main = new MainController();
    }
}