package com.mycompany.tsga;

/**
 *
 * @author Troy Gayman
 */
public class Runner {

    /*
        Starting point for the application
    */
    public static void main(String[] args) {
        SimulationController sim = new SimulationController();
        UIController gui = new UIController(sim);
    }
}
