import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

abstract class ASML {
    void print(String fileName) {
        try {
            print(new PrintStream(fileName), 0);
        } catch (Exception e) {
            //System.err.println(e);
            print(System.out, 0);
        }
    }

    abstract void print(PrintStream out, int indent);

    void indent(PrintStream out, int indent) {
        for(int i=0; i<indent; i++) { out.print("\t"); }
    }

    abstract <E> E accept(ASML_ObjVisitor<E> v);

    abstract void accept(ASML_Visitor v);
}



// -------------------------- //
// -------------------------- //
// -- Function definitions -- //
// -------------------------- //
// -------------------------- //

abstract class ASML_Fundefs extends ASML {}

class ASML_FloatLabel extends ASML_Fundefs {
    String label;
    Float value;
    ASML_Fundefs in;

    ASML_FloatLabel(String l, Float v, ASML_Fundefs i) { label = l; value = v; in = i; }

    void print(PrintStream out, int indent) {
        out.println("let "+label+" = "+value+" in");
        out.println();
        in.print(out, indent);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fun extends ASML_Fundefs {
    String label;
    List<Id> args;
    ASML_Body body;
    ASML_Fundefs in;

    ASML_Fun(String l, List<Id> a, ASML_Body b, ASML_Fundefs i) { label = l; args = a; body = b; in = i; }

    void print(PrintStream out, int indent) {
        out.print("let "+label);
        Iterator<Id> it = args.iterator();
        while (it.hasNext()) {
            out.print(" "+it.next().toString());
        }
        out.println(" =");
        body.print(out, indent+1);
        out.println();
        in.print(out, indent);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Main extends ASML_Fundefs {
    ASML_Body body;

    ASML_Main(ASML_Body b) { body = b; }

    void print(PrintStream out, int indent) {
        out.println("let _ =");
        body.print(out, indent+1);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}



// --------------------- //
// --------------------- //
// -- Function bodies -- //
// --------------------- //
// --------------------- //

abstract class ASML_Body extends ASML {}

class ASML_Let extends ASML_Body {
    Id ident;
    ASML_Expr expr;
    ASML_Body in;

    ASML_Let(Id i, ASML_Expr e, ASML_Body ii) { ident = i; expr = e; in = ii; }

    void print(PrintStream out, int indent) {
        indent(out, indent);
        out.print("let "+ident.toString()+" = ");
        expr.print(out, indent);
        out.println(" in");
        in.print(out, indent);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_End extends ASML_Body {
    ASML_Expr expr;

    ASML_End(ASML_Expr a) { expr = a; }

    void print(PrintStream out, int indent) {
        indent(out, indent);
        expr.print(out, indent);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}



// ----------------- //
// ----------------- //
// -- Expressions -- //
// ----------------- //
// ----------------- //

abstract class ASML_Expr extends ASML {}

class ASML_Nop extends ASML_Expr {

    ASML_Nop() {}

    void print(PrintStream out, int indent) {
        out.print("nop");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Int extends ASML_Expr {
    int value;

    ASML_Int(int v) { value = v; }

    void print(PrintStream out, int indent) {
        out.print(value);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Var extends ASML_Expr {
    Id name;

    ASML_Var(Id i) { name = i; }

    void print(PrintStream out, int indent) {
        out.print(name.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_New extends ASML_Expr {
    Id i;

    ASML_New(Id id) { i = id; }

    void print(PrintStream out, int indent) {
        out.print("new "+i.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Ident extends ASML_Expr {
    Id i;

    ASML_Ident(Id id) { i = id; }

    void print(PrintStream out, int indent) {
        out.print(i.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Label extends ASML_Expr {
    String label;

    ASML_Label(String l) { label = l; }

    void print(PrintStream out, int indent) {
        out.print(label);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Neg extends ASML_Expr {
    ASML_Expr a;

    ASML_Neg(ASML_Expr aa) { a = aa; }

    void print(PrintStream out, int indent) {
        out.print("neg (");
        a.print(out, indent);
        out.print(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Add extends ASML_Expr {
    ASML_Expr a1, a2;

    ASML_Add(ASML_Expr aa1, ASML_Expr aa2) { a1 = aa1; a2 = aa2; }

    void print(PrintStream out, int indent) {
        out.print("add (");
        a1.print(out, indent);
        out.print(") (");
        a2.print(out, indent);
        out.print(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Sub extends ASML_Expr {
    ASML_Expr a1, a2;

    ASML_Sub(ASML_Expr aa1, ASML_Expr aa2) { a1 = aa1; a2 = aa2; }

    void print(PrintStream out, int indent) {
        out.print("sub (");
        a1.print(out, indent);
        out.print(") (");
        a2.print(out, indent);
        out.print(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fneg extends ASML_Expr {
    Id i;

    ASML_Fneg(Id id) { i = id; }

    void print(PrintStream out, int indent) {
        out.print("fneg "+i.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fadd extends ASML_Expr {
    Id i1, i2;

    ASML_Fadd(Id ii1, Id ii2) { i1 = ii1; i2 = ii2; }

    void print(PrintStream out, int indent) {
        out.print("fadd "+i1.toString()+i2.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fsub extends ASML_Expr {
    Id i1, i2;

    ASML_Fsub(Id ii1, Id ii2) { i1 = ii1; i2 = ii2; }

    void print(PrintStream out, int indent) {
        out.print("fsub "+i1.toString()+i2.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fmul extends ASML_Expr {
    Id i1, i2;

    ASML_Fmul(Id ii1, Id ii2) { i1 = ii1; i2 = ii2; }

    void print(PrintStream out, int indent) {
        out.print("fmul "+i1.toString()+i2.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Fdiv extends ASML_Expr {
    Id i1, i2;

    ASML_Fdiv(Id ii1, Id ii2) { i1 = ii1; i2 = ii2; }

    void print(PrintStream out, int indent) {
        out.print("fdiv "+i1.toString()+" "+i2.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Load extends ASML_Expr {
    Id adr;
    ASML_IdOrIm offset;

    ASML_Load(Id a, ASML_IdOrIm o) { adr = a; offset = o; }

    void print(PrintStream out, int indent) {
        out.print("mem("+adr.toString()+" + ");
        offset.print(out, indent);
        out.print(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Store extends ASML_Expr {
    Id adr, val;
    ASML_IdOrIm offset;

    ASML_Store(Id a, ASML_IdOrIm o, Id v) { adr = a; offset = o; val = v; }

    void print(PrintStream out, int indent) {
        out.print("mem("+adr.toString()+" + ");
        offset.print(out, indent);
        out.print(") <- "+val.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_IfEq extends ASML_Expr {
    ASML_Expr e1, e2;
    ASML_Body thn, els;

    ASML_IfEq(ASML_Expr ee1, ASML_Expr ee2, ASML_Body t, ASML_Body e) { e1 = ee1; e2 = ee2; thn = t; els = e; }

    void print(PrintStream out, int indent) {
        out.print("if (");
        e1.print(out, indent);
        out.print(" == ");
        e2.print(out, indent);
        out.println(") then (");
        thn.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(") else (");
        els.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_IfLE extends ASML_Expr {
    ASML_Expr e1, e2;
    ASML_Body thn, els;

    ASML_IfLE(ASML_Expr ee1, ASML_Expr ee2, ASML_Body t, ASML_Body e) { e1 = ee1; e2 = ee2; thn = t; els = e; }

    void print(PrintStream out, int indent) {
        out.print("if (");
        e1.print(out, indent);
        out.print(" <= ");
        e2.print(out, indent);
        out.println(") then (");
        thn.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(") else (");
        els.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_IfGE extends ASML_Expr {
    ASML_Expr e1, e2;
    ASML_Body thn, els;

    ASML_IfGE(ASML_Expr ee1, ASML_Expr ee2, ASML_Body t, ASML_Body e) { e1 = ee1; e2 = ee2; thn = t; els = e; }

    void print(PrintStream out, int indent) {
        out.print("if (");
        e1.print(out, indent);
        out.print(" >= ");
        e2.print(out, indent);
        out.println(") then (");
        thn.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(") else (");
        els.print(out, indent+1);
        out.println();
        indent(out, indent);
        out.println(")");
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_IfFEq extends ASML_Expr {
    Id i1, i2;
    ASML_Body thn, els;

    ASML_IfFEq(Id ii1, Id ii2, ASML_Body t, ASML_Body e) { i1 = ii1; i2 = ii2; thn = t; els = e; }

    void print(PrintStream out, int indent) {
        out.println("if "+i1.toString()+" =. "+i2.toString()+" then (");
        thn.print(out, indent+1);
        indent(out, indent);
        out.println(") else (");
        els.print(out, indent+1);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_IfFLE extends ASML_Expr {
    Id i1, i2;
    ASML_Body thn, els;

    ASML_IfFLE(Id ii1, Id ii2, ASML_Body t, ASML_Body e) { i1 = ii1; i2 = ii2; thn = t; els = e; }

    void print(PrintStream out, int indent) {
        out.println("if "+i1.toString()+" <=. "+i2.toString()+" then (");
        thn.print(out, indent+1);
        indent(out, indent);
        out.println(") else (");
        els.print(out, indent+1);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Call extends ASML_Expr {
    String label;
    List<ASML_Expr> args;

    ASML_Call(String l, List<ASML_Expr> a) { label = l; args = a; }

    void print(PrintStream out, int indent) {
        out.print("call "+label);
        Iterator<ASML_Expr> it = args.iterator();
        while (it.hasNext()) {
            it.next().print(out, indent);
            out.print(" ");
        }
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_CallCls extends ASML_Expr {
    Id closure;
    List<Id> args;

    ASML_CallCls(Id c, List<Id> a) { closure = c; args = a; }

    void print(PrintStream out, int indent) {
        out.print("call_closure "+closure.toString());
        Iterator<Id> it = args.iterator();
        while (it.hasNext()) {
            out.print(" "+it.next().toString());
        }
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}



// ----------------------------------- //
// ----------------------------------- //
// -- Identifier or immediate value -- //
// ----------------------------------- //
// ----------------------------------- //

abstract class ASML_IdOrIm extends ASML {}

class ASML_Id extends ASML_IdOrIm {
    Id i;

    ASML_Id(Id id) { i = id; }

    void print(PrintStream out, int indent) {
        out.print(i.toString());
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}

class ASML_Im extends ASML_IdOrIm {
    int i;

    ASML_Im(int i2) { i = i2; }

    void print(PrintStream out, int indent) {
        out.print(i);
    }

    <E> E accept(ASML_ObjVisitor<E> v) { return v.visit(this); }
    void accept(ASML_Visitor v) { v.visit(this); }
}