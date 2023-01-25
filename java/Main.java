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
      System.out.println("\n------ ASML ------");
      a.print(null);
      System.out.println();

      // Printing register allocation
      RegAllocVisitor visitReg = new RegAllocVisitor();
      RegAllocation regAll = a.accept(visitReg);
      /*System.out.println("\n------ Register allocation ------");
      regAll.print();*/
      ArmGen arm = new ArmGen(argv[0], regAll);
      ArmVisitor visitArm = new ArmVisitor();
      a.accept(visitArm);
      System.out.print("\nfin\n");
      arm.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

