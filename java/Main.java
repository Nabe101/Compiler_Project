import java_cup.runtime.*;
import java.io.*;
import java.util.*;

public class Main {
  static String input = null;
  static String output = "a.out";
  static int step = 4;

  static void argsParse(String argv[]) {
    int i = 0;
    while(i<argv.length) {
      switch(argv[i]) {
        case "-h":
          System.out.println("Options :");
          System.out.println("-ac : produit le code fourni après alpha-conversion");
          System.out.println("-nl : produit le code fourni après alpha-conversion et réduction des let imbriqués");
          System.out.println("-asml : produit un fichier au format asml (après les optimisations précédentes)");
          System.out.println("Pour produire directement du code assembleur ARM, ne préciser aucune des options ci-dessus.");
          System.out.println("-o <nom> : remplace le nom du fichier produit par <nom> (par défaut, il s'appellera a.out)");
          System.out.println("-h : affiche cette aide");
          System.exit(0);
        case "-ac":
          if (step==4 || step<1) { step = 1; }
          break;
        case "-nl":
          if (step==4 || step<2) { step = 2; }
          break;
        case "-asml":
          if (step==4 || step<3) { step = 3; }
          break;
        case "-o":
          i++;
          if (i>=argv.length) {
            System.err.println("Un argument est attendu après -o (nom du fichier en sortie).");
            System.exit(1);
          }
          output = argv[i];
          break;
        default:
          if(input==null) {
            input = argv[i];
          } else {
            System.err.println("Option non reconnue ("+argv[i]+")");
            System.err.println("Utiliser l'option -h pour voir les options possibles.");
            System.err.println("Note : si vous tentiez de compiler plusieurs fichiers, ce compilateur n'en accepte qu'un seul.");
          }
      }
      i++;
    }

    if(input==null) {
      System.err.println("Aucun fichier d'entrée renseigné.");
      System.exit(1);
    }
  }
  static public void main(String argv[]) {   
    argsParse(argv);

    try {
      Parser p = new Parser(new Lexer(new FileReader(input)));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);
      
      GenEquations equations = new GenEquations();
      equations.GenEquation(equations.env, expression, new TUnit());
      System.out.println(equations.env);
      //System.out.println(equations.SetEquations);
      equations.printSetEquations(equations.SetEquations);
      Equations testEq = new Equations(new TInt(), new TUnit());
      testEq.printType(testEq);
      
      // Alpha-conversion
      expression.accept(new AlphaVisitor());

      if(step==1) {
        expression.accept(new PrintVisitor(output));
        System.exit(0);
      }

      // Réduction des let imbriqués
      expression = expression.accept(new NestedLetVisitor());

      if(step==2) {
        expression.accept(new PrintVisitor(output));
        System.exit(0);
      }
      
      /*System.out.println("------ AST ------");
      expression.accept(new PrintVisitor());
      System.out.println();*/
      			
      // Conversion en arbre ASML
      ASML a = expression.accept(new AstToASML());

      if(step==3) {
        a.print(output);
        System.exit(0);
      }

      /*System.out.println("\n------ ASML ------");
      a.print();
      System.out.println();*/

      // Allocation de registres
      RegAllocation regAll = a.accept(new RegAllocVisitor());

      /*System.out.println("\n------ Register allocation ------");
      regAll.print();*/

      // Génération de code ARM
      ArmGen arm = new ArmGen(input, regAll);
      ArmVisitor visitArm = new ArmVisitor();
      a.accept(visitArm);
      //System.out.print("\nfin\n");
      arm.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

