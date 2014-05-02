import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DrawFrame extends JFrame {
    private mxGraphComponent graphComponent;

    public DrawFrame(String name){
        super("Graph " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setVisible(false);
        mxGraph graph = new mxGraph();
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setEnabled(true);
        graph.setCellsEditable(false);
        getContentPane().add(graphComponent);
        pack();
    }

    public DrawFrame(String name, Levels levels) {
        this(name);
        drawLevels(levels);
        pack();
        setVisible(true);
    }

    public DrawFrame(String name, Schedule schedule) {
        this(name);
        drawSchedule(schedule);
        pack();
        setVisible(true);
    }

    private void drawLevels(Levels levels) {
        mxGraph graph = graphComponent.getGraph();
        Object graphParent = graph.getDefaultParent();
        String vertexFormat = "fillColor=#FFFFFF;strokeColor=#000000;fontColor=#000000;shape=ellipse;verticalAlign:middle;align:center";
        String edgeFormat = "strokeColor=#000099;fontColor=#000000";

        // tasks count
        int count = 0;
        for (Level level : levels) {
            count += level.size();
        }

        graph.getModel().beginUpdate();
        try {
            FontMetrics fm = graphComponent.getGraphics().getFontMetrics();
            mxCell[] cells = new mxCell[count];
            // add vertexes
            for (int i = 0; i < levels.size(); i++) {
                ArrayList<Task> level = levels.get(i);
                for (int j = 0; j < level.size(); j++)
                {
                    Task task = level.get(j);
                    String label = String.format("%d[%d]", task.id, task.weight);
                    cells[task.id] = (mxCell)graph.insertVertex(
                        graphParent, null, label,
                        120 * j + 20, 90 * i + 20, fm.stringWidth(String.valueOf(i + 1)) + 50, fm.getHeight() + 15,
                        vertexFormat
                    );
                }
            }
            System.out.println(levels.size());

            // add edges
            for (Level level : levels) {
                for (Task parent : level) {
                    for (Task child: parent.getChildren().keySet()) {
                        String label = parent.getChildren().get(child).toString();
                        graph.insertEdge(graphParent, null, label, cells[parent.id], cells[child.id], edgeFormat);
                    }
                }
            }
        }
        finally {
            graph.getModel().endUpdate();
        }
    }

    private void drawSchedule(Schedule schedule) {
        final int SY = 20;
        final int SX = 100;
        final int SW = schedule.processors.size();
        mxGraph graph = graphComponent.getGraph();
        Object graphParent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        String procFormat = "fontColor=#000000;shape=none";
        String transFormat = "strokeColor=#FF1F00;fontColor=#000000;fontSize=12;";
        String lineFormat = "strokeColor=#AAAAAA;fontColor=#000000;shape=line";
        String taskFormat = "strokeColor=#004400;fontColor=#000000;shape=rectangle;verticalAlign:middle;align:center";
        String timeLabelFormat = "fontColor=#000000;shape=rectangle;fillColor=#FFFFFF;strokeColor=#FFFFFF";
        try {
            for (int i = 0; i < schedule.processors.size(); i++) {
                String procLabel = String.format("P%d", i + 1);
                graph.insertVertex(graphParent, null, procLabel, 100 + i * SX, 0, 50, 20, procFormat);
            }
            mxCell[] cells = new mxCell[schedule.tasksCount()];
            for (Map.Entry<Task, Processor> assign : schedule.assignes.entrySet()) {
                Task task = assign.getKey();
                Processor proc = assign.getValue();
                Integer start = schedule.getStartTime(task);
                Integer end = start + task.weight;

                graph.insertVertex(
                    graphParent, null, start.toString(),
                    0, 20 + start * SY - SY + 1, 20, SY - 1,
                    timeLabelFormat
                );
                graph.insertVertex(
                    graphParent, null, "",
                    0, 20 + start * SY, 100 + SW * 100, 1,
                    lineFormat
                );
                graph.insertVertex(
                    graphParent, null, end.toString(),
                    0, 20 + end * SY - SY + 1, 20, SY - 1,
                    timeLabelFormat
                );
                graph.insertVertex(
                    graphParent, null, "",
                    0, 20 + end * SY, 100 + SW * 100, 1,
                    lineFormat
                );

                String taskLabel = String.format("T%d [%d]", task.id, task.weight);
                cells[task.id] = (mxCell)graph.insertVertex(
                        graphParent, null, taskLabel,
                        100 + proc.id * SX,
                        20 + start * SY, 50, task.weight * SY,
                        taskFormat
                );

            }
            for (Task task : schedule.assignes.keySet()) {
                for (Map.Entry<Task, Integer> pair : task.getChildren().entrySet()) {
                    Task child = pair.getKey();
                    Integer weight = pair.getValue();
//                    if (schedule.assignes.get(child) == schedule.assignes.get(task))
//                        continue;
                    String transLabel = weight.toString();
                    graph.insertEdge(graphParent, null, transLabel, cells[task.id], cells[child.id], transFormat);
                }
            }
        }
        finally {
            graph.getModel().endUpdate();
        }
    }

}
