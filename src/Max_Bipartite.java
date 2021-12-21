


import java.util.*;
public class Max_Bipartite {

//Constructing the Applics and the hospitals arrays 

   static String[] ApplicsList = {"Ahmed", "Mahmoud", "Eman", "Fatimah", "Kamel", "Nojood"};
   static String[] HospitList = {"King Abdelaziz University", "King Fahd", "East Jeddah", "King Fahad Armed Forces", "King Faisal Specialist", "Ministry of National Guard"};
  static   ArrayList<Integer> Set = new ArrayList<>();

    static int M = ApplicsList.length, N = HospitList.length;
    
 
    static int[] Applics = new int[N];

    // a function for checking if a matching for the applicant is possible(return true if a match is found) 
    static boolean BipMatch(int m, boolean visit[], int Assigning[],int [][]BipGraph) {
        for (int v = 0; v < N; v++) {    //for all the hospital 0 to n-1
            if (BipGraph[m][v] == 1 && !visit[v]) {    //when the hospital v is not visited and u is interested
                visit[v] = true;    //mark hospital v as visited
                //when v is not assigned or previously assigned
                if (Assigning[v] < 0 || BipMatch(Assigning[v], visit, Assigning,BipGraph)) {
                    Assigning[v] = m;    //assign the hospital v to the applicant u
                    System.out.println(ApplicsList[m] + " is assigned to " + HospitList[v] + "\n");
                    Set.set(m, v); // add the edge to the matching set of M
                    Applics[v] = m;
                    return true;
                }
            }
        }
        return false;
    }

    //the maximum matching method(return the number of the matching found in the graph)
    static int MimumMatch( int [][]BipGraph) {
        int assigning[] = new int[N];    //an array to track which hospital is assigned to which applicant
        for (int i = 0; i < N; i++) {
            assigning[i] = -1;    //in the begining set all the jobs to available
            Set.add(-1);     //initialize the  set
        }
        int CountTheMatch = 0;

        for (int u = 0; u < M; u++) {    //for all the Applics
            boolean visit[] = new boolean[N];
            if (BipMatch(u, visit, assigning ,BipGraph)) //when u get a hospital
            {
                System.out.println("set : \n{");
                for (int i = 0; i < Set.size(); i++) {
                    if (Set.get(i) > -1) {
                        System.out.print("(" + ApplicsList[i] + " - " + HospitList[Set.get(i)] + ") \n");
                    }
                }
                System.out.println("}\n---------------------------------------------------------");
                CountTheMatch++;
            }

        }
        return CountTheMatch;
    }

    //the  main method
    public static void main(String[] args) {
        
        WeightedGraph BipGraph=new  WeightedGraph(6);
         BipGraph.add_Edge(1,1,1);
         BipGraph.add_Edge(1,2,1);
         BipGraph.add_Edge(2,6,1);
         BipGraph.add_Edge(3,1,1);
         BipGraph.add_Edge(3,4,1);
         BipGraph.add_Edge(4,3,1);
         BipGraph.add_Edge(5,4,1);
         BipGraph.add_Edge(5,5,1);
         BipGraph.add_Edge(6,6,1);
         //BipGraph.print();
         
       System.out.println("The maximum possible number of assignments of the hospitals to the applicants: " + MimumMatch(BipGraph.adjMat) + "\n");
    }
}
