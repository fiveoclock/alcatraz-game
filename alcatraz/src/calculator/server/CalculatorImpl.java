package calculator.server;

import calculator.common.Calculator;
import calculator.common.CalculatorException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
  
  // Implementations must have an
  // explicit constructor
  // in order to declare the
  // RemoteException exception
  public CalculatorImpl() throws RemoteException {
    super();
  }
  
  public long add(long a, long b) throws RemoteException {
    return a + b;
  }
  
  public long sub(long a, long b) throws RemoteException {
    return a - b;
  }
  
  public long mul(long a, long b) throws RemoteException {
    return a * b;
  }
  
  public long div(long a, long b) throws CalculatorException, RemoteException {
    if (b == 0) throw new CalculatorException("Div by zero.");
    return a / b;
  }
}
