
public class AstToASML implements ObjVisitor<ASML> {
    AstToASML() {}
    public ASML visit(Let e) {
        Id name = e.id;
        ASML_Expr expr = (ASML_Expr) e.e1.accept(this);
        ASML end = e.e2.accept(this);
        try {
            return new ASML_Let(name, expr, (ASML_Body) end);
        } catch (Exception ex) {
            return new ASML_Let(name, expr, new ASML_End((ASML_Expr) end));
        }
    }
    public ASML visit(Int e) { return new ASML_Int(e.i); }
    public ASML visit(Var e) { return new ASML_Var(e.id); }
    public ASML visit(Neg e) { return new ASML_Neg((ASML_Expr) e.e.accept(this)); }
    public ASML visit(Add e) {
        return new ASML_Add((ASML_Expr) e.e1.accept(this), (ASML_Expr) e.e2.accept(this)); /*
        Var o1 = (Var) e.e1;
        Exp o2 = e.e2;
        if (o2 instanceof Var) {
            return new ASML_Add(o1.id, new ASML_Id(((Var) o2).id));
        } else if (o2 instanceof Int) {
            return new ASML_Add(o1.id, new ASML_Im(((Int) o2).i));
        } else {
            return null;
        }*/
    }
    public ASML visit(Sub e) {
        return new ASML_Sub((ASML_Expr) e.e1.accept(this), (ASML_Expr) e.e2.accept(this)); /*
        Var o1 = (Var) e.e1;
        Exp o2 = e.e2;
        if (o2 instanceof Var) {
            return new ASML_Sub(o1.id, new ASML_Id(((Var) o2).id));
        } else if (o2 instanceof Int) {
            return new ASML_Sub(o1.id, new ASML_Im(((Int) o2).i));
        } else {
            return null;
        }*/
    }
    

    // En Standby...
    public ASML visit(LetRec e) {
        System.err.println("Cette version ne prend pas en compte les fonctions."); return null; /*
        try {
            ASML_Body corps = (ASML_Body) e.fd.e.accept(this);
            ASML_Fundefs suite = (ASML_Fundefs) e.e.accept(this);
            return new ASML_Fun(e.fd.id.toString(), e.fd.args, corps, suite);
        } catch(Exception ex) {
            System.err.println(ex);
            System.exit(1);
            return null;
        } // */
    }

    public ASML visit(Tuple e) { System.err.println("Cette version ne prend pas en compte les tuples."); return null; }
    public ASML visit(LetTuple e) { System.err.println("Cette version ne prend pas en compte les tuples."); return null; }
    
    public ASML visit(Array e) { System.err.println("Cette version ne prend pas en compte les tableaux."); return null; }
    public ASML visit(Get e) { System.err.println("Cette version ne prend pas en compte les tableaux."); return null; }
    public ASML visit(Put e) { System.err.println("Cette version ne prend pas en compte les tableaux."); return null; }

    public ASML visit(If e) { System.err.println("Cette version ne prend pas en compte les conditions."); return null; }
    public ASML visit(App e) { System.err.println("Cette version ne prend pas en compte les fonctions."); return null; }

    public ASML visit(Unit e) { System.err.println("Oops."); return null; }
    public ASML visit(Bool e) { System.err.println("Oops."); return null; }
    public ASML visit(Float e) { System.err.println("Oops."); return null; }
    public ASML visit(Not e) { System.err.println("Oops."); return null; }
    public ASML visit(FNeg e) { System.err.println("Oops."); return null; }
    public ASML visit(FAdd e) { System.err.println("Oops."); return null; }
    public ASML visit(FSub e) { System.err.println("Oops."); return null; }
    public ASML visit(FMul e) { System.err.println("Oops."); return null; }
    public ASML visit(FDiv e) { System.err.println("Oops."); return null; }
    public ASML visit(Eq e) { System.err.println("Oops."); return null; }
    public ASML visit(LE e) { System.err.println("Oops."); return null; }
}


