import java.util.Scanner;

public class ScannerTest {
  
  public static void main(String[] args) {
    //constructs a new scanner that produces values scanned from specified input stream
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter your name:");
    String input = scanner.nextLine();      //takes input
    System.out.println("Hello" + input);
    scanner.close();                        //close scanner when done with it
  }
  
}