package com.mycompany.tsga;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Troy Gayman
 */
public class MainPanel extends JPanel {

    private int gridX;
    private int gridY;
    private final int nodeDiameter = 10;
    private String rank;
    private JTable table;
    private int bufferX;
    private int bufferY;
    private int screenX;
    private int screenY;
    private final double simYRatio = 0.8;
    private SimulationController sim;

    public MainPanel(SimulationController sim) {
        this.sim = sim;
        gridX = sim.getGridX();
        gridY = sim.getGridY();
        rank = "best";

        setLayout(null);
        screenAdjustment();
        addButtonBest();
        addButtonMedian();
        addButtonWorst();
        addButtonEvolve();
        addButtonGreedy();
        addButtonReset();
        addTable();
        this.setVisible(true);
    }

    /*
        Dynamically adjusts GUI parameters to fit the screen
     */
    public void screenAdjustment() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenX = (int) screenSize.getWidth();
        screenY = (int) screenSize.getHeight();
        bufferX = ((int) screenX - (gridX * nodeDiameter)) / (gridX + 1);
        int simY = (int) (screenY * simYRatio) - 23;
        bufferY = (((int) simY - (gridY * nodeDiameter)) / (gridY + 1));
    }

    /*
        Initializes and adds an information table to the GUI
     */
    public void addTable() {
        String[][] tableInfo = {
            {"Best Path", formatDouble(sim.getGen().getBest().getDistance())},
            {"Median Path", formatDouble(sim.getGen().getMedian().getDistance())},
            {"Worst Path", formatDouble(sim.getGen().getWorst().getDistance())},
            {"Greedy Path", formatDouble(sim.getGreedy().getDistance())},
            {"Generation : " + sim.getGenerationNumber(), ""}
        };

        String[] columnName = {"----------", "Distance"};
        JTable tab = new JTable(tableInfo, columnName);
        JScrollPane tabS = new JScrollPane(tab);
        tabS.setSize(300, 103);
        tabS.setLocation(950, 10);
        this.add(tabS);
        table = tab;
    }

    /*
        Adds a button that displays the best individual's path in a generation
        on the GUI
     */
    public void addButtonBest() {
        JButton b0 = new JButton("See Best");
        b0.setSize(100, 50);
        b0.setLocation(25, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rank = "best";
                repaint();
            }
        });
        this.add(b0);
    }

    /*
        Adds a button that displays the median individual path in a generation
        on the GUI
     */
    public void addButtonMedian() {
        JButton b0 = new JButton("See Median");
        b0.setSize(100, 50);
        b0.setLocation(175, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rank = "median";
                repaint();
            }
        });
        this.add(b0);
    }

    /*
        Adds a button that displays the worst individual path in a generation
        on the GUI
     */
    public void addButtonWorst() {
        JButton b0 = new JButton("See Worst");
        b0.setSize(100, 50);
        b0.setLocation(325, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rank = "worst";
                repaint();

            }
        });
        this.add(b0);
    }

    /*
        Adds a button that displays the greedy algorithm path
     */
    public void addButtonGreedy() {
        JButton b0 = new JButton("See Greedy");
        b0.setSize(100, 50);
        b0.setLocation(475, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rank = "greedy";
                repaint();
            }
        });
        this.add(b0);
    }

    /*
        Adds a button that prompts the simulationController class
        to evolve the generation
     */
    public void addButtonEvolve() {
        JButton b0 = new JButton("Evolve");
        b0.setSize(100, 50);
        b0.setLocation(625, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sim.swapEvolve();
                rank = "best";
                updateTable();
                repaint();
            }
        });
        this.add(b0);
    }

    /*
        Adds a button that resets the simulationController and runs 
        a new expirement
     */
    public void addButtonReset() {
        JButton b0 = new JButton("Reset");
        b0.setSize(100, 50);
        b0.setLocation(775, 50);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sim.reset();
                updateTable();
                repaint();
            }
        });
        this.add(b0);
    }

    /*
        Updates the information table after resets, and evolutions
     */
    public void updateTable() {
        table.setValueAt(formatDouble(sim.getGen().getBest().getDistance()), 0, 1);
        table.setValueAt(formatDouble(sim.getGen().getMedian().getDistance()), 1, 1);
        table.setValueAt(formatDouble(sim.getGen().getWorst().getDistance()), 2, 1);
        table.setValueAt("Generation : " + sim.getGenerationNumber(), 4, 0);
    }

    /*
        Paints the nodes in the GUI only, not the lines connecting path nodes
     */
    public void paintSimNodes(Graphics g) {
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                g.setColor(Color.WHITE);
                if (sim.getCities()[0].x == i & sim.getCities()[0].y == j) {
                    g.setColor(Color.GREEN);
                }
                Point pix = getPixelLoc(new Point(i, j));
                g.fillOval(pix.x, pix.y, nodeDiameter, nodeDiameter);
            }
        }

        g.setColor(Color.BLUE);
        for (int i = 1; i < sim.getCities().length; i++) {
            Point p = sim.getCities()[i];
            Point pix = getPixelLoc(p);
            g.fillOval(pix.x, pix.y, nodeDiameter, nodeDiameter);
        }
    }

    /*
        Helper method for the information table, which rounds doubles
        to two decimal places
     */
    public String formatDouble(double dist) {
        return String.format("%.2f", dist);
    }

    /*
        Paints the border of the node grid in the GUI
     */
    public void paintSimBorder(Graphics g) {
        int borderY = (int) (screenY * (1 - simYRatio));
        g.drawLine(0, borderY, screenX, borderY);
    }

    /*
        Paints the connections between path nodes
     */
    public void paintRoute(Graphics g) {
        Point[] route = getRoute();
        g.setColor(Color.BLUE);
        int radius = nodeDiameter / 2;
        for (int i = 0; i < route.length; i++) {
            Point p1 = getPixelLoc(route[i]);
            Point p2 = getPixelLoc(route[(i + 1) % route.length]); // %connects last index to first index
            g.drawLine(p1.x + radius, p1.y + radius, p2.x + radius, p2.y + radius);
        }
    }


    /*
        Helper metod for paintRoute(), looks at the variable rank,
        and returns the appropriate path
     */
    public Point[] getRoute() {
        if (rank.equals("worst")) {
            return sim.getGen().getWorst().getPath();
        } else if (rank.equals("median")) {
            return sim.getGen().getMedian().getPath();
        } else if (rank.equals("greedy")) {
            return sim.getGreedy().getPath();
        } else {
            return sim.getGen().getBest().getPath();
        }
    }

    /*
        Returns the upper left pixel location of a node at point p. A helper method 
        for building the GUI.
     */
    public Point getPixelLoc(Point p) {
        Point pix = new Point();
        int x = p.x * nodeDiameter + (p.x + 1) * bufferX;
        int y = (int) (screenY * (1 - simYRatio)) + p.y * nodeDiameter + (p.y + 1) * bufferY;
        pix.setLocation(x, y);
        return pix;
    }

    /*
        Draw the gui, it is called everytime repaint() is called.
     */
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) screenX, (int) screenY);
        paintSimNodes(g);
        paintSimBorder(g);
        paintRoute(g);
    }
}
