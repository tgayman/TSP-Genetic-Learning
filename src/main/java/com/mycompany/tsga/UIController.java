/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tsga;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Point;
/**
 *
 * @author troyg
 */
public class UIController {
    private Point[] path;
    
    public UIController(Point[] pathIn, int gridXIn, int gridYIn) {
        path = pathIn;
        JFrame frame = createJFrame();
        JPanel mainP = new MainPanel(path, gridXIn, gridYIn);
        frame.add(mainP);
        frame.setVisible(true);
    }

    public JFrame createJFrame() {
        JFrame frame = new JFrame("TSP");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    
}
