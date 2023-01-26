
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
    	Exp a = e.e.accept(this);
    	Not b = new Not((Exp)a);
    	return (Exp)b;
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
    	e.e1.accept(this);
   	e.e2.accept(this);
   	try {
    		final Let x = (Let)e.e1; // risque cast exception
    		final Let tmp = new Let(e.id, e.t, x.e2, e.e2);
    		final Let retour = new Let(x.id, x.t, x.e1, tmp);
    		return retour;
    	}
    	catch (ClassCastException exc) {
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
}
