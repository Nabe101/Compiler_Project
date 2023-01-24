public interface ASML_ObjVisitor<E> {
    // Function definitions
    E visit(ASML_FloatLabel a);
    E visit(ASML_Fun a);
    E visit(ASML_Main a);

    // Function bodies
    E visit(ASML_Let a);
    E visit(ASML_End a);

    // Expressions
    E visit(ASML_Nop a);
    E visit(ASML_Int a);
    E visit(ASML_Var a);
    E visit(ASML_New a);
    E visit(ASML_Ident a);
    E visit(ASML_Label a);
    E visit(ASML_Neg a);
    E visit(ASML_Fneg a);
    E visit(ASML_Fadd a);
    E visit(ASML_Fsub a);
    E visit(ASML_Fmul a);
    E visit(ASML_Fdiv a);
    E visit(ASML_Add a);
    E visit(ASML_Sub a);
    E visit(ASML_Load a);
    E visit(ASML_Store a);
    E visit(ASML_IfEq a);
    E visit(ASML_IfLE a);
    E visit(ASML_IfGE a);
    E visit(ASML_IfFEq a);
    E visit(ASML_IfFLE a);
    E visit(ASML_Call a);
    E visit(ASML_CallCls a);

    // Identifier or immediate value (used as the second part of some expressions)
    E visit(ASML_Id a);
    E visit(ASML_Im a);
    
}
