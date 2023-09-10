import org.junit.*;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Hashtable;

@SuppressWarnings("unchecked")
public class Project6Tester {
  
  //TESTS FOR Graph Class
  
  //test addNode 
  @Test
  public void testAddNode() {
    Graph graph = new Graph();
    //test for null name
    assertEquals(false, graph.addNode(null, null));
    //test for new vertex
    assertEquals(true, graph.addNode("NewVertex", 5));
    //test for duplicate vertex
    assertEquals(false, graph.addNode("NewVertex", 5));
  }
  
  //test addNodes 
  @Test
  public void testAddNodes() {
    Graph graph = new Graph();
    //test differring length arrays
    String[] names = {"George", "Fred", "Velma"};
    Integer[] data = {5, 2};
    boolean exceptionThrown = false;    //tracks whether exception thrown by test
    try{
      assertEquals(false, graph.addNodes(names, data));
    }
    catch (IllegalArgumentException e) {
      exceptionThrown = true;           //if exception thrown, set to true
    }
    assertEquals(true, exceptionThrown);
    //test arrays of unique vertices
    String[] names2 = {"George", "Fred", "Velma"};
    Integer[] data2 = {5, 2, 12};
    assertEquals(true, graph.addNodes(names2, data2));
    //test arrays with duplicate vertices
    String[] names3 = {"George", "Fred", "Velma", "George"};
    Integer[] data3 = {4, 6, 32, 1};
    assertEquals(false, graph.addNodes(names3, data3));
  }
  
  //test addEdge  
  @Test
  public void testAddEdge() {
    Graph graph = new Graph();
    String[] names = {"George", "Fred", "Velma"};
    Integer[] data = {5, 2, 12};
    graph.addNodes(names, data);
    //test for input from vertex not in graph
    assertEquals(false, graph.addEdge("Bob", "Fred"));
    //test for input to vertex not in graph
    assertEquals(false, graph.addEdge("George", "Dana"));
    //test for both vertices in graph
    assertEquals(true, graph.addEdge("Velma", "George"));
    //test for both inputs are same vertex
    assertEquals(true, graph.addEdge("Fred", "Fred"));
      //DEMO - printing graph in main method shows only one "Fred" edge
      //       added to "Fred" adjacency list
  }
  
  //test addEdges 
  @Test
  public void testAddEdges() {
    Graph graph = new Graph();
    String[] names = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data = {5, 2, 12, 54, 1};
    graph.addNodes(names, data);
    //test for input from vertex not in graph
    assertEquals(false, graph.addEdges("John", "Fred", "Velma"));
    //test for one length toList
    assertEquals(true, graph.addEdges("George", "Dana"));
    //test for many length toList
    assertEquals(true, graph.addEdges("Fred", "Velma", "Dana", "Bob"));
    //test for first vertex in toList not in graph
    assertEquals(false, graph.addEdges("Velma", "John", "George"));
      //DEMO - printing graph in main method shows edge added where possible,
      //       but not to nonexistent vertices
    //test for middle node in toList not in graph
    assertEquals(false, graph.addEdges("Velma", "George", "John", "Bob"));
      //DEMO - printing graph in main method shows edge added where possible,
      //       but not to nonexistent vertices
    //test for last node in toList not in graph
    assertEquals(false, graph.addEdges("Velma", "George", "Bob", "John")); 
      //DEMO - printing graph in main method shows edge added where possible,
      //       but not to nonexistent vertices
  }
  
  //test removeNode 
  @Test
  public void testRemoveNode() {
    Graph graph = new Graph();
    String[] names = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data = {5, 2, 12, 54, 1};
    graph.addNodes(names, data);
    //test for input not in graph
    assertEquals(false, graph.removeNode("Denny"));
    //test for input has no edges
    assertEquals(true, graph.removeNode("Fred"));
    //test for input has one edge
    graph.addEdge("George", "Velma");
    assertEquals(true, graph.removeNode("George"));
      //DEMO - printing graph in main method shows edge removed from all locations
    //test for input has multiple edges
    graph.addEdges("Bob", "Dana", "Velma");
    assertEquals(true, graph.removeNode("Bob"));
      //DEMO - printing graph in main method shows edge removed from all locations
  }
  
  //test removeNodes 
  @Test
  public void testRemoveNodes() {
    Graph graph = new Graph();
    String[] names = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data = {5, 2, 12, 54, 1};
    graph.addNodes(names, data);
    //test for input not in graph
    assertEquals(false, graph.removeNodes("Johnny"));
    //test for one input to remove
    assertEquals(true, graph.removeNodes("Velma"));
    //test for multiple inputs to remove
    assertEquals(true, graph.removeNodes("Bob", "George", "Dana"));
  }
  
  //test printGraph - SEE DEMO
    //print empty graph
      //See DEMO
    //print graph with no edges
      //See DEMO
    //print graph with edges
      //See DEMO
  
  //test read 
  @Test
  public void testRead() throws IOException{
    //read file with vertices and no edges
    Graph graph1 = Graph.read("./OnlyVerticesGraph.txt");
      //DEMO - see demo printout of graph
    //read file with vertices and edges
    Graph graph2 = Graph.read("./GraphTest.txt");
      //DEMO - see demo printout of graph
  }
  
  //test readWordGraph 
  @Test
  public void testReadWordGraph() throws IOException{
    //read file with vertices and no edges
    Graph graph1 = Graph.readWordGraph("./NoEdgeWordLadder.txt");
      //DEMO - see demo printout of graph
    //read file with 
    Graph graph2 = Graph.read("./WordLadderTest.txt");
      //DEMO - see demo printout of graph
  }
 
  //use multiple examples to show functionality of BFS and DFS
  
  //test BFS 
  @Test
  public void testBFS() throws IOException{
    Graph graph = Graph.read("./GraphTest.txt");
    //test from not in graph
    assertEquals(0, graph.BFS("E", "A").length);
    //test to not in graph
    assertEquals(0, graph.BFS("B", "E").length);
    //test from and to are same vertex
    assertEquals("A", graph.BFS("A", "A")[0]);
    assertEquals(1, graph.BFS("A", "A").length);
    //test from and to are not connected
    assertEquals(0, graph.BFS("Q", "A").length);
    //test from and to are direct neighbors
    assertEquals("A", graph.BFS("A", "B")[0]);
    assertEquals("B", graph.BFS("A", "B")[1]);
    assertEquals(2, graph.BFS("A", "B").length);
    //test from and to are not direct neighbors
    assertEquals("D", graph.BFS("D", "B")[0]);
    assertEquals("A", graph.BFS("D", "B")[1]);
    assertEquals("B", graph.BFS("D", "B")[2]);
    assertEquals(3, graph.BFS("D", "B").length);
  }
  
  //test DFS 
  @Test
  public void testDFS() throws IOException{
    Graph graph = Graph.read("./GraphTest.txt");
    //test from not in graph
    assertEquals(0, graph.DFS("E", "A").length);
    //test to not in graph
    assertEquals(0, graph.DFS("B", "E").length);
    //test from and to are same vertex
    assertEquals("A", graph.DFS("A", "A")[0]);
    assertEquals(1, graph.DFS("A", "A").length);
    //test from and to are not connected
    assertEquals(0, graph.DFS("Q", "A").length);
    //test from and to are direct neighbors
    assertEquals("A", graph.DFS("A", "B")[0]);
    assertEquals("D", graph.DFS("A", "D")[1]);
    assertEquals(2, graph.DFS("A", "D").length);
    //test from and to are not direct neighbors
    assertEquals("D", graph.DFS("D", "B")[0]);
    assertEquals("A", graph.DFS("D", "B")[1]);
    assertEquals("C", graph.DFS("D", "B")[2]);
    assertEquals("B", graph.DFS("D", "B")[3]);
    assertEquals(4, graph.DFS("D", "B").length);
  }
  
  //test toHashtable 
  @Test
  public void testToHashtable() throws IOException {
    Graph graph = Graph.readWordGraph("./WordLadderTest.txt");
    Graph badGraph = Graph.read("./GraphTest.txt");
    //test K and V incorrect types
    boolean exceptionThrown = false;    //checks if exception was thrown with wrong types
    try {
      badGraph.toHashtable();
    }
    catch (IllegalArgumentException e) {
      exceptionThrown = true;
    }
    assertEquals(true, exceptionThrown);
    //test K and V correct types
    Hashtable table = graph.toHashtable();
    assertEquals(0, table.get("A"));
    assertEquals(3, table.get("D"));
  }
  
  //test getDataAtIndex 
  @Test
  public void testGetDataAtIndex() throws IOException {
    Graph graph = Graph.readWordGraph("./WordLadderTest.txt");
    //test first index
    assertEquals("A", graph.getDataAtIndex(0));
    //test middle index
    assertEquals("C", graph.getDataAtIndex(2));
    //test last index
    assertEquals("D", graph.getDataAtIndex(3));
  } 
}