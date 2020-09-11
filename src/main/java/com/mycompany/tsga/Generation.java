package com.mycompany.tsga;

import java.util.Arrays;
import java.awt.Point;

/**
 *
 * @author Troy Gayman
 */
public class Generation {

    private Individual[] population;
    private int populationSize;
    
    /*
        Constructor used to create an initial generation populated with random paths
    */
    public Generation(Point[] cities, int populationSize) {
        this.populationSize = populationSize;
        this.population = fillPopulation(cities);
        Arrays.sort(this.population);
    }
    
    /*
        Constructor used to create an evolved generation that accepts a premade population
    */
    public Generation(Individual[] population, int populationSize) {
        this.populationSize = populationSize;
        this.population = population;
        Arrays.sort(this.population);
    }

    /*
        Creates a population of individuals to fill this generation
    */
    private Individual[] fillPopulation(Point[] cities) {
        Individual[] randGen = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            randGen[i] = new Individual(cities, true);
        }
        return randGen;
    }

    /*----------------Get/Set/Print methods below------------------*/

    public void printGen() {
        System.out.println("printing generation");
        for (Individual i : population) {
            i.printIndv();
        }
        System.out.println("");
    }

    public Individual[] getPopulation() {
        return population;
    }
    
    public Individual getBest() {
        return population[0];
    }

    public Individual getMedian() {
        return population[(int) (populationSize * 0.5)];
    }

    public Individual getWorst() {
        return population[populationSize - 1];
    }
}
