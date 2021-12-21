/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell
 */
public class WeightedGraph {
   
    int numOfVertices;
    int[][] adjMat;

    public WeightedGraph(int v) {
        this.numOfVertices = v;
        adjMat = new int[v][v];
    }

    public void add_Edge(int source, int destination, int weight) {
        adjMat[--source][--destination] = weight;

    }

    public void print() {
        for (int row = 0; row < adjMat.length; row++)//Cycles through rows
        {
            for (int col = 0; col < adjMat[row].length; col++)//Cycles through columns
            {
                System.out.printf("%5d", adjMat[row][col]); //change the %5d to however much space you want
            }
            System.out.println(); //Makes a new row
        }
    }

    static class Edge {

        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}


