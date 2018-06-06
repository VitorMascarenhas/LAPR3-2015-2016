package Controller;



import Model.Junction;

import Persistence.RepositoryJunctions;



/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */



/**

 *

 * @author vitoralexandremascarenhasmascarenhas

 */

public class JunctionController {

    

    public JunctionController() {

    }

    

    public void createJunction(String id) {

        

        Junction junction = new Junction(id);

        

        RepositoryJunctions repJunctions = new RepositoryJunctions();

        repJunctions.addJunction(junction);

    }

}