package calculator.client;
import calculator.common.Calculator;
import calculator.common.CalculatorException;
import java.rmi.Naming;

public class CalculatorClient {
  
  public static void main(String[] args) {
    try {
      Calculator c = (Calculator)Naming.lookup("rmi://localhost/CalculatorService");
      System.out.print("4-3="); System.out.println(c.sub(4, 3));
      System.out.print("4+5="); System.out.println(c.add(4, 5));
      System.out.print("3*6="); System.out.println(c.mul(3, 6));
      System.out.print("9/3="); System.out.println(c.div(9, 3));
      System.out.print("5/0="); System.out.println(c.div(5, 0));
    } catch (CalculatorException ce) {
      System.err.println("Calculator threw Exception: "+ce.getMessage());
    } catch (Exception e) {
      System.err.println("Something did not work, see stack trace.");
      e.printStackTrace();
    }
  }
}

