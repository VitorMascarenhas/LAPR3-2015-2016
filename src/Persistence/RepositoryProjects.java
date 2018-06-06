/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Project;
import java.util.ArrayList;

/**
 *
 * @author suq-madik
 */
public class RepositoryProjects {
    private static ArrayList<Project> projects = new ArrayList<>();

    /**
     * @return the projects
     */
    public static ArrayList<Project> getProjects() {
        return projects;
    }
    
    public RepositoryProjects(Project p){
        projects.add(p);
    }
    
    public static void addProject(Project p){
        getProjects().add(p);
    }
}
