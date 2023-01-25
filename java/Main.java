import java_cup.runtime.*;
import java.io.*;
import java.util.*;

public class Main {
  static public void main(String argv[]) {    
    try {
      Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);
      expression.accept(new AlphaVisitor());
      expression = expression.accept(new NestedLetVisitor());
      
      System.out.println("------ AST ------");
      expression.accept(new PrintVisitor());
      System.out.println(); // 
      			
      ASML a = expression.accept(new AstToASML());
      a.print("out.asml");

      // Printing register allocation
      RegAllocVisitor visitReg = new RegAllocVisitor();
      RegAllocation regAll = a.accept(visitReg);
      regAll.print();
      ArmGen arm = new ArmGen("test", regAll);
      ArmVisitor visitArm = new ArmVisitor();
      a.accept(visitArm);
      System.out.print("\nfin\n");
      arm.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

