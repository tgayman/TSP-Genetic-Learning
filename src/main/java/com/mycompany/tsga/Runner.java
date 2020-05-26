package com.mycompany.tsga;
import java.awt.Point;
/**
 *
 * @author Troy Gayman
 */
public class Runner {
    public static void main(String []args){
        
    SimulationController sim = new SimulationController();
    UIController gui = new UIController(sim.getPath(), sim.getGridX(), sim.getGridY());
    
    }
}
