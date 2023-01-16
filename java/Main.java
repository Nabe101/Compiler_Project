import java_cup.runtime.*;
import java.io.*;
import java.util.*;

public class Main {
  static public void main(String argv[]) {    
    try {
      Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);

      /*/ Printing AST
      System.out.println("------ AST ------");
      expression.accept(new PrintVisitor());
      System.out.println(); // 

      // Printing AST height
      System.out.println("------ Height of the AST ----");
      int height = Height.computeHeight(expression);
      System.out.println("using Height.computeHeight: " + height);

      ObjVisitor<Integer> v = new HeightVisitor();
      height = expression.accept(v);
      System.out.println("using HeightVisitor: " + height); /*/

      // Printing register allocation
      RegAllocVisitor visit = new RegAllocVisitor();
      RegAllocation regAll = expression.accept(visit);
      regAll.print();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

