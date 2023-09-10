import java.io.Console;

public class ConsoleTest {
  
  //NOTE: Will throw error - ChatGPT says that's b/c needs to be run in console environment
  // and this isn't ig... so System.console() returns null -> thus the NullPointerException
  //Hypothetically this would work if run in a console...?
  public static void main(String[] args) {
    Console c = System.console();
    System.out.print("Enter your name: ");
    String n = c.readLine(); //taking input
    System.out.println("Hello " + n);
  }
}