/** Starter code for SP8
 Author: Amal Mohan - axm179030
 Anki Chauhan - axc170043

 axm179030\DFS.java consists of the implementation of topological sort
 rbk\Graph.java should be available as a package for appropriate functioning of DFS.java

 */

package axm179030;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * DFS class implements depth first search algorithm to traverse through the graph
 */
public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    //finish list has the vertices traversed through DFS
    LinkedList<Vertex> finishList;
    //indicates if the graph has cycles
    boolean hasCycle;

    /**
     * DFSVertex is a helper class that helps in adding additional fields to the vertex to handle DFS
     */
    public static class DFSVertex implements Factory {
        int cno;
        // True of the vertex is seen in DFS visit.
        boolean seen;
        // Helps to identify any back edges on the Vertex, top>1
        Vertex parent;

        /**
         * constructor for DFS vetex initializes seen and parent for each vertex
         * @param u
         */
        public DFSVertex(Vertex u) {
            seen=false;
            parent=null;
            int cno=0;
        }

        /**
         * helper function to convert vertex to DFSVertex
         * @param u
         * @return
         */
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    /**
     * Constructor of DFS
     * @param g
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList=new LinkedList<>();
        hasCycle=false;
    }

    /**
     * Function to perform depth first search on a given graph
     * @param g
     * @return DFS objects containing list of vertices and indicator for hasCycle
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d=new DFS(g);
        //gets list of vertices
        Iterator<Vertex> vIter=g.iterator();
        d.depthFirstSearch(vIter);
        return d;
    }

    public void depthFirstSearch(Iterator<Vertex> vIter) {
        int componentNumber=0;
        while(vIter.hasNext()){
            Vertex u = vIter.next();
            //visit each vertex that is not seen earlier
            if (!this.seen(u)){
                DFSVisit(u,++componentNumber,this);
            }
        }
    }


        /**
         * inputs a vertex and find all vertices starting from it
         * @param u
         * @param d
         */
    public static void DFSVisit(Vertex u,int componentNumber,DFS d) {
        d.setSeen(u,true);
        d.setCno(u,componentNumber);
        for (Edge e : d.g.incident(u)){
            if(d.seen(e.otherEnd(u)) && !d.finishList.contains(e.otherEnd(u)))
                d.setHasCycle(true);
            if (!d.seen(e.otherEnd(u))){
                d.setParent(e.otherEnd(u),u);
                DFSVisit(e.otherEnd(u),componentNumber,d);
            }
        }
        d.finishList.addFirst(u);
    }

    public static DFS stronglyConnectedComponents(Graph g) {
        DFS d=depthFirstSearch(g);
//        g.printGraph(false);
        List<Vertex> fList=d.getFinishList();
//        printSCC(d);
        g.reverseGraph();
//        g.printGraph(false);
        DFS d1=new DFS(g);
        Iterator<Vertex> vertexListIterator=fList.listIterator();
        d1.depthFirstSearch(vertexListIterator);
        g.reverseGraph();
        return d1;
    }

    /**
     * finds topological ordering of a DAG
      * @return list of vertices in topological sorted order
     */
    public List<Vertex> topologicalOrder1() {
        if(!this.g.isDirected())
            return null;
        DFS d=depthFirstSearch(this.g);
        if(d.getHasCycle())
            return null;
        return d.finishList;
    }

    public List<Vertex> getFinishList(){
        return finishList;
    }

    /**
     * setter method for has cycle
     * @param hasCycle
     */
    public void setHasCycle(boolean hasCycle){
        this.hasCycle=hasCycle;
    }


    public void setCno(Vertex u,int compno){
       this.get(u).cno=compno;
    }

    /**
     * getter method for has cycle
     * @return
     */
    public boolean getHasCycle(){
        return this.hasCycle;
    }
    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    public boolean seen(Vertex u){
        return get(u).seen;
    }

    public void setSeen(Vertex u,boolean b){
        get(u).seen=b;
    }

    public void setParent(Vertex u,Vertex parent){
        get(u).parent=parent;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void printSCC(DFS d){
        int compno=0;
        System.out.println("Strongly Connected Components are");
        for(Vertex u:d.getFinishList()) {
            if (d.cno(u) != compno) {
                System.out.println();
                compno = d.cno(u);
            }
            System.out.print(u + " ");
        }
    }

    public static void main(String[] args) throws Exception {
        String DAG = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   1 5 7   6 7 1   7 1 0";
        String notDAG = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        String exampleDAG="11 17  5 4 1  5 7 1  5 8 1  4 9 1  4 1 1  9 11 1  1 11 1  11 4 1  7 8 1  8 2 1  2 7 1  2 3 1  11 3 1  3 10 1  10 6 1  6 3 1  11 6 1";

        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(
                exampleDAG);

        // Read graph from input
        Graph g = Graph.readGraph(in,true);
        g.printGraph(false);


        DFS d=stronglyConnectedComponents(g);
        printSCC(d);
    }
}
