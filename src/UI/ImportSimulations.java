/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Factory.ProjectFactory;
import Model.Simulation;

import Persistence.IO;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rekgnyz
 */
public class ImportSimulations extends JInternalFrame{
     public ImportSimulations(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Import Simulation File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false); 
        //chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(ImportSimulations.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try{
                File fi = chooser.getSelectedFile();
                String name = chooser.getDescription(fi);
                name = getExtension(name);
                System.out.println(name);
                
                ProjectFactory pj = ProjectFactory.getInstance();
                IO estrategia = pj.getEstrategia(name);
                ArrayList<Simulation> simulations = estrategia.importSimulation(fi);
                //p.setVehicles(vehicles);
                System.out.println("simulations size" + simulations.size());
            }catch(Exception e2){
                JOptionPane.showMessageDialog(null, e2);
            }
        } else if (option == JFileChooser.CANCEL_OPTION) {
            System.out.println(JFileChooser.CANCEL_OPTION);
        }
    }
       
    /**
     * Method removes de extension from a string
     * @param fileName string with name+.+extention
     * @return String extention
     */   
    private String getExtension(String fileName){
        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
        return tokens[1];
    }



class ExtensionFileFilter implements FileFilter {

        String description;
        String extensions[];

        public ExtensionFileFilter(String description, String extension) {
            this(description, new String[]{extension});
        }

        public ExtensionFileFilter(String description, String extensions[]) {
            if (description == null) {
                this.description = extensions[0];
            } else {
                this.description = description;
            }
            this.extensions = (String[]) extensions.clone();
            toLower(this.extensions);
        }

        private void toLower(String array[]) {
            for (int i = 0, n = array.length; i < n; i++) {
                array[i] = array[i].toLowerCase();
            }
        }

        public String getDescription() {
            return description;
        }

        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            } else {
                String path = file.getAbsolutePath().toLowerCase();
                for (int i = 0, n = extensions.length; i < n; i++) {
                    String extension = extensions[i];
                    if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
