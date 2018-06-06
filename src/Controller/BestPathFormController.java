/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataAnalysis.BestPathStrategy;
import DataAnalysis.CarPhysics;
import Factory.BestPathFactory;
import Model.Junction;
import Model.Project;
import Model.Section;
import Model.Segment;
import Model.Vehicle;
import Model.Wind;
import Persistence.Html;
import UI.BestPathForm;
import UI.MainUI;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author 11011_000
 */
public class BestPathFormController {
    
    private Project model;
    private BestPathForm bestPathForm;
    private MainUI mainUI;
    private BestPathFactory bestPathFact = BestPathFactory.getInstance();
    private BestPathStrategy bestPath;
    private Vehicle vehicle;
    private CarPhysics physics = new CarPhysics();

    
    public BestPathFormController(Project model, MainUI mainUI) {
        
        this.model=model;
        this.mainUI=mainUI;

        mainUI.bestPathFormPanel.setVisible(true);

        initItems();
        initListeners();
    }
    
    private void initItems() {
        String[] algorithms = {"ShortestPath", "FastestPath", "TheoreticalMostEfficientPath"};
        
        mainUI.fromNode.setModel(new DefaultComboBoxModel(model.getJunctions().toArray()));
        mainUI.destNode.setModel(new DefaultComboBoxModel(model.getJunctions().toArray()));
        mainUI.vehicle.setModel(new DefaultComboBoxModel(model.getVehicles().toArray()));
        mainUI.algorithm.setModel(new DefaultComboBoxModel(algorithms));
        
    }
    
    private void initListeners() {
             
        mainUI.done.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    try {
                        doneActionPerformed(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(BestPathFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(BestPathFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    private void doneActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        
        if(mainUI.fromNode.getSelectedItem()==mainUI.destNode.getSelectedItem())
            JOptionPane.showMessageDialog(null, "The source and destination nodes are the same.");
        
        bestPath = bestPathFact.GetStrategy(mainUI.algorithm.getSelectedItem().toString());
        
        Deque<Junction> bestPathJunctions = new LinkedList<>();
        Deque<Section> bestPathSections = new LinkedList<>();
        
        Junction from = model.getJunctions().get(mainUI.fromNode.getSelectedIndex());
        Junction destination = model.getJunctions().get(mainUI.destNode.getSelectedIndex());
        vehicle = model.getVehicles().get(mainUI.vehicle.getSelectedIndex());
        
        double result = bestPath.getBestPath(model.getJunctions(), model.getSections(), from, destination, vehicle, bestPathJunctions, bestPathSections);

        showResults(result, bestPathJunctions, bestPathSections);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to generate an HTML file?","Question",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            Html.exportSimulation(model, result, bestPathJunctions, bestPathSections, vehicle);}
    }
    
    private void showResults(double result, Deque<Junction> bestPathJunctions, Deque<Section> bestPathSections) {

        float distance = 0;
        float travelTime=0;
        double energy=0.0;
        float toll=0;
        float velocity = 0.0f;
        Wind wind;

        ArrayList<String> path = new ArrayList();
        for(Section sec : bestPathSections){
            distance+=sec.getLength();
            travelTime+=sec.getTravelTime(model.getVehicles().get(mainUI.vehicle.getSelectedIndex()));
            wind = sec.getWind();
            toll+=sec.getToll();

            for(Segment segment : sec.getSegments()){
                    velocity = segment.getMaximumVelocity(vehicle.returnVelocityLimitFromSegment(sec.getTypology().toString()));                     
                    energy = (double)physics.theoricalVehicleWork(vehicle, velocity, segment, wind);
            }
            path.add("from " + sec.getStartingNode().getId() + " take " + sec.getRoad() + "  to " + sec.getEndingNode().getId() + " for " + String.format("%.1f",sec.getLength()) + " km");
        }
            

        mainUI.resultsPanel.setVisible(true);
        mainUI.resultsList.setListData(path.toArray());
        
        mainUI.distanceLabel.setText(String.format("%.1f",distance) + " km");
        mainUI.travelTimeLabel.setText(String.format("%.1f",travelTime) + " minutes");
        mainUI.energyLabel.setText(String.format("%.2f",energy) + " joules");
        mainUI.tollLabel.setText(String.format("%.2f",toll) + " euros");
                
        mainUI.resultsList.setVisible(true);
        
    }

    
}
