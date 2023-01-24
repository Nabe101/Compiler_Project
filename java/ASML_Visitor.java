public interface ASML_Visitor {
    // Function definitions
    void visit(ASML_FloatLabel a);
    void visit(ASML_Fun a);
    void visit(ASML_Main a);

    // Function bodies
    void visit(ASML_Let a);
    void visit(ASML_End a);

    // Expressions
    void visit(ASML_Nop a);
    void visit(ASML_Int a);
    void visit(ASML_Var a);
    void visit(ASML_New a);
    void visit(ASML_Ident a);
    void visit(ASML_Label a);
    void visit(ASML_Neg a);
    void visit(ASML_Fneg a);
    void visit(ASML_Fadd a);
    void visit(ASML_Fsub a);
    void visit(ASML_Fmul a);
    void visit(ASML_Fdiv a);
    void visit(ASML_Add a);
    void visit(ASML_Sub a);
    void visit(ASML_Load a);
    void visit(ASML_Store a);
    void visit(ASML_IfEq a);
    void visit(ASML_IfLE a);
    void visit(ASML_IfGE a);
    void visit(ASML_IfFEq a);
    void visit(ASML_IfFLE a);
    void visit(ASML_Call a);
    void visit(ASML_CallCls a);

    // Identifier or immediate value (used as the second part of some expressions)
    void visit(ASML_Id a);
    void visit(ASML_Im a);
    
}
