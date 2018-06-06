/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Project;
import Model.Simulation;
import Model.Vehicle;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author 1060708
 */
public interface IO {
    public Project importNetworkFile(File f);
    public ArrayList<Vehicle> importVehiclesFile(File f);
    public ArrayList<Simulation> importSimulation(File f);
}
