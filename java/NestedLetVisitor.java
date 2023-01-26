
public class NestedLetVisitor implements ObjVisitor<Exp> {
	public Exp visit(Unit e){
		return (Exp)e;
	}
    public Exp visit(Bool e){
    	return (Exp)e;
    }
    public Exp visit(Int e){
    	return (Exp)e;
    }
    public Exp visit(Float e){
    	return (Exp)e;
    }
    public Exp visit(Not e){
    	return new Not(e.e.accept(this));
    }
    public Exp visit(Neg e){
    	return new Neg(e.e.accept(this));
    }
    public Exp visit(Add e){
    	return new Add(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(Sub e){
    	return new Sub(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(FNeg e){
    	return new FNeg(e.e.accept(this));
    }
    public Exp visit(FAdd e){
    	return new FAdd(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(FSub e){
    	return new FSub(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(FMul e){
    	return new FMul(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(FDiv e){
    	return new FDiv(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(Eq e){
    	return new Eq(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(LE e){
    	return new LE(e.e1.accept(this), e.e2.accept(this));
    }
    public Exp visit(If e){
    	return new If(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }
    public Exp visit(Let e){
    	Exp a = e.e1.accept(this); //e1'
   	Exp b = e.e2.accept(this); //e2'
   	try {
    		Let y = (Let)a; //let ... in e
    		Let tmp = new Let(e.id, e.t, y.e2, b); //let x = e in e2' : récursion jusqu'à la fin
    		try {tmp = reduc(tmp);}
        	catch (ClassCastException except) {;}
        Let retour = new Let(y.id, y.t, y.e1, tmp); //let ... in tmp
    	return retour;
    	}
    	catch (ClassCastException exc) {
            e.accept(new PrintVisitor());
    		return e;
    	}
    }
    public Exp visit(Var e){
    	return new Var(e.id);
    }
    public Exp visit(LetRec e){
    	return new LetRec(e.fd, e.e);
    }
    public Exp visit(App e){
    	return new App(e.e, e.es);
    }
    public Exp visit(Tuple e){
    	return new Tuple(e.es);
    }
    public Exp visit(LetTuple e){
    	return new LetTuple(e.ids, e.ts, e.e1, e.e2);
    }
    public Exp visit(Array e){
    	return new Array(e.e1, e.e2);
    }
    public Exp visit(Get e){
    	return new Get(e.e1, e.e2);
    }
    public Exp visit(Put e){
    	return new Put(e.e1, e.e2, e.e3);
    }

    Let reduc(Let y) {
        y.accept(new PrintVisitor()); // 2 fois le 3 avec y = y2.e1 ?
        try {
            Let x1 = y;
            Let x = (Let)x1.e1;
            Exp z = x1.e2;
            Let tmp = new Let(x1.id, x1.t, x.e2, z); //let x = e in e2' : récursion jusqu'à la fin
            try {tmp = reduc(tmp);}
            catch (ClassCastException except) {;}
            Let retour = new Let(x.id, x.t, x.e1, tmp); //let ... in tmp
            return retour;
        }
        catch (ClassCastException exc) {
  		return y;
    	}
    }
}
