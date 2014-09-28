package calculator.server;
import calculator.common.Calculator;
import java.rmi.Naming;

public class CalculatorServer {

  public static void main(String args[]) {
    try {
      System.out.println("Starting up calculator server...");
      Calculator c = new CalculatorImpl();
      Naming.rebind("rmi://localhost:1099/CalculatorService", c);
      System.out.println("Calculator server up and running.");
    } catch (Exception e) {
      System.out.println("Trouble!");
      e.printStackTrace();
    }
  }
}
