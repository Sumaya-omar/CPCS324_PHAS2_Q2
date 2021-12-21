/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;

/**
 *
 * @author reem
 */
public class MaxFlowEdmond {
     static  int Ver = 6;
      // main function
    public static void main(String[] args) throws java.lang.Exception {
        

      WeightedGraph graph = new WeightedGraph(Ver);
        graph.add_Edge(1, 2, 2);
        graph.add_Edge(1, 3, 7);
        graph.add_Edge(2, 4, 3);
        graph.add_Edge(2, 5, 4);
        graph.add_Edge(3, 4, 4);
        graph.add_Edge(3, 5, 2);
        graph.add_Edge(4, 6, 1);
        graph.add_Edge(5, 6, 5);

        MaxFlowEdmond m = new MaxFlowEdmond();
        m.Edmond(graph, 0, graph.numOfVertices - 1);
    }

   

    //BFS will be  the supporting method to find the max-flow
    //it return true if we reached sink in BFS starting from the source, or otherwise return false 
    public boolean BFS(int [][] matrix, int source, int destantion, int parent[]) {

        //Create a new visit array and mark all the vertices not visit
        boolean visit[] = new boolean[Ver];
        for (int i = 0; i < Ver; ++i) {
            visit[i] = false;
        }

        // Create a new queue, add in the queue a source vertex and mark the source vertex as visited
        LinkedList<Integer> q = new LinkedList<>();
        q.add(source);
        visit[source] = true;
        parent[source] = -1;

        // a normal BFS Loop
        while (!q.isEmpty()) {
            int m = q.poll();
            for (int v = 0; v < Ver; v++) {

                if (visit[v] == false && matrix[m][v] > 0) {

                    q.add(v);
                    parent[v] = m;
                    visit[v] = true;
                }
            }
        }

        //If we reached a sink in the BFS starting from the source, then return true, otherwise return false
        return (visit[destantion] == true);
    }

    // the maximum-flow algorithm using the BFS 
    //to find the maximum flow of the network, and the corresponding min-cut
    public void Edmond(WeightedGraph g, int S, int T) {
        System.out.println("\n------------------------------------------------");
        System.out.println("\t\t   The Maximum flow");
        System.out.println("------------------------------------------------\n");
        System.out.println("> The Augiminting path:");
        int m, v;

        //Create a residual graph and fill it with the given capacities in the
        //original graph as a residual capacities in the residual graph
        //the Residual graph where ResidualGraph[i][j] indicatesresidual 
        //capacity of the edge from i to j (if there is an edge. If ResidualGraph[i][j]
        //is 0 then there is not)
        WeightedGraph ResidualGraph = new WeightedGraph(Ver);
        for (m = 0; m < Ver; m++) {
            for (v = 0; v < Ver; v++) {
                ResidualGraph.adjMat[m][v] = g.adjMat[m][v];
            }
        }
        // This array is filled by the BFS and to store the path
        int par[] = new int[Ver];
        int maximum_flow = 0;  // in the begining There is no flow 

        //Augment the flow while there is a path from the source to the sink
        //************************************************
        while (BFS(ResidualGraph.adjMat, S, T, par)) {
            String Path = "";

            //Find the minimum residual capacity of the edges along the path 
            //filled by the BFS. Or we can say to find the maximum flow through 
            //the path is found.
            int TheFlowOfPath = Integer.MAX_VALUE;
            for (v = T; v != S; v = par[v]) {
                String direct = "←";
                m = par[v];
                TheFlowOfPath = Math.min(TheFlowOfPath, ResidualGraph.adjMat[m][v]);
                if (g.adjMat[m][v] != 0) {
                    direct = "→";
                }
                Path = direct + (v + 1) + Path;
            }
            Path = (v + 1) + Path;
            System.out.printf("%-17s %s %d \n", Path, "flow: ", TheFlowOfPath);

            //update the residual capacities of the edges and reverse the edges 
            //along the path
            for (v = T; v != S; v = par[v]) {
                m = par[v];
                ResidualGraph.adjMat[m][v] -= TheFlowOfPath;
                ResidualGraph.adjMat[v][m] += TheFlowOfPath;
            }
            // Add the path flow to the overall flow
            maximum_flow += TheFlowOfPath;
            System.out.println("Updated flow: " + maximum_flow + "\n");

        }

        // print the maximum-flow
        System.out.println("> The Maximum flow is " + maximum_flow);
        System.out.println("\n------------------------------------------------");
        System.out.println("\t\t   The Minimum cut");
        System.out.println("--------------------------------------------------");
        // print the minimum-cut edges
        System.out.println("\n> Edges included in the minimum-cut");
        boolean[] IsItVisited = new boolean[g.numOfVertices];
        DFS(ResidualGraph, S, IsItVisited);

        // Print out all the edges that are from a reachable vertex to 
        // a non-reachable vertex in the original graph
        int TotCut = 0;
        for (int i = 0; i < g.numOfVertices; i++) {
            for (int j = 0; j < g.numOfVertices; j++) {
                if (g.adjMat[i][j] > 0 && IsItVisited[i] && !IsItVisited[j]) {
                    System.out.print("\nEdge: " + (i + 1) + "-" + (j + 1));
                    System.out.println(" , capacity = " + g.adjMat[i][j]);
                    TotCut += g.adjMat[i][j];
                    System.out.println("Updated minimum-cut capicity: " + TotCut);
                }
            }
        }
        System.out.println("\n> The total minimum-cut capacity is " + TotCut + "\n");
    }

    //DFS supporting method to find the min-cut 
   public void DFS(WeightedGraph graph, int S, boolean[] Visit) {
        Visit[S] = true;
        for (int i = 0; i < graph.numOfVertices; i++) {
            if (graph.adjMat[S][i] > 0 && !Visit[i]) {
                DFS(graph, i, Visit);
            }
        }
    }

  
}
