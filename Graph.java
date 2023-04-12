package minSpanningTree;

/*
 * Sava Jankovic 400292525
 * Graph.java component. 
 */

import java.util.Scanner;

public class Graph {
    public static int infinity = 10000; //logical infinity
    Vertex[] vArray; //array of vertices
    int size=0; //number of vertices

    //Constructor: construct the graph described by inputString;
    //the string contains only non-negative
    //integers separated by white spaces; the first integer is
    //the number of vertices; each of the following triple of integers
    //specifies an edge, namely end1, end2 and weight; you may assume
    //that the input string respects the format

    public Graph(String inputString) {
        try (Scanner input = new Scanner(inputString)) 
        {
            size = input.nextInt();
            vArray = new Vertex[size];

            /*Start of My Code */

            //fill the array of vertices; create the Vertex objects
            // Would create a for loop that would fill
            // up the array with the verticies. 
            for (int i=0; i<size;i++)  //O(n)
            {
                vArray[i] = new Vertex(i); // assigning each vertex in the array to a new one. 
            }

            //read the info from the string
            int end1;
            int end2; int w;

            while(input.hasNext()) 
            {
                //read next edge
                end1 = input.nextInt(); // each variable would read the verticies that are given. 
                end2 = input.nextInt(); 
                w = input.nextInt();  //weight of the non directed edges. 
                // start of my implementation. 
                // first, creation of the edge objects
                // abiding with the input parameters of the edge.java
                Edge edge1 = new Edge(w, vArray[end1].adjList, vArray[end2]); // end 1 and end 2 represented by the edge1
                Edge edge2 = new Edge(w, vArray[end2].adjList, vArray[end1]);  // (weight, next array, end)
                vArray[end1].adjList = edge1; //the adjacency list is getting filled up with the edge weight. 
                vArray[end2].adjList = edge2; // consistent appending of the edges to the adjacency lists.

                //if (end1=True){
               //     vArray[end1].adjList = edge1;
                //}
            }//end while
        }
    }// end constructor

    public String adjListString() 
    {
        Edge p; //edge pointer
        String s = " "; // empty string
        for(int i=0; i<size; i++) {
            p = vArray[i].adjList; //p points to first edge in adjList of vertex i
            while(p != null) {
                s += " \n edge: (v" + i +", v" + p.end.label + "), weight: "
                + p.weight;
                p = p.next; //move to next edge
            }//end while
        }//end for

        return s;
    } //end method

    //Start of My Code//

    //minST: find an MST using Prim’s algorithm, where the starting vertex
    //has label r; return a string that lists all edges in the MST,
    //in the order they were found; see the output of the test class
    //for clarification on the format of the string
    public String minST(int r) 
    {
        String output = ""; // initialize the output string
        MinBinHeap heap = new MinBinHeap(this, r); // create a heap with the given starting vertex
        Edge graphedge;
        Vertex vertex;
        int counter = 1; // initialize a counter for looping

        // loop until all vertices have been processed
        while (counter <= size) 
        {
            vertex = heap.extractMin(); // extract the vertex with the minimum key value from the heap
            graphedge = vertex.adjList; // get the first edge in the adjacency list of the current vertex

            // loop through all edges in the adjacency list
            while (graphedge != null) 
            {
                Vertex adjacent = graphedge.end; // get the adjacent vertex at the end of the current edge

                // if the adjacent vertex has a higher key value than the edge weight and is still in the heap
                if (adjacent.key > graphedge.weight && adjacent.isInQ) {
                    Vertex adjacentVertex = graphedge.end; // assign the adjacent vertex to a new variable
                    adjacentVertex.prev = vertex; // set the previous vertex of the adjacent vertex to the current vertex
                    int edgeWeight = graphedge.weight; // assign the edge weight to a new variable
                    heap.decreaseKey(adjacentVertex.heapIndex, edgeWeight); // decrease the key value of the adjacent vertex in the heap
                    adjacentVertex.key = edgeWeight; // update the key value of the adjacent vertex
                }

                graphedge = graphedge.next; // move on to the next edge in the adjacency list
            }

            // if the current vertex has a previous vertex, add the edge between them to the output string
            if (vertex.prev != null) 
            {
                output += String.format("(v%d, v%d), ", vertex.label, vertex.prev.label);
            }
            
            counter++; // increment the counter for the next loop iteration
        }
        
        System.out.print("MST:  "); // print out the MST label
        return output; // return the output string containing the edges of the MST
    }

    

    /*Start of my code */

    //return a two-dim array that represents the adjacency matrix of the graph
    
    public int[][] adjMatrix() {
        int[][] adjMatrix = new int[size][size];
        int i, j; // initialize the integers for for and while loops
    
        // iterate through matrix to place infinity in their respective positions
        if (size > 0)  
        {
            for (i = 0; i < size; i++)    // nested for loops (2D) iterating through the adj. matrix
            {
                for (j = 0; j < size; j++) 
                {
                    adjMatrix[i][j] = Integer.MAX_VALUE;     // start with all having max values. 
                }
            }
        }        
    
        // Loop over all vertices in the graph
        for (i = 0; i < size; i++) {
            // Get the first adjacent edge of the current vertex
            Edge current = vArray[i].adjList;

            // Loop over all adjacent edges of the current vertex
            while (current != null) {
                // Get the destination vertex index and edge weight
                j = current.end.label;
                int weight = current.weight;
                
                // Store the edge weight in the adjacency matrix
                adjMatrix[i][j] = weight;
                
                // Move on to the next adjacent edge of the current vertex
                current = current.next;
            }
        }

    
        // replace all instances of Integer.MAX_VALUE with infinity
        for (i = 0; i < size; i++) 
        {
                for (j = 0; j < size; j++) {
                    if (adjMatrix[i][j] == Integer.MAX_VALUE)  
                    {
                        adjMatrix[i][j] = infinity;
                    }
                }
            }
        
            return adjMatrix;
        }
    
    
}//end class
