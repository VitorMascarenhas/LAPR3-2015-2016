/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author 11011_000
 */


import Model.Junction;
import Model.Project;
import Model.Section;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.HashMap;
import java.util.Map;

public class GraphDesign extends JFrame
{
    public GraphDesign(Project model)
    {
        super("Road Network for project " + model.getId());

        Map<String, Object> junc = new HashMap<>();

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        int i=20, j=20;

        for(Junction obj : model.getJunctions()) {
            junc.put(obj.getId(), graph.insertVertex(parent, null, obj.getId(), i, j, 30,30));
            i+=40;
            j+=100;
        }

        graph.getModel().beginUpdate();
        try
        {
            for (Section section : model.getSections()) {
                graph.insertEdge(parent, null, section.getRoad(), junc.get(section.getStartingNode().getId()), junc.get(section.getEndingNode().getId()));
                if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                    Section mirror = section.getMirroredSection();
                    graph.insertEdge(parent, null, mirror.getRoad(), junc.get(mirror.getStartingNode().getId()), junc.get(mirror.getEndingNode().getId()));
                }
            }
        }
        finally
        {
            graph.getModel().endUpdate();
        }
        
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }  
}