/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tsga;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author troyg
 */
public class Individual implements Comparable<Individual> {

    private Point[] genome;
    private final double distance;

    public Individual(Point[] path, boolean shuffle) {
        if (shuffle) {
            genome = shuffleGenome(path).clone();
        } else {
            genome = path;
        }
        distance = calculateDistance();
    }

    //checked
    public Individual mutate() {
        Point[] path = genome.clone();
        Random r = new Random();
        int index = r.nextInt(path.length - 1) + 1;
        int index2 = r.nextInt(path.length - 1) + 1;
        Point tmp = path[index];                //swap two elements
        path[index] = path[index2];
        path[index2] = tmp;
        return new Individual(path, false);
    }

    @Override
    public int compareTo(Individual o) {
        if (distance < o.getDistance()) {
            return -1;
        } else if (o.getDistance() < distance) {
            return 1;
        }
        return 0;
    }

    //checked
    private Point[] shuffleGenome(Point[] path) {
        Point[] newGen = path.clone();
        Random r = new Random();
        for (int i = 1; i < newGen.length - 1; i++) { // shuffle all but the first and last elements of the array
            int index = r.nextInt(newGen.length - 2) + 1;
            Point p = newGen[i];
            newGen[i] = newGen[index];
            newGen[index] = p;
        }
        return newGen;
    }

    public Point[] getGenome(){
        return genome;
    }
    //checked
    public void printIndv() {
        System.out.print("INDV : " + distance + " : ");
        for (Point p : genome) {
            System.out.print("(" + p.x + "," + p.y + ")  ");
        }
        System.out.println("");
    }

    //checked
    public double getDistance() {
        return distance;
    }

    //checked
    private double calculateDistance() {
        double dist = 0;
        for (int i = 1; i < genome.length - 1; i++) {
            dist += distBetweenPoints(genome[i], genome[i - 1]);
        }
        dist += distBetweenPoints(genome[genome.length - 1], genome[0]);
        return dist;
    }

    //checked
    private double distBetweenPoints(Point p, Point q) {
        double deltaX = Math.abs(p.x - q.x);
        double deltaY = Math.abs(p.y - q.y);
        double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return dist;
    }
}
