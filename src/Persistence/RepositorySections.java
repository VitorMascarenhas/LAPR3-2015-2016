/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Junction;
import Model.Section;
import java.util.*;
/**
 *
 * @author vitoralexandremascarenhasmascarenhas
 */
public class RepositorySections {
    
    private static ArrayList<Section> sections = new ArrayList<>();
    
    public void addSection(Section section) {
        sections.add(section);
    }
    
    public Section getSection(Junction j1, Junction j2) {
        
        for(Section s : this.sections) {
            if(s.getStartingNode().getId().equalsIgnoreCase(j1.getId()) && s.getEndingNode().getId().equalsIgnoreCase(j2.getId())) {
                return s;
            }
        }
        return null;
    }
}