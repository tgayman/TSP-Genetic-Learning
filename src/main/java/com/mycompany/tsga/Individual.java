package com.mycompany.tsga;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Troy Gayman
 */
public class Individual implements Comparable<Individual> {

    private Point[] path;
    private final double distance;

    /*
        Constructor that sets this individuals path to be an argument, 
        or random, depending on boolean shuffle
     */
    public Individual(Point[] path, boolean shuffle) {
        if (shuffle) {
            this.path = shufflePath(path).clone();
        } else {
            this.path = path;
        }
        distance = calculateDistance();
    }

    /*
        Takes in a point array path, shuffles all but the first element of path
        and returns the output.
     */
    private Point[] shufflePath(Point[] path) {
        Point[] newGen = path.clone();
        Random r = new Random();
        for (int i = 1; i < newGen.length; i++) {
            int index = r.nextInt(newGen.length - 2) + 1;
            Point p = newGen[i];
            newGen[i] = newGen[index];
            newGen[index] = p;
        }
        return newGen;
    }
    
    /*
        This method is necessary for the generation class to sort a list
        of individuals
    */
    @Override
    public int compareTo(Individual o) {
        if (distance < o.getDistance()) {
            return -1;
        } else if (o.getDistance() < distance) {
            return 1;
        }
        return 0;
    }

    /*
        Calculates the total distance(double) between all consecutive
        points in this individual's path
    */
    private double calculateDistance() {
        double dist = 0;
        for (int i = 1; i < path.length - 1; i++) {
            dist += distBetweenPoints(path[i], path[i - 1]);
        }
        dist += distBetweenPoints(path[path.length - 1], path[0]);
        return dist;
    }

    /*
        Returns the distance between two points, a helper method 
        for calculateDistance()
    */
    private double distBetweenPoints(Point p, Point q) {
        double deltaX = Math.abs(p.x - q.x);
        double deltaY = Math.abs(p.y - q.y);
        double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return dist;
    }

    /*----------------- Print/Get/Set Methods ------------------ */
    public void printIndv() {
        System.out.print("INDV : " + distance + " : ");
        for (Point p : path) {
            System.out.print("(" + p.x + "," + p.y + ")  ");
        }
        System.out.println("");
    }

    public Point[] getPath() {
        return path;
    }

    public double getDistance() {
        return distance;
    }
}
