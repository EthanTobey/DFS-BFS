import java.io.IOException;

public class Project6Demo {
  
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException{
    //DEMO testAddEdge() for inputs are same vertex
    Graph graph1 = new Graph();
    String[] names1 = {"George", "Fred", "Velma"};
    Integer[] data1 = {5, 2, 12};
    graph1.addNodes(names1, data1);
    graph1.addEdge("Fred", "Fred");
    System.out.println("***Test addEdge() for inputs are same vertex: Fred***");
    graph1.printGraph();
    
    //DEMOS testAddEdges()
    Graph graph2 = new Graph();
    String[] names2 = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data2 = {5, 2, 12, 54, 1};
    graph2.addNodes(names2, data2);
    //first vertex in toList not in graph
    graph2.addEdges("Velma", "John", "George");
    System.out.println("***Test addEdges() for first vertex in toList not in graph - only adds possible edges***");
    graph2.printGraph();
    //middle vertex in toList not in graph
    Graph graph3 = new Graph();
    String[] names3 = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data3 = {5, 2, 12, 54, 1};
    graph3.addNodes(names3, data3);
    graph3.addEdges("Velma", "George", "John", "Bob");
    System.out.println("***Test addEdges() for middle vertex in toList not in graph - only adds possible edges***");
    graph3.printGraph();
    //last vertex in toList not in graph
    Graph graph4 = new Graph();
    String[] names4 = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data4 = {5, 2, 12, 54, 1};
    graph4.addNodes(names4, data4);
    graph4.addEdges("Velma", "George", "Bob", "John");
    System.out.println("***Test addEdges() for last vertex in toList not in graph - only adds possible edges***");
    graph4.printGraph();
    
    //DEMOS testRemoveNode()
    Graph graph5 = new Graph();
    String[] names5 = {"George", "Fred", "Velma", "Bob", "Dana"};
    Integer[] data5 = {5, 2, 12, 54, 1};
    graph5.addNodes(names5, data5);
    //input has one edge
    graph5.addEdge("George", "Velma");
    System.out.println("***Testing removeNode() for input vertex has only one edge***");
    System.out.println("***Before***");
    graph5.printGraph();
    graph5.removeNode("George");
    System.out.println("***After***");
    graph5.printGraph();
    //input has multiple edges
    graph5.addEdges("Bob", "Dana", "Velma");
    System.out.println("***Testing removeNode() for input vertex has multiple edges***");
    System.out.println("***Before***");
    graph5.printGraph();
    graph5.removeNode("Bob");
    System.out.println("***After***");
    graph5.printGraph();
    
    //DEMOS testPrintGraph
    Graph graph6 = new Graph();
    System.out.println("***Testing printGraph() for no vertices***");
    graph6.printGraph();
    System.out.println("***Testing printGraph() for vertices but no edges***");
    String[] names6 = {"A", "B", "C", "D", "E"};
    Integer[] data6 = {5, 2, 12, 54, 1};
    graph6.addNodes(names6, data6);
    graph6.printGraph();
    System.out.println("***Testing printGraph() for vertices and edges***");
    graph6.addEdges("A", "C", "E");
    graph6.addEdges("B", "E", "D");
    graph6.addEdges("E", "C", "B", "E");
    graph6.printGraph();
    
    //DEMOS testRead
    Graph graph7 = Graph.read("./OnlyVerticesGraph.txt");
    System.out.println("***Testing read() for no edges only vertices***");
    graph7.printGraph();
    Graph graph8 = Graph.read("./GraphTest.txt");
    System.out.println("***Testing read() for edges and vertices***");
    graph8.printGraph();
    
    //DEMOS testReadWordGraph
    Graph graph9 = Graph.readWordGraph("./NoEdgeWordLadder.txt");
    System.out.println("***Testing readWordGraph() for no edges only vertices***");
    graph9.printGraph();
    Graph graph10 = Graph.read("./WordLadderTest.txt");
    System.out.println("***Testing readWordGraph() for edges and vertices***");
    graph10.printGraph();
  }
}