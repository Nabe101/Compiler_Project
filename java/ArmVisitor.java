
public class ArmVisitor implements Visitor{
	ArmGen arm = ArmGen.creer();
	@Override
	public void visit(Unit e) {}

	@Override
	public void visit(Bool e) {
		//true -> 0
		//false -> -1
		
	}

	@Override
	public void visit(Int e) {
				
	}

	@Override
	public void visit(Float e) {
 
		
	}

	@Override
	public void visit(Not e) {
		e.e.accept(this);
		//le resultat de e est dans r0
		arm.not();
		
	}

	@Override
	public void visit(Neg e) {
		e.e.accept(this);
		arm.neg();
	}

	@Override
	public void visit(Add e) {
		e.e1.accept(this);
		//peut changer le type de retour du visiteur
//		arm.add(0);
		e.e2.accept(this);
		
		
	}

	@Override
	public void visit(Sub e) {
 
		
	}

	@Override
	public void visit(FNeg e) {
 
		
	}

	@Override
	public void visit(FAdd e) {
		
		
	}

	@Override
	public void visit(FSub e) {
 
		
	}

	@Override
	public void visit(FMul e) {
 
		
	}

	@Override
	public void visit(FDiv e) {
 
		
	}

	@Override
	public void visit(Eq e) {
 
		
	}

	@Override
	public void visit(LE e) {
 
		
	}

	@Override
	public void visit(If e) {
 
		
	}

	@Override
	public void visit(Let e) {
		
	}

	@Override
	public void visit(Var e) {
		
		
	}

	@Override
	public void visit(LetRec e) {
 
		
	}

	@Override
	public void visit(App e) {
 
		
	}

	@Override
	public void visit(Tuple e) {
 
		
	}

	@Override
	public void visit(LetTuple e) {
 
		
	}

	@Override
	public void visit(Array e) {
 
		
	}

	@Override
	public void visit(Get e) {
 
		
	}

	@Override
	public void visit(Put e) {

		
	}

}
