import java.util.*;
import java.io.PrintStream;

class PrintVisitor implements Visitor {
    PrintStream out;

    PrintVisitor() { out = System.out; }
    PrintVisitor(String fileName) {
        try {
            out = new PrintStream(fileName);
        } catch(Exception ex) {
            System.err.println("Impossible d'ouvrir le fichier "+fileName+" en écriture.");
            System.exit(1);
        }
    }

    public void visit(Unit e) {
        out.print("()");
    }

    public void visit(Bool e) {
        out.print(e.b);
    }

    public void visit(Int e) {
        out.print(e.i);
    }

    public void visit(Float e) {
        String s = String.format("%.2f", e.f);
        out.print(s);
    }

    public void visit(Not e) {
        out.print("(not ");
        e.e.accept(this);
        out.print(")");
    }

    public void visit(Neg e) {
        out.print("(- ");
        e.e.accept(this);
        out.print(")");
    }

    public void visit(Add e) {
        out.print("(");
        e.e1.accept(this);
        out.print(" + ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Sub e) {
        out.print("(");
        e.e1.accept(this);
        out.print(" - ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(FNeg e){
        out.print("(-. ");
        e.e.accept(this);
        out.print(")");
    }

    public void visit(FAdd e){
        out.print("(");
        e.e1.accept(this);
        out.print(" +. ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(FSub e){
        out.print("(");
        e.e1.accept(this);
        out.print(" -. ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(FMul e) {
        out.print("(");
        e.e1.accept(this);
        out.print(" *. ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(FDiv e){
        out.print("(");
        e.e1.accept(this);
        out.print(" /. ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Eq e){
        out.print("(");
        e.e1.accept(this);
        out.print(" = ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(LE e){
        out.print("(");
        e.e1.accept(this);
        out.print(" <= ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(If e){
        out.print("(if ");
        e.e1.accept(this);
        out.print(" then ");
        e.e2.accept(this);
        out.print(" else ");
        e.e3.accept(this);
        out.print(")");
    }

    public void visit(Let e) {
        out.print("(let ");
        out.print(e.id);
        out.print(" = ");
        e.e1.accept(this);
        out.print(" in ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Var e){
        out.print(e.id);
    }


    // print sequence of identifiers 
    <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<E> it = l.iterator();
        out.print(it.next());
        while (it.hasNext()) {
            out.print(op + it.next());
        }
    }

    // print sequence of Exp
    void printInfix2(List<Exp> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<Exp> it = l.iterator();
        it.next().accept(this);
        while (it.hasNext()) {
            out.print(op);
            it.next().accept(this);
        }
    }

    public void visit(LetRec e){
        out.print("(let rec " + e.fd.id + " ");
        printInfix(e.fd.args, " ");
        out.print(" = ");
        e.fd.e.accept(this);
        out.print(" in ");
        e.e.accept(this);
        out.print(")");
    }

    public void visit(App e){
        out.print("(");
        e.e.accept(this);
        out.print(" ");
        printInfix2(e.es, " ");
        out.print(")");
    }

    public void visit(Tuple e){
        out.print("(");
        printInfix2(e.es, ", ");
        out.print(")");
    }

    public void visit(LetTuple e){
        out.print("(let (");
        printInfix(e.ids, ", ");
        out.print(") = ");
        e.e1.accept(this);
        out.print(" in ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Array e){
        out.print("(Array.create ");
        e.e1.accept(this);
        out.print(" ");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Get e){
        e.e1.accept(this);
        out.print(".(");
        e.e2.accept(this);
        out.print(")");
    }

    public void visit(Put e){
        out.print("(");
        e.e1.accept(this);
        out.print(".(");
        e.e2.accept(this);
        out.print(") <- ");
        e.e3.accept(this);
        out.print(")");
    }
}


