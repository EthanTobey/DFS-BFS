import java.util.Scanner;
import java.util.Hashtable;
import java.io.IOException;

public class WordLadders {
  
  //main method - take String filename of word graph as input
  public static void main(String[] args) throws IOException{
    Graph<Integer, String> wordGraph = Graph.readWordGraph(args[0]); //make word graph from input filename
  //  wordGraph.printGraph();
    Hashtable<String, Integer> wordTable = wordGraph.toHashtable(); //Hashtable to hold words in graph
    Scanner scanner = new Scanner(System.in);                       //Scanner to take user input
    Object[] searchResult = null;                                   //Stores integer names of 
    boolean loop = true;
    
    while (loop == true) {
      //Ask for input words
      System.out.print("Input a starting word: ");
      Integer firstWordName = wordTable.get(scanner.nextLine());   //name of first word for search
      System.out.print("Input a word to go to: ");
      Integer lastWordName = wordTable.get(scanner.nextLine());   //name of word to search to
      
      //Ask whether to BFS or DFS
      System.out.print("Do BFS or DFS: ");
      String nextLine = scanner.nextLine();
      
      if (nextLine.equals("BFS")) {
        if (firstWordName != null && lastWordName != null)
          searchResult = wordGraph.BFS(firstWordName, lastWordName);
        //if words weren't in wordTable
        else {
          System.out.println("One or both words not in database");
          searchResult = new Object[0]; //initialize searchResult to avoid errors
        }
      }
      else if (nextLine.equals("DFS")) {
        if (firstWordName != null && lastWordName != null)
          searchResult = wordGraph.DFS(firstWordName, lastWordName);
        //if words weren't in wordTable
        else {
          System.out.println("One or both words not in database");
          searchResult = new Object[0]; //initialize searchResult to avoid erros
        }
      }
      else
        System.out.println("Invalid Input: \n" + nextLine);
      
      //print the word ladder
      for (Object name : searchResult) {         //typecast back to Integer
        if (name instanceof Integer) {
          Integer index = (Integer)name;
          System.out.println(wordGraph.getDataAtIndex(index));
        }
      }
    
      //Ask whether or not to continue
      System.out.println("Would you like continue? Answer Y or N ");
      String answer = scanner.nextLine();
      if (answer.equals("N"))
            loop = false;
    }
    scanner.close();
  }
}