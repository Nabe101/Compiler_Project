import java_cup.runtime.*;
import java.io.*;
import java.util.*;

public class Main {
  static public void main(String argv[]) {    
    try {
      /*/ Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);

      Printing AST
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

      Id x = new Id("x"); Id y = new Id("y"); Id z = new Id("z"); Id w = new Id("w"); 
      Exp e = new Let(x, new TInt(), new Int(10),                  // let x:int = 10 in
              new Let(y, new TInt(), new Int(5),                   // let y:int = 5 in
              new Let(z, new TInt(), new Add(new Var(x), new Var(y)), // let z:int = x+y
              new Let(w, new TInt(), new Int(8),                   // let w:int = 8 in
              new Sub(new Var(z), new Var(w))))));                    // z-w

      AstToASML converter = new AstToASML();
      ASML a = converter.convert(e);
      a.print("out.asml");

      // Printing register allocation
      RegAllocVisitor visit = new RegAllocVisitor();
      RegAllocation regAll = a.accept(visit);
      regAll.print();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

