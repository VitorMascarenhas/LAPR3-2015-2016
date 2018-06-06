/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

/**
 *
 * @author 11011_000
 */


import Model.Junction;
import Model.Project;
import Model.Section;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class GraphX extends JPanel
{
    private final mxGraphComponent graphComponent;
    
    public GraphX(Project model, Dimension dimension)
    {
        Map<String, Object> junc = new HashMap<>();
        
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        int i=10, j=dimension.height/2;
        int widthGap = (dimension.width-(i*2))/(model.getJunctions().size()*25);
        int k=-1;
        int l=j;
        
        String road;
        String distance;
        String toll;
        
        for(Junction obj : model.getJunctions()) {
            junc.put(obj.getId(), graph.insertVertex(parent, null, obj.getId(), i, j, 25,25));
            i+=(widthGap*25);
            j=(dimension.height/2+(k*(j/2)))+(25*k);
            k=k*(-1);
        }

        graph.getModel().beginUpdate();
        try
        {
            for (Section section : model.getSections()) {
                road=section.getRoad();
                distance=String.format("%.1f",section.getLength()) + " Km";
                toll=String.format("%.2f",section.getToll()) + " €";
                graph.insertEdge(parent, null, road + " / " + distance + ": " + toll, junc.get(section.getStartingNode().getId()), junc.get(section.getEndingNode().getId()));
                if(section.getDirection().toString().equals("BIDIRECTIONAL")) {
                    Section mirror = section.getMirroredSection();
                    graph.insertEdge(parent, null, "", junc.get(mirror.getStartingNode().getId()), junc.get(mirror.getEndingNode().getId()));
                }
            }
        }
        finally
        {
            graph.getModel().endUpdate();
        }
        
        graphComponent = new mxGraphComponent(graph);
        new mxOrthogonalLayout(graph).execute(graph.getDefaultParent());
        this.add(graphComponent);
        this.setBorder(null);
        this.setSize(dimension);
    }
    
    public mxGraphComponent getGraphComponent() {
        return graphComponent;
    }
}