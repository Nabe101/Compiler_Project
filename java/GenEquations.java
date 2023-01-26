import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.*;

public class GenEquations {
    // Le set d'equations que la fonction GenEquation va retourner
    Set<Equations> SetEquations;
    // Le hashMap predef qui est l'environnement contenant
    // Les éléments de types <Var,Type>

    Map<String, Type> env;

    public void printSetEquations(Set<Equations> set){
        for (Equations equations : set) {
            System.out.println("["+ equations.t1.toString()+" = "+equations.t2.toString()+"]");
        }
    }

    public GenEquations() {
        env = new HashMap<String, Type>();
        env.put("print_string", new TString());
        env.put("print_int", new TInt());
        SetEquations = new HashSet<Equations>();

    }

    // predef.put("print_int",new TInt());

    public Set<Equations> GenEquation(Map env, Exp exp, Type type) {

        if (exp instanceof Unit) {
            Unit e = (Unit) exp;
            Equations eq = new Equations(new TUnit(), type);
            SetEquations.add(eq);
        }

        else if (exp instanceof Int) {
            Int e = (Int) exp;
            Equations eq = new Equations(new TInt(), type);
            SetEquations.add(eq);
         
        }

        else if (exp instanceof Float) {
            Float e = (Float) exp;
            Equations eq = new Equations(new TFloat(), type);
            SetEquations.add(eq);
            
        }

        else if (exp instanceof Bool) {
            Bool e = (Bool) exp;
            Equations eq = new Equations(new TBool(), type);
            SetEquations.add(eq);
        } else if (exp instanceof Not) {
            Not e = (Not) exp;
            Equations eq = new Equations(new TBool(), type);
            SetEquations.add(eq);
            GenEquation(env, e.e, new TBool());

        } else if (exp instanceof Add) {
            Add e = (Add) exp;
            Equations eq = new Equations(new TInt(), type);
            Equations eq2 = new Equations(new TInt(), type);
            SetEquations.add(eq);
            SetEquations.add(eq2);
            GenEquation(env, e.e1, new TInt());
            GenEquation(env, e.e2, new TInt());
        }

        else if (exp instanceof Let) {
            Let e = (Let) exp;
            env.put(e.id.id, e.t);
            System.out.print(e.t);
            Equations eq = new Equations(e.t, type);
            SetEquations.add(eq);
            GenEquation(env, e.e1, e.t);
            GenEquation(env, e.e2, type);

        }

        else if (exp instanceof Var) {
            Var e = (Var) exp;
            Boolean contains = env.containsKey(e.id.id);
            if (contains) {
                System.out.println(e.id.id + " Is present");
                // Créer une equation
            } else {
                System.out.println(e.id.id + " Is not present");
                // retourner une erreur
            }

        }

        else if (exp instanceof App) {
            App e = (App) exp;
            Equations eq = new Equations(new TVar(e.e.toString()), type);
            for (Exp e1 : e.es) {
                GenEquation(env, e1, type);

            }
        }

        else if (exp instanceof Sub) {
            Sub e = (Sub) exp;
        }

        else if (exp instanceof FNeg) {
            FNeg e = (FNeg) exp;
        }

        else if (exp instanceof FAdd) {
            FAdd e = (FAdd) exp;
        }

        else if (exp instanceof FSub) {
            FSub e = (FSub) exp;
        }

        else if (exp instanceof FMul) {
            FMul e = (FMul) exp;
        }

        else if (exp instanceof FDiv) {
            FDiv e = (FDiv) exp;
        }

        else if (exp instanceof Eq) {
            Eq e = (Eq) exp;
        }

        else if (exp instanceof LE) {
            LE e = (LE) exp;
        }

        else if (exp instanceof If) {
            If e = (If) exp;
        }

        else if (exp instanceof LetRec) {
            LetRec e = (LetRec) exp;
        }

        else if (exp instanceof Tuple) {
            Tuple e = (Tuple) exp;

            for (Exp e1 : e.es) {

            }

        }

        else if (exp instanceof LetTuple) {
            LetTuple e = (LetTuple) exp;
        }

        else if (exp instanceof Array) {
            Array e = (Array) exp;
        }

        else if (exp instanceof Get) {
            Get e = (Get) exp;
        }

        else if (exp instanceof Put) {
            Put e = (Put) exp;
        }

        else {
            // shouldn't happen
            assert (false);
        }

        return SetEquations;
    }

}
