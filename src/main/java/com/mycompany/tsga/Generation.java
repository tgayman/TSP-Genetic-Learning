/*
 */
package com.mycompany.tsga;

import java.util.Arrays;

/**
 *
 * @author troyg
 */
public class Generation {

    private Individual[] generation;

    public Generation(Individual[] genIn) {//Creates a generation zero of individuals with random genomes
        generation = genIn.clone();
        Arrays.sort(generation);
    }

    public Individual[] getGenArray() {
        return generation;
    }


    public void printGen() {
        System.out.println("printing generation");
        for (Individual i : generation) {
            i.printIndv();
        }
        System.out.println("");
    }


    //mutates evathang
    public Generation evolve(){
        Individual[] newGen = generation.clone();
        for(int i = 0; i < newGen.length; i++){
            newGen[i] = generation[i].mutate();
        }
        return new Generation(newGen);
    }
    
    public Individual getTop(){
        return generation[0];
    }
}