package minSpanningTree;

/*
 * Sava Jankovic 400292525
 * 3SM4 
 * MinBinHeap.java component. 
 */


public class MinBinHeap {
    private Vertex[] heap;
    private int size = 0; 

    //Constructor: set the key of vertex with label r to 0 and place that vertex
    //at the root; set the keys of the remaining vertices
    //to logical infinity and copy them in the heap;
    //for each vertex initialize heapIndex appropriately



    // method for the switching of two nodes 
    private void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= heap.length || index2 < 0 || index2 >= heap.length) {
            throw new IllegalArgumentException("Invalid index");
        }
    
        // Swap the vertices at the specified indices in the heap array
        Vertex temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    
        // Update the heapIndex fields of the swapped vertices
        heap[index1].heapIndex = index1;
        heap[index2].heapIndex = index2;
    
    }
    
    /*Start of My Code */

    public MinBinHeap(Graph g, int r) {
        // initialize sizing for graph and root
        size = g.size; 
        heap = new Vertex[size + 1]; 
        int i = 0;
        //the code essentially iterates through all of the
        // verticies and compares them to the root within the graph
        // Implemented a while loop 
        // first line looks at the heap i +2, and adds i to the graph array
        // second creates the index for the heap
        // third gives it a key and makes it infinite
        // incremented i. 
        while (i < r) 

        {
            //heap[i+2] = Graph.vArray[i];
            heap[i + 2] = g.vArray[i];
            heap[i + 2].heapIndex = i + 2;
            heap[i + 2].key = Graph.infinity;
            i++;
        }
        
        // Whenever i size is the same as the root
        // the heap would be set as heap[1] 
        // same process applied as when i was smaller than r. 
        heap[1] = g.vArray[r]; 
        heap[1].heapIndex = 1;
        heap[1].key = 0;
        i = r + 1; 

        // while the vertex i is smaller than the size
        // the same is applied, just instead of 
        // i +2 or 1, i + 1 is used for the addition of the arry, index and the key. 
        while (i < size) {
            heap[i + 1] = g.vArray[i];
            heap[i + 1].heapIndex = i + 1; // applying the heap index
            heap[i + 1].key = Graph.infinity;
            i++;
        }
        // as required, the heapIndex was initialized appropriately.

        
    }//end constructor
    
    //public int readMin() throws EmptyHeapException{
    //    if (size==0) throw new 
    //}
    

    // taking the percolateDown function from lecture notes
    // however I am using the opposite
    

    private void percolateUp(int i){
        // same idea applied as percolateDown
        // however, instead of mutiplying by two i simply divide the parent by 2
        //int nodeparent = i/2;
        //Vertex key = heap[i];

        // Compute the index of the parent node
        int nodeparent = i / 2;

        // Continue the loop until the current node is the root of the heap
        while (nodeparent > 0) {

            // Check if the heap property is satisfied (i.e., whether the key of the parent node
            // is less than or equal to the key of the current node). If it is, exit the loop.
            if (heap[i].key >= heap[nodeparent].key) {
                break;
            }
            if ( nodeparent > 0) // ensuring that parent node is larger than 0. 
            {
                // Swap the current node with its parent, to restore the heap property
                swap(i, nodeparent);

                // Update the parent node's heapIndex to reflect its new position
                heap[nodeparent].heapIndex = nodeparent;

                // Set the current node to its new position, which is the parent node
                i = nodeparent;

                // Compute the index of the new parent node
                nodeparent = i / 2;
            }
            
        }
        
    }

    //extractMin: return the vertex with the smallest key and remove it from
    //the heap; note that every time a change is made in the heap,
    //the heapIndex of any vertex involved in the change has to be updated
    // This method removes the minimum value (i.e., the root) from the heap and returns it.
    
    Vertex extractMin() {
        // Store the minimum vertex in the `minimum` variable. This is the root of the heap, located at index 1.
        Vertex minimum = heap[1];
        
        // Replace the root with the last element in the heap (located at index `size`), and update its `heapIndex` to 1.
        heap[1] = heap[size];
        heap[1].heapIndex = 1;
        
        // Decrement the `size` of the heap by 1, since we've removed one element.
        size--;
        
        // Percolate down the root element to restore the heap property.
        int i = 1;
        while (i * 2 <= size) {
            int j = i * 2;
            if (j + 1 <= size && compareVertices(heap[j + 1], heap[j]) < 0) {
                j++;
            }
            if (compareVertices(heap[i], heap[j]) <= 0) {
                break;
            }
            swap(i, j);
            i = j;
        }
        
        // Mark the minimum vertex as no longer being in the queue.
        minimum.isInQ = false;
        
        // Return the `minimum` vertex.
        return minimum;
    }
    
    // Custom comparison function to compare two vertices by their `key` values
    private int compareVertices(Vertex v1, Vertex v2) {
        if (v1.key < v2.key) {
            return -1;
        } else if (v1.key > v2.key) {
            return 1;
        } else {
            return 0;
        }
    }
    
    //decreasesKey: decrease the key of Vertex at index i in the heap;
    //newKey is the new value of the key; newKey is smaller than the old key
    //note that the heap may need to be reorganized

    void decreaseKey( int i, int newKey)
    {
            heap[i].key = newKey;
            percolateUp(i); // using the percolation function 

    }
    


    public String toString() {
        String s = "\n The heap size is " + "\n The items' labels are: \n";
        for (int i=1; i<size+1; i++) {
            s += heap[i].label + " key: ";
            s += heap[i].key + "\n";
        }//end for
        return s;
    }//end method
}//end class
