/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tsga;

import java.awt.Point;
import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author troyg
 */
public class SimulationController {

    private final int pathSize = 5;
    private final int gridX = 15;
    private final int gridY = 10;
    private final int genSize = 5;
    private Generation currentGen;
    private Point[] path;

    public SimulationController() {
        path = createPath().clone();
        currentGen = new Generation(fillGeneration());
        System.out.println(Arrays.toString(path));
        currentGen.printGen();
//        for(int i = 0; i < 5; i++){
//           currentGen.printGen();
//           currentGen = currentGen.evolve();
//                   }
    }

    public Generation getGen() {
        return currentGen;
    }

    private Individual[] fillGeneration() {
        Individual[] randGen = new Individual[genSize];
        for (int i = 0; i < genSize; i++) {
            randGen[i] = new Individual(path, true);
        }
        return randGen;
    }

    public Point[] getPath() {
        return path;
    }
    
    public int getGridX(){
        return gridX;
    }
    public int getGridY(){
        return gridY;
    }

    //checked
    public Point[] createPath() { //create an array of pathSize number of random, unique points.
        Point[] tmpPath = new Point[pathSize];
        Random r = new Random();

        for (int i = 0; i < pathSize; i++) {
            Point p = new Point(r.nextInt(gridX), r.nextInt(gridY));

            while (!isNewPoint(tmpPath, p)) { //ensure new points are unique
                p = new Point(r.nextInt(gridX), r.nextInt(gridY));
            }

            tmpPath[i] = p;
        }
        return tmpPath;
    }

    //checked
    public boolean isNewPoint(Point[] tmpPath, Point p) { // check if Point p is already in Point[] path
        boolean isNewPoint = true;
        for (int i = 0; i < tmpPath.length; i++) {
            if (tmpPath[i] != null) {
                if (tmpPath[i].equals(p)) {
                    return false;
                }
            }
        }
        return isNewPoint;
    }

}
