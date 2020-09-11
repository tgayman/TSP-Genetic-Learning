package com.mycompany.tsga;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Troy Gayman
 */
public class UIController {

    /*
        Constructor which initializes the GUI frame and adds the main panel
     */
    public UIController(SimulationController sim) {
        JFrame frame = createJFrame();
        JPanel mainP = new MainPanel(sim);
        frame.add(mainP);
        frame.setVisible(true);
    }

    /*
        Helper method for the constructor. Creates the JFrame used for the 
        GUI
     */
    public JFrame createJFrame() {
        JFrame frame = new JFrame("TSP");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }
}
