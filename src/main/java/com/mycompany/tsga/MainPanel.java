/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.Arrays;
import javax.swing.JComponent;

public class MainPanel extends JPanel {

    private int gridX;
    private int gridY;
    private int nodeDiameter = 10;

    private int bufferX;
    private int bufferY;
    private int screenX;
    private int screenY;
    private final double simYRatio = 0.8;
    private Point[] path;
    //private Individual curInd;

    public MainPanel(Point[] pathIn, int gridXIn, int gridYIn) {
        path = pathIn;
        gridX = gridXIn;
        gridY = gridYIn;
        System.out.println(Arrays.toString(path));
        screenAdjustment();
        //addTable();
        addButton0();
        this.setVisible(true);

    }

    public void screenAdjustment() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenX = (int) screenSize.getWidth();
        screenY = (int) screenSize.getHeight();
        bufferX = ((int) screenX - (gridX * nodeDiameter)) / (gridX + 1);
        int simY = (int) (screenY * simYRatio) - 23;
        bufferY = (((int) simY - (gridY * nodeDiameter)) / (gridY + 1));
    }

    public void addTable() {

        String[][] tableInfo = {
            {"Worst Path", "Uno"},
            {"Median Path", "Dos"},
            {"Best Path", "Tres"},
            {"Greedy Path", "Quatro"}
        };

        // Column Names 
        String[] columnName = {"----------", "Distance"};
        JTable table = new JTable(tableInfo, columnName);
        //able.setBounds(30, 40, 200, 300);
        this.add(new JScrollPane(table));
    }

    public void addButton0() {
        JButton b0 = new JButton("test");
        b0.setSize(75, 50);
        //b0.setLocation(1100, 200);
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        this.add(b0);
    }

    private void update() {
    }

    public void paintSimNodes(Graphics g) {
        g.fillOval(1356, 735, nodeDiameter, nodeDiameter);
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                g.setColor(Color.WHITE);
                if (path[0].x == i & path[0].y == j) {
                    g.setColor(Color.GREEN);
                }
                Point pix = getPixelLoc(new Point(i, j));
                g.fillOval(pix.x, pix.y, nodeDiameter, nodeDiameter);
            }
        }

        g.setColor(Color.BLUE);
        for (int i = 1; i < path.length; i++) {
            Point p = path[i];
            Point pix = getPixelLoc(p);
            g.fillOval(pix.x, pix.y, nodeDiameter, nodeDiameter);
        }

    }

    public void paintSimBorder(Graphics g) {
        int borderY = (int) (screenY * (1 - simYRatio));
        g.drawLine(0, borderY, screenX, borderY);
    }

    public void paintRoute(Point[] route, Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < route.length; i++) {
            Point p1 = getPixelLoc(route[i]);
            Point p2 = getPixelLoc(route[(i + 1) % route.length]); // %connects last index to first index
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public Point getPixelLoc(Point p) {
        Point pix = new Point();
        int x = p.x * nodeDiameter + (p.x + 1) * bufferX;
        int y = (int) (screenY * (1 - simYRatio)) + p.y * nodeDiameter + (p.y + 1) * bufferY;
        pix.setLocation(x, y);
        return pix;
    }

//    public void setCurInd(Individual i){
//        currInd = i;
//    }
    
    //fill frame
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) screenX, (int) screenY);
        paintSimNodes(g);
        paintSimBorder(g);
        Point p = getPixelLoc(new Point(0,9));
        Point p2 = getPixelLoc(new Point(14,0));
        int rad = nodeDiameter/2;
        g.drawLine(p.x + rad, p.y+ rad, p2.x+ rad, p2.y+ rad);
//paintRoute(curInd.getGenome(),g);
    }

    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

}
