import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Stack;

public class operations {
  boolean error;
  String operators[] = { "+", "-", "x", "/", "(", ")","^","y√x" };
  String trig[] = { "sin", "cos", "tan","sqrt","!","log","e","1/","asin","acos","atan","pi","2√x","3√x","Rand","MC","MR","M+","M-"};

  ArrayList<String> arithmetic = new ArrayList<String>();
  Double answer;
  ArrayList<String> integer = new ArrayList<String>();
  Stack<String> tempStack = new Stack<String>();

  private boolean check(String array[], String a) {
    boolean ans = false;
    for (int i = 0; i < array.length; i++) {
      if (a == array[i]) {
        ans = true;
      }

    }
    return ans;

  }

  int evaluate(String s) {
    if (s == "+") {
      return 2;
    } else if (s == "-") {
      return 1;
    } else if (s == "x") {
      return 3;
    } else if (s == "/") {
      return 4;
    } else if (s == ")" || s == "(") {
      return -1;
    } else if (s == "sin" || s == "cos" || s == "tan" ||s== "sqrt"||s=="^" ||s=="log"||s=="1/"||s=="!"||s=="e"
    ||s=="asin" || s=="acos" || s =="atan" || s=="y√x"||s=="3√x"||s=="2√x"||s=="Rand" ||s=="MC" ||s=="MR"
    ||s=="M+"||s=="M-") {
      return 5;
    } else {
      return 0;
    }

  }

  private double equal_to(String a, String c, String b) {
    Double ans = 0.0;
  try
 {   switch (a) {
      case "+":
        ans = Double.valueOf(c) + Double.valueOf(b);
        break;
      case "-":

        ans = Double.valueOf(c) - Double.valueOf(b);
        break;
      case "x":

        ans = Double.valueOf(c) * Double.valueOf(b);
        break;
      case "/":
        ans = Double.valueOf(c) / Double.valueOf(b);
        break;
      case "^":
        ans=Math.pow(Double.valueOf(b),Double.valueOf(c));
        break;
       case "y√x":
         ans = Math.pow(Double.valueOf(b), 1.0 / Double.valueOf(c));
         break;
      
    }

  }catch(Exception operation_Exception)
  {
    System.out.println("STNTAX ERROR");
  }

    return ans;
  }

  private double trig(String a, String b) {
    double ans = 0;
    try
{    switch (a) {
      case "sin":
        ans = Math.sin(Math.toRadians(Double.valueOf(b)));
        break;
      case "cos":
        ans = Math.cos(Math.toRadians(Double.valueOf(b)/57.2957795));
        break;
      case "tan":
        ans = Math.tan(Math.toRadians(Double.valueOf(b)/57.2957795));
        case "sqrt":
        ans=Math.sqrt(Double.valueOf(b));
        break;
        case "!":
          for(int i=Integer.valueOf(b);i>0;i--)
          {
            ans+=i;
          }
        break;
        case "log":
        ans=Math.log(Double.valueOf(b));
        break;
        case "1/":
        ans=1/Double.valueOf(b);
        break;
        case "e":
        ans= Math.exp(Double.valueOf(b));
        break;
        case "asin":
        ans = Math.asin(Math.toRadians((Double.valueOf(b))));
        break;
        case "acos":
        ans = Math.acos(Math.toRadians((Double.valueOf(b))));
        break;
        case "atan":
        ans=Math.atan(Math.toRadians((Double.valueOf(b))));
          break;
         case "2√x":
            // Calculate yth root of x using 1/y as the exponent
            ans = Math.pow(2, 1.0 / Double.valueOf(b));
            break;
          case "3√x":
            // Calculate yth root of x using 1/y as the exponent
            ans = Math.pow(3, 1.0 / Double.valueOf(b));
            break;
          case "Rand":
            SecureRandom random = new SecureRandom();
            // Generate a random double between 0.0 and 1.0 by default:
            double randomDouble = random.nextDouble();
            // To generate a random integer within a specific range:
            // int randomInt = random.nextInt(max) + min; // Customize min and max as needed

            // Handle the random value based on your UI design and functionality.
            // For example, display it in the text field, add it to the expression being evaluated, etc.
            break;
        case "MC": // Clear memory
         double memory = 0.0;
          break;
        case "MR": // Recall memory
          String c = null;
          if (memory != 0.0) {
            ans = memory;
            c = Double.toString(memory); // Update input for display
          } else {
            throw new IllegalArgumentException("Memory is empty");
          }
          break;
        case "M+": // Add to memory
          if (memory == 0.0) {
            memory = Double.parseDouble(c);
          } else {
            memory += Double.parseDouble(c);
          }
          break;
        case "M-": // Subtract from memory
          if (memory != 0.0) {
            memory -= Double.parseDouble(c);
          } else {
            throw new IllegalArgumentException("Memory is empty");
          }
          break;


}
  }
  catch(Exception trigException)
  {
    System.out.println("syntax error");
  }
    return ans;
  }

  public operations(ArrayList<String> array) {

    arithmetic = array;
    

    for (int i = 0; i < arithmetic.size(); i++) {
   
      
      if (!check(operators, arithmetic.get(i)) && !check(trig, arithmetic.get(i)) ) {
        integer.add(arithmetic.get(i));
        
      } else if (arithmetic.get(i) == "(") {
        tempStack.push(arithmetic.get(i));
      } else if (arithmetic.get(i) == ")") {
        while (!tempStack.isEmpty() && tempStack.peek() != "(") {
          integer.add(tempStack.pop());
        }
        tempStack.pop();
      } else {
        if (tempStack.empty() ) {
          tempStack.push(arithmetic.get(i));
        } else {
          while (!tempStack.isEmpty() && evaluate(tempStack.getLast()) > evaluate(arithmetic.get(i))) {
            String order = tempStack.pop();
            integer.add(order);
          }
          tempStack.push(arithmetic.get(i));

        }

      }

    }

    while (!tempStack.isEmpty()) {
      integer.add(tempStack.pop());
    }
    for (int i = 0; i < integer.size(); i++) {

      if (!check(operators, integer.get(i)) && !check(trig, integer.get(i))) {
        tempStack.push(integer.get(i));
      } else {
        if (check(trig, integer.get(i))) {
         
          String num1 = tempStack.pop();
          tempStack.push(Double.toString(trig(integer.get(i), num1)));
          tempStack.peek();
         
        } else {
          String num1 = tempStack.pop();
          String num2 = tempStack.pop();
          tempStack.push(Double.toString(equal_to(integer.get(i), num2, num1)));
        }
       
      

      }

    }

    if (tempStack.size() == 1) {
      answer = Double.valueOf(tempStack.pop());
    }
  }

 /*public static void main(String[] args) {
    ArrayList<String> test = new ArrayList<String>();
    test.add("pi");
    test.add("x");
    test.add("45");

    operations operations = new operations(test);
    System.out.println(operations.answer);

  }*/

}
