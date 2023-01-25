import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class AlphaVisitor implements Visitor {
	HashMap<String, String> scope;
	int nb;
	
	public AlphaVisitor() {
		
		scope = new HashMap();
	}
	
	public void visit(Unit e) {
		;
	}
	public void visit(Bool e) {
    	;
    }
	public void visit(Int e) {
    	;
    }
	public void visit(Float e) {
    	;
    }
	public void visit(Not e) {
    	e.e.accept(this);
    }
	public void visit(Neg e) {
		e.e.accept(this);
    }
	public void visit(Add e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(Sub e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(FNeg e) {
		e.e.accept(this);
    }
	public void visit(FAdd e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(FSub e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(FMul e) {
		e.e1.accept(this);
		e.e2.accept(this);;
    }
	public void visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);;
    }
	public void visit(Eq e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(LE e) {
		e.e1.accept(this);
		e.e2.accept(this);
    }
	public void visit(If e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
    }
	public void visit(Let e) { //ajouter id t et nouveau nom dans hashmap /!\ ATTENTION SCOPE A REMETTRE COMME AVANT A LA FIN
		HashMap<String, String> tmp = null;
		boolean var_exists = scope.containsKey(e.id.id);
		if (var_exists) {
			tmp = new HashMap(); // alloue que si une var existe deja pour restaurer l'etat
			tmp.putAll(scope);
		} else {
			scope.put(e.id.id, e.id.id);
		}
		e.e1.accept(this);
		String new_var = "alphaconv" + String.valueOf(nb++);
		scope.put(e.id.id, new_var);
		e.id.id = new_var;
		scope.put(new_var, "-1"); // pb : inclus les vars libres
		if (tmp != null) {
			tmp.put(new_var, new_var);
		}
		e.e2.accept(this);
		if (var_exists) {
			scope = tmp; //ne pas oublier putall cles des sous arbres
		}
    }
	public void visit(Var e) { //check si existe dans hashmap, sinon, pas toucher
    	if (scope.containsKey(e.id.id) && scope.get(e.id.id) != "-1") {
    		e.id.id = scope.get(e.id.id);
    	} else { // variable libre
    		System.err.println("Unbound value " + e.id.id);
    		System.exit(-1);
    	}
    }
	public void visit(LetRec e) {
    	;
    }
	public void visit(App e) {
    	Iterator<Exp> it = e.es.iterator();
		Exp ee;
		while (it.hasNext()) {
			ee = it.next();
			ee.accept(this);
		}
    }
	public void visit(Tuple e) {
    	;
    }
	public void visit(LetTuple e) {
    	;
    }
	public void visit(Array e) {
    	;
    }
	public void visit(Get e) {
    	;
    }
	public void visit(Put e) {
    	;
    }
	
}
