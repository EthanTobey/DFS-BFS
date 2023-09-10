import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Hashtable;

@SuppressWarnings("unchecked")
public class Graph<K, V> {
  
  //adjacency list for graph
  private ArrayList<LinkedList<Vertex>> adjList;
  
  //constructor
  public Graph() {
    adjList = new ArrayList<LinkedList<Vertex>>();
  }
  
  //adds a node to the graph so long as it is not a duplicate
  public boolean addNode(K name, V data) {
    //make sure input name is valid (not null)
    if (name == null)
      return false;
    
    //check if trying to add duplicate
    for (LinkedList<Vertex> list : getAdjList()) {
      //if node is already somewhere in adjList
      if (list.getFirst().getName().equals(name))
        return false;
    }
    
    LinkedList<Vertex> newVertList = new LinkedList<Vertex>();
    newVertList.add(new Vertex(name, data));
    getAdjList().add(newVertList); 
    return true;
  }
  
  //adds a list of nodes to the graph and checks for duplicates
  public boolean addNodes(K[] names, V[] data) throws IllegalArgumentException {
    //if arguments differ in length, throw exception
    if (names.length != data.length)
      throw new IllegalArgumentException();
    
    boolean wasFalse = true;  //stores whether any of the inputs were not added
    
    //add all vertices
    for (int i = 0; i < names.length; i++) {
      if(!addNode(names[i], data[i]))   
        wasFalse = false;     //if any additions returned false, method returns false
    }
    return wasFalse;
  }
  
  //adds undirected edge from first input to second input nodes
  public boolean addEdge(K from, K to) {
    //indices to search for from and to
    int indexF = 0;
    int indexT = 0;
    //store vertices of from and to
    Vertex fromStore = null;
    Vertex toStore = null;
    //find linked list for from & vertex of from
    while (indexF < getAdjList().size() && 
           !getAdjList().get(indexF).getFirst().getName().equals(from)) {
      indexF++;
    }
    if (indexF < getAdjList().size())
      fromStore = getAdjList().get(indexF).getFirst();   //set fromStore to from's Vertex
    else
      return false;      //return false if from not in graph
    
    //find linked list for to & vertex of to
    while (indexT < getAdjList().size() && 
           !getAdjList().get(indexT).getFirst().getName().equals(to)) {
      indexT++;
    }
    if (indexT < getAdjList().size())
      toStore = getAdjList().get(indexT).getFirst();  //set toStore to to's Vertex
    else
      return false;    //return false if to not in graph
    
    //add each vertex to other's linked list
    getAdjList().get(indexF).add(toStore);
    if (!from.equals(to))
      getAdjList().get(indexT).add(fromStore); //don't add to second vertex list if to == from b/c making loop
    
    return true;
  }
  
  //adds undirected edge from first input to each node in toList
  public boolean addEdges(K from, K... toList) {
    boolean wasFalse = true;  //stores whether any edges were not added
    for (K to : toList) {
      if(!addEdge(from, to))
        wasFalse = false;      //if any additions returned false, method returns false
    }
    return wasFalse;
  }
  
  //removes given node and all its edges from the graph
  public boolean removeNode(K name) {
    // 1. Remove & store in temp adj linked list of node
    int thisIndex = 0;    //index of names position
    LinkedList<Vertex> removedList = null;    //LinkedList of input vertex
    //iterate through adjList to find name's list
    while (thisIndex < getAdjList().size() && 
           !getAdjList().get(thisIndex).getFirst().getName().equals(name)) {
      thisIndex++;
    }
    if (thisIndex < getAdjList().size()) {
      //remove & store linkedList at belonging to input vertex
      removedList = getAdjList().remove(thisIndex);
    }
    else
      return false;    //return false if name not in graph
    
    // 2. For each vertex in linkedList (except first)
    for (Vertex v : removedList) {
        //a. Find it's list in adjList array
      int searchIndex = 0;
      while (searchIndex < getAdjList().size() && 
             !getAdjList().get(searchIndex).getFirst().getName().equals(v.getName())) {
        searchIndex++;
      }
      
        //b. remove vertex of input name from its LinkedList
      if (searchIndex < getAdjList().size()) {
        getAdjList().get(searchIndex).remove(removedList.getFirst());
      }
    }
    return true;
  }
  
  //removes each node in nodelist and their edges from the graph
  public boolean removeNodes(K... nodelist) {
    boolean wasFalse = true;  //stores whether any edges were not added
    for (K node : nodelist) {
      if(!removeNode(node))
        wasFalse = false;      //if any additions returned false, method returns false
    }
    return wasFalse;
  }
  
  //prints the graph in adjacency list format
  public void printGraph() {
    StringBuilder builder = new StringBuilder();    //builds string to print
    for (LinkedList<Vertex> list : getAdjList()) {
      for (Vertex v : list) {
        builder.append(v.getName() + " ");
      }
      builder.append("\n");
    }
    if (builder.length() > 0)
      builder.deleteCharAt(builder.length() - 1);
    System.out.println(builder.toString());
  }
  
  //constructs a graph from a text file
  public static <V> Graph<String, V> read(String filename) throws IOException {
    Graph newGraph = new Graph();                                                //graph to return
    BufferedReader buffRead = new BufferedReader(new FileReader(filename));      //reads through file
    String readString = buffRead.readLine();                                     //current line in file
    //iterate through all lines of input file
    while (readString != null) {
      String[] splitWords = readString.split(" "); //splits readString into String[] of words separated by " "
      newGraph.addNode(splitWords[0], null);
      for (int i = 1; i < splitWords.length; i++) {
        newGraph.addEdge(splitWords[0], splitWords[i]);
      }
      readString = buffRead.readLine();
    }
    return newGraph;
  }
  
  //constructs a graph from a word graph formatted file
  public static Graph<Integer, String> readWordGraph(String filename) throws IOException {
    Graph newGraph = new Graph();                                                 //graph to return
    BufferedReader buffRead = new BufferedReader(new FileReader(filename));       //reads through file
    String readString = buffRead.readLine();                                      //current line in file
    //iterate through all lines of input file
    while (readString != null) {
      String[] splitWords = readString.split(" "); //splits readString into String[] of words separated by " "
      newGraph.addNode(Integer.parseInt(splitWords[0]), splitWords[1]);
      for (int i = 2; i < splitWords.length; i++) {
        newGraph.addEdge(Integer.parseInt(splitWords[0]), Integer.parseInt(splitWords[i]));
      }
      readString = buffRead.readLine();
    }
    return newGraph;
  }
  
  //returns a hashtable containing all nodes in the graph
  public Hashtable<String, Integer> toHashtable() throws IllegalArgumentException {
    Hashtable<String, Integer> table = new Hashtable<String, Integer>();
    //for each vertex in the graphg.print
    for (LinkedList<Vertex> list : getAdjList()) {
      try {
        if (list.getFirst().getData() instanceof String && list.getFirst().getName() instanceof Integer) {
          String data = (String)list.getFirst().getData();
          Integer name = (Integer)list.getFirst().getName();
          table.put(data, name);
        }
        //throw exception if V and K not correct types
        else
          throw new IllegalArgumentException(); 
      }
      catch (NullPointerException e) {
        //throw exception if data or name of anything is null
        throw new IllegalArgumentException();
      }
    }
    return table;
  }
  
  //returns path from first input node to second input node using DFS
  public K[] DFS(K from, K to) {
    //1. Create ArrayList visited and stack (linkedList implement)
    ArrayList<K> visited = new ArrayList<K>();
    LinkedList<Vertex> stack = new LinkedList<Vertex>();
    
    //check that to and from arent same
    if (from.equals(to)) {
      visited.add(from);
      return (K[])visited.toArray();
    }
    
    //2. Place from in stack
    int i = 0;
    //find from
    while (i < getAdjList().size() && 
           !getAdjList().get(i).getFirst().getName().equals(from)) {
      i++;
    }
    if (i < getAdjList().size())
      //push the vertex
      stack.push(getAdjList().get(i).getFirst());
    //if from is not a vertex in the graph
    else
      return (K[])Array.newInstance(from.getClass(), 0);  //makes generic array of length 0
    
    Vertex tempVertex = getAdjList().get(i).getFirst(); //initialize tempVertex to from
    //this way if from == to, loop won't run - & avoids nullptr
    //Repeat 3 & 4 until stack empty or popped vertex.equals(to)
    while (!stack.isEmpty() && !tempVertex.getName().equals(to)) {
      //3. Pop stack & add to visited
      tempVertex = stack.pop();    //temp store popped vertex from stack
      visited.add(tempVertex.getName());
      //4.Make list of popped vertex's adjacent nodes - add ones not in visited to top of stack
      ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
      //find tempVertex
      int j = 0;
      while (j < getAdjList().size() && 
             !getAdjList().get(j).getFirst().getName().equals(tempVertex.getName())) {
        j++;
      }
      for (Vertex vertex : getAdjList().get(j)) {
        //if vertex not visited or already in stack, push vertex
        if (!visited.contains(vertex.getName()) && !stack.contains(vertex))
          stack.push(vertex);
      }
    }
    //check if to was found
    if (visited.get(visited.size() - 1).equals(to))
      return (K[])visited.toArray();
    else
      return (K[])Array.newInstance(from.getClass(), 0); //return empty array if to was not findable
  }
  
  //returns path from first input node to second input node using BFS
  public K[] BFS(K from, K to) {   
    //1. Create ArrayList visited and queue (linkedList implement)
    ArrayList<K> visited = new ArrayList<K>();
    LinkedList<Vertex> queue = new LinkedList<Vertex>();
    
    //check that to and from arent same
    if (from.equals(to)) {
      visited.add(from);
      return (K[])visited.toArray();
    }
    
    //2. Place from in queue
    int i = 0;
    //find from
    while (i < getAdjList().size() && 
           !getAdjList().get(i).getFirst().getName().equals(from)) {
      i++;
    }
    if (i < getAdjList().size())
      //enqueue the vertex
      queue.addFirst(getAdjList().get(i).getFirst());
    //if from is not a vertex in the graph
    else
      return (K[])Array.newInstance(from.getClass(), 0);  //makes generic array of length 0
    
    Vertex tempVertex = getAdjList().get(i).getFirst(); //initialize tempVertex to from
    //Repeat 3 & 4 until queue empty or dequeued vertex.equals(to)
    while (!queue.isEmpty() && !tempVertex.getName().equals(to)) {
      //3. dequeue & add to visited
      tempVertex = queue.pollLast();    //temp store popped vertex from stack
      visited.add(tempVertex.getName());
      //4.Make list of dequeued vertex's adjacent nodes - enqueue ones not in visited or queue
      ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
      //find tempVertex
      int j = 0;
      while (j < getAdjList().size() && 
             !getAdjList().get(j).getFirst().getName().equals(tempVertex.getName())) {
        j++;
      }
      for (Vertex vertex : getAdjList().get(j)) {
        //if vertex not visited or already in queue, enqueue vertex
        if (!visited.contains(vertex.getName()) && !queue.contains(vertex))
          queue.addFirst(vertex);
      }
    }
    //check if to was found
    if (visited.get(visited.size() - 1).equals(to))
      return (K[])visited.toArray();
    else
      return (K[])Array.newInstance(from.getClass(), 0); //return empty array if to was not findable
  }
  
  //method to return data of vertex at index of adjList
  public V getDataAtIndex(int index) {
    return getAdjList().get(index).getFirst().getData();
  }
  
  //getter method for adjList
  private ArrayList<LinkedList<Vertex>> getAdjList() {
    return this.adjList;
  }
  
  private class Vertex {
    
    private K name;   //stores name of vertex
    private V data;   //stores extra information in vertex
    
    //constructor
    private Vertex(K name, V data) {
      this.name = name;
      this.data = data;
    }
    
    //returns name
    private K getName() {
      return this.name;
    }
    
    //returns info
    private V getData() {
      return this.data;
    }
    
    //sets name to input
    private void setName(K name) {
      this.name = name;
    }
    
    //sets info to input
    private void setData(V data) {
      this.data = data;
    }
  }
}