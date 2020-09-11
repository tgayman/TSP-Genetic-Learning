package com.mycompany.tsga;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Troy Gayman
 */
public class SimulationController {

    private final int pathSize = 25;
    private final int gridX = 15;
    private final int gridY = 10;
    private final int populationSize = 1000;
    private int generationNumber;
    private Generation generation;
    private Point[] cities;
    private Individual greedyPath;

    /*
        Constructor which initializes the simulation, including generation 0
     */
    public SimulationController() {
        this.cities = initCities().clone();
        this.generation = new Generation(cities, populationSize);
        generationNumber = 0;
        initGreedyPath();
    }

    /*
        Method to reset the simulation in the GUI, starts again
        with new nodes and generation 0
     */
    public void reset() {
        this.cities = initCities().clone();
        this.generation = new Generation(cities, populationSize);
        generationNumber = 0;
        initGreedyPath();
    }

    /*
        A method to evolve the current generation, only utalizes mutation, or swapping
        rather than genome crossing which is not implemented here.
        It uses both mutateMajor(), and mutateMinor()
        Adjustments are made after generation 75 because drastic mutations are
        desireable in the beginning of the expirament, and smaller changes are 
        better later on.
     */
    public void swapEvolve() {
        Individual[] evolvedPopulation = generation.getPopulation().clone();
        int highPopStrata;
        if (generationNumber > 75) {
            highPopStrata = Math.max(2, (int) (populationSize * 0.1));
        } else {
            highPopStrata = Math.max(2, (int) (populationSize * 0.3));
        }
        Random r = new Random();
        for (int i = highPopStrata; i < populationSize; i++) {
            if (r.nextBoolean() || generationNumber > 75) {
                evolvedPopulation[i] = mutateMinor(evolvedPopulation[i % highPopStrata]);
            } else {
                evolvedPopulation[i] = mutateMajor(evolvedPopulation[i % highPopStrata]);
            }
        }

        Generation evolvedGeneration = new Generation(evolvedPopulation, populationSize);
        this.generation = evolvedGeneration;
        this.generationNumber++;
    }

    /*
        Initializes the individual greedyPath which contains the greedy algorithm path
        and distance
     */
    private void initGreedyPath() {
        Point[] greedy = new Point[pathSize];
        ArrayList<Point> pathList = new ArrayList();
        Collections.addAll(pathList, cities);
        greedy[0] = pathList.get(0);
        pathList.remove(0);
        for (int i = 1; i < greedy.length; i++) {
            greedy[i] = getClosestPoint(greedy[i - 1], pathList);
            pathList.remove(greedy[i]);
        }
        this.greedyPath = new Individual(greedy, false);
    }

    /*
        A helper method for initGreedyPath(), which determines the closest
        point from pointList, and point p
     */
    public Point getClosestPoint(Point p, ArrayList<Point> pointList) {
        double minDist = 100;
        Point ret = null;
        for (Point p2 : pointList) {
            double deltaX = Math.abs(p.x - p2.x);
            double deltaY = Math.abs(p.y - p2.y);
            double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            if (dist < minDist) {
                ret = p2;
                minDist = dist;
            }
        }
        return ret;
    }

    /*
        A mutation method which only swaps two points in the genome
     */
    private Individual mutateMinor(Individual i) {
        Point[] path = i.getPath().clone();
        Random r = new Random();
        int index = r.nextInt(path.length - 1) + 1;
        int index2 = r.nextInt(path.length - 1) + 1;
        Point tmp = path[index];
        path[index] = path[index2];
        path[index2] = tmp;
        return new Individual(path, false);
    }

    /*
        A mutation method which makes a swap for 10% of the nodes in the path
     */
    private Individual mutateMajor(Individual i) {
        Point[] path = i.getPath().clone();
        int gamma = Math.max(5, (int) (path.length * 0.1));
        for (int mutateCount = 0; mutateCount < gamma; mutateCount++) {
            Random r = new Random();
            int index = r.nextInt(path.length - 1) + 1;
            int index2 = r.nextInt(path.length - 1) + 1;
            Point tmp = path[index];
            path[index] = path[index2];
            path[index2] = tmp;
        }
        return new Individual(path, false);
    }

    /*
        Helper method for the constructor. It creates the base nodes in the 
        TSP problem, all of the nodes must be visited, starting and ending at the first
        node
     */
    public Point[] initCities() {
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

    /*
        A helper method for initCities(). It ensures that there are
        no duplicated nodes
     */
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

    /*-------------------------- Print/Get/Set Methods Below ----------------------*/
    public Point[] getCities() {
        return cities;
    }

    public Individual getGreedy() {
        return greedyPath;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public Generation getGen() {
        return generation;
    }
}
