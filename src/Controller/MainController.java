/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Graph.GraphX;
import Model.Junction;
import Model.Project;
import Model.Section;
import Model.Segment;
import Model.Vehicle;
import Persistence.DBAccess;
import Persistence.Html;
import Persistence.RepositoryProjects;
import UI.ChangeCurrentProj;
import UI.GraphDesign;
import UI.ImportProject;
import UI.ImportRoads;
import UI.ImportSimulations;
import UI.ImportVehicles;
import UI.MainUI;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author 11011_000
 */
public class MainController {
    
    private List<Project> availableModels = new ArrayList<>();
    private Project currentModel;
    private MainUI mainUI;
    DefaultListModel<String> jListModel;
    private ChangeCurrentProj changeProj = new ChangeCurrentProj();
    DBAccess db;
    
    
    public MainController(){
        
        mainUI = new MainUI();
        mainUI.setVisible(true);

        currentModel = dummyModel();
        availableModels.add(currentModel);

        mainUI.bestPathFormPanel.setVisible(false);
        mainUI.resultsPanel.setVisible(false);
        
        
        initListeners();
    }
    
    /* 
    * Action Listeners for Menu Items
    */
    private void initListeners() {

        /*
        *   PROJECT MENU LISTENERS
        */        
        mainUI.newProjMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        newProjActionPerformed(evt);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });

        mainUI.copyProjMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        copyProjActionPerformed(evt);
                }
        });

        mainUI.editProjMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        editProjActionPerformed(evt);
                }
        });

        mainUI.saveProjMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        saveProjActionPerformed(evt);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });

        mainUI.changeProjMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        changeProjActionPerformed(evt);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });

        ChangeCurrentProj.applyChangeProjButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyChangeProjButtonActionPerformed(evt);
            }
        });
        
        /*
        *   ANALYSIS MENU LISTENERS
        */        
        mainUI.bestPathMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        bestPathMenuActionPerformed(evt);
                }
        });
        
        mainUI.vehicleCompareMenu.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    vehicleCompareActionPerformed(evt);
                    }
            });
        
        /*
        *   TOOLS MENU LISTENERS
        */        
        mainUI.impVehiclesMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        importVehiclesActionPerformed(evt);
                }
        });

        mainUI.viewVehiclesMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        viewVehiclesActionPerformed(evt);
                }
        });

        mainUI.impRoadsMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        importRoadsActionPerformed(evt);
                }
        });

        mainUI.viewRoadsMenu.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        viewRoadsActionPerformed(evt);
                }
        });
    }

    /* 
    * New Project option
    * User Story P02
    */
    private void newProjActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {                                           

        int size = RepositoryProjects.getProjects().size();
        ImportProject p = new ImportProject();
        if(size == RepositoryProjects.getProjects().size()){
            JOptionPane.showMessageDialog(null, "Project not imported");
        }else{
            size = RepositoryProjects.getProjects().size();
            currentModel = RepositoryProjects.getProjects().get(size-1);
            //ImportVehicles v = new ImportVehicles(prj);
            ImportSimulations s = new ImportSimulations();
            this.availableModels.add(currentModel);        
            changeCurrentProject(currentModel);
            //Html.exportSimulation(this.currentModel);
        }     
    }

    /* 
    * Copy Project option
    * User Story P03
    */
    private void copyProjActionPerformed(java.awt.event.ActionEvent evt) {                                           
        
        String id="", description="";
        
        /* Asks for a new ID for the replicated project */
        while (id == null || id.equals("")) {
            id = JOptionPane.showInputDialog("Project new ID :");
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Must provide a valid ID.");
                }
        }
        
        /* Asks for a new description for the replicated project */
        while (description == null || description.equals("")) {
            description = JOptionPane.showInputDialog("Project description :", currentModel.getDescription());
                if (description.equals("")) {
                    JOptionPane.showMessageDialog(null, "Must provide a description of the project.");
                }
        }
        
        /* Creates the replicated project */
        Project newProj = new Project(id, description, currentModel.getJunctions(), currentModel.getSections());
        availableModels.add(newProj);
        changeCurrentProject(newProj);
    }

    
    /* 
    * Edit Project option
    * User Story P04
    */
    private void editProjActionPerformed(java.awt.event.ActionEvent evt) {                                           

      JTextField id = new JTextField(10);
      JTextField description = new JTextField(20);

      JPanel panel = new JPanel();
      panel.add(new JLabel("ID: "));
      id.setText(currentModel.getId());
      panel.add(id);
      panel.add(new JLabel("Description: "));
      description.setText(currentModel.getDescription());
      panel.add(description);
      
      int result = JOptionPane.showConfirmDialog(null, panel, 
               "Change Project properties", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
            currentModel.setId(id.getText());
            currentModel.setDescription(description.getText());
            changeCurrentProject(currentModel);
      }
    }

    /*
    * Save Project option
    * It saves within a DB
    */
    private void saveProjActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {                                           
        db = new DBAccess("gandalf.dei.isep.ipp.pt", "pdborcl", "LAPR3_54", "anedjovi");
        
        db.saveProject(currentModel);
        //db.getProjectsFromDB();
        db.closeConnection();
    }
    
    /*
    * Changes Active Project
    * User Story P01
    * It opens the ChangeCurrentProj JFrame for dealing
    */
    private void changeProjActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {                                           
        
        ArrayList<String> tempArray = new ArrayList<>();

        db = new DBAccess("gandalf.dei.isep.ipp.pt", "pdborcl", "LAPR3_54", "anedjovi");
        ArrayList<Project> projects = db.getProjectsFromDB();
        
        for(Project p : projects){
            availableModels.add(p);
        }
        
        if(!availableModels.isEmpty()){

            for(Project proj:availableModels)
                tempArray.add(proj.getId() + " - " + proj.getDescription());

            jListModel = new DefaultListModel<>();

            for(String p : tempArray)
                jListModel.addElement(p);

            changeProj.projList.setModel(jListModel);     
            changeProj.projList.setSelectedIndex(0);
            changeProj.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(null, "There's no available projects.", "Change Active Project", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void applyChangeProjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        changeCurrentProject(availableModels.get(changeProj.projList.getSelectedIndex()));
        changeProj.dispose();
    }    
    
    /* Changes active project and creats graph for road network*/
    private void changeCurrentProject(Project proj) {
        
        this.currentModel=proj;
        
        mainUI.setTitle(proj.getId() + proj.getDescription());
        mainUI.analysisMenu.setEnabled(true);
        
        mainUI.jPanel1.removeAll();
        
        mainUI.jPanel1.setSize(mainUI.getWidth() - mainUI.bestPathFormPanel.getWidth()-130, mainUI.getHeight()/2);
        mainUI.jPanel1.setLayout(new BorderLayout());
        
        GraphX graphx = new GraphX(currentModel,mainUI.jPanel1.getSize());
        mainUI.jPanel1.add(graphx, BorderLayout.CENTER);
        
        //graphx.revalidate();
        //graphx.updateUI();
        
        mainUI.jPanel1.revalidate();
        mainUI.jPanel1.repaint();
        
        mainUI.jPanel1.setVisible(true);
        
    }
    

    private void bestPathMenuActionPerformed(java.awt.event.ActionEvent evt) {
        BestPathFormController bestPathController = new BestPathFormController(this.currentModel, this.mainUI);
    }

    private void vehicleCompareActionPerformed(java.awt.event.ActionEvent evt) {
        
        JList list = new JList(currentModel.getSections().toArray());
        list.setBorder(null);
        JPanel panel = new JPanel();
        panel.add(list);
        JOptionPane.showMessageDialog(null, panel);
        
    }

    
    /*
    * Imports new vehicles to the current project
    * User Story P05
    * It opens the ImportVehicles JFrame for dealing
    */    
    private void importVehiclesActionPerformed(java.awt.event.ActionEvent evt) {
        ImportVehicles v = new ImportVehicles(currentModel);
    }

    private void viewVehiclesActionPerformed(java.awt.event.ActionEvent evt) {
        JList list = new JList(currentModel.getVehicles().toArray());
        list.setBorder(null);
        JPanel panel = new JPanel();
        panel.add(list);
        JOptionPane.showMessageDialog(null, panel);
    }

    private void importRoadsActionPerformed(java.awt.event.ActionEvent evt) {
        ImportRoads v = new ImportRoads(currentModel);
    }

    private void viewRoadsActionPerformed(java.awt.event.ActionEvent evt) {
        JList list = new JList(currentModel.getSections().toArray());
        list.setBorder(null);
        JPanel panel = new JPanel();
        panel.add(list);
        JOptionPane.showMessageDialog(null, panel);
    }

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {
        // About menu
        //mainUI.add(resultsFrame);
        //mainUI.resultsFrame
        //mainUI.resultsFrame.setVisible(true);
    }

    
    
    /* Dummy project only for tests propose */
    private Project dummyModel() {
        
        Junction n0;
        Junction n1;
        Junction n2;
        Junction n3;
        Junction n4;
        Junction n5;
        Section s0;
        Section s1;
        Section s2;
        Section s3;
        Section s4;
        Section s5;
        Section s6;
        Section s7;
        Section s8;
        ArrayList<Segment> segments;
        ArrayList<Segment> segments2;

        
        n0 = new Junction("n0");
        n1 = new Junction("n1");
        n2 = new Junction("n2");
        n3 = new Junction("n3");
        n4 = new Junction("n4");
        
        segments=new ArrayList<>();
        segments2=new ArrayList<>();
        Segment dummy = new Segment();
        dummy.setLength(1);
        dummy.setMaximumVelocity(50);
        segments.add(dummy);
        segments2.add(dummy);
        
        for(int i=0; i<20; i++)
            segments2.add(dummy);

        
        s0 = new Section(n0, n1, "s0", Section.Typology.CONTROLLED.toString(), Section.Direction.BIDIRECTIONAL.toString(), 0, null, segments, null);
        s1 = new Section(n1, n2, "s1", Section.Typology.EXPRESS.toString(), Section.Direction.DIRECT.toString(), 0, null, segments, null);
        s2 = new Section(n2, n0, "s2", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s3 = new Section(n0, n2, "s3", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s4 = new Section(n2, n3, "s4", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);
        s5 = new Section(n0, n3, "s5", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments2, null);
        s6 = new Section(n3, n4, "s6", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);    
        s7 = new Section(n4, n1, "s7", Section.Typology.URBAN.toString(), Section.Direction.REVERSE.toString(), 0, null, segments, null);    

        
        ArrayList<Junction> junctions = new ArrayList<>();
        ArrayList<Section> sections = new ArrayList<>();
        
        junctions.add(n0);
        junctions.add(n1);
        junctions.add(n2);
        junctions.add(n3);
        junctions.add(n4);
        sections.add(s0);
        sections.add(s1);
        sections.add(s2);        
        sections.add(s3);        
        sections.add(s4);        
        sections.add(s5);        
        sections.add(s6);        
        sections.add(s7);        
        
        return new Project("Dummy Project", "Test Project", junctions, sections);
    }

       
}
