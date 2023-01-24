import java.util.*;

class RegAllocVisitor implements ASML_ObjVisitor<RegAllocation> {
    RegAllocation wip;
    String funcName;
    int nextOffset;

    RegAllocVisitor() {
        wip = new RegAllocation();
        funcName = null;
    }
    // Function definitions
    public RegAllocation visit(ASML_FloatLabel a) { return a.in.accept(this); }
    public RegAllocation visit(ASML_Fun a) {
        String bakString = funcName;
        funcName = a.label;
        int bakInt = nextOffset;
        nextOffset = 4;

        wip.newFunc(funcName);
        Iterator<Id> it = a.args.iterator();
        Id iden;
        while (it.hasNext()) {
            iden = it.next();
            wip.put(funcName, iden.toString());
            nextOffset+=4;
        }
        a.body.accept(this);

        funcName = bakString;
        nextOffset = bakInt;
        return a.in.accept(this);
    }
    public RegAllocation visit(ASML_Main a) { return a.body.accept(this); }

    // Function bodies
    public RegAllocation visit(ASML_Let a) {
        wip.put(funcName, a.ident.toString());
        nextOffset+=4;
        return a.in.accept(this);
    }
    public RegAllocation visit(ASML_End a) { return wip; }

    // Expressions
    public RegAllocation visit(ASML_Nop a)     { return wip; }
    public RegAllocation visit(ASML_Int a)     { return wip; }
    public RegAllocation visit(ASML_Var a)     { return wip; }
    public RegAllocation visit(ASML_New a)     { return wip; }
    public RegAllocation visit(ASML_Ident a)   { return wip; }
    public RegAllocation visit(ASML_Label a)   { return wip; }
    public RegAllocation visit(ASML_Neg a)     { return wip; }
    public RegAllocation visit(ASML_Fneg a)    { return wip; }
    public RegAllocation visit(ASML_Fadd a)    { return wip; }
    public RegAllocation visit(ASML_Fsub a)    { return wip; }
    public RegAllocation visit(ASML_Fmul a)    { return wip; }
    public RegAllocation visit(ASML_Fdiv a)    { return wip; }
    public RegAllocation visit(ASML_Add a)     { return wip; }
    public RegAllocation visit(ASML_Sub a)     { return wip; }
    public RegAllocation visit(ASML_Load a)    { return wip; }
    public RegAllocation visit(ASML_Store a)   { return wip; }
    public RegAllocation visit(ASML_IfEq a)    { return wip; }
    public RegAllocation visit(ASML_IfLE a)    { return wip; }
    public RegAllocation visit(ASML_IfGE a)    { return wip; }
    public RegAllocation visit(ASML_IfFEq a)   { return wip; }
    public RegAllocation visit(ASML_IfFLE a)   { return wip; }
    public RegAllocation visit(ASML_Call a)    { return wip; }
    public RegAllocation visit(ASML_CallCls a) { return wip; }

    // Identifier or immediate value (used as the second part of some expressions)
    public RegAllocation visit(ASML_Id a) { return wip; }
    public RegAllocation visit(ASML_Im a) { return wip; }
}


