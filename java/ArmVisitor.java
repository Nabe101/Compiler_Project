
public class ArmVisitor implements ASML_Visitor{
	static ArmGen arm = ArmGen.creer();

	@Override
	public void visit(ASML_FloatLabel a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fun a) {
		
	}

	@Override
	public void visit(ASML_Main a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Let a) {
		a.expr.accept(this); //res dans r1
		arm.setVar(a.ident.id, "1");
		a.in.accept(this);
	}

	@Override
	public void visit(ASML_End a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Nop a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Int a) {
		arm.Int(a.value);
	}

	@Override
	public void visit(ASML_New a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Ident a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Label a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Neg a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fneg a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fadd a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fsub a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fmul a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Fdiv a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Add a) {
		a.a1.accept(this);
		//le resultat est dans r1
		String var = arm.newVar();//on save r1
		a.a2.accept(this);
		//res dans r1
		arm.getVar(var, 2);//on met dans r2 notre valeur save
		arm.add();//on fait r1 + r2
		//le resultat de l'addition est dans r1
	}

	@Override
	public void visit(ASML_Sub a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Load a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Store a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_IfEq a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_IfLE a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_IfGE a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_IfFEq a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_IfFLE a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Call a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_CallCls a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Id a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Im a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ASML_Var a) {
		// TODO Auto-generated method stub
		
	}
	

}
