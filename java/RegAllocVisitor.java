import java.util.*;

class RegAllocVisitor implements ObjVisitor<RegAllocation> {
    RegAllocation wip;
    String funcName;
    int nextOffset;

    RegAllocVisitor() {
        wip = new RegAllocation();
        funcName = null;
        nextOffset = 4;
    }

    public RegAllocation visit(Unit e) { return wip; }
    public RegAllocation visit(Bool e) { return wip; }
    public RegAllocation visit(Int e) { return wip; }
    public RegAllocation visit(Float e) { return wip; }
    public RegAllocation visit(Not e) { return wip; }
    public RegAllocation visit(Neg e) { return wip; }
    public RegAllocation visit(Add e) { return wip; }
    public RegAllocation visit(Sub e) { return wip; }
    public RegAllocation visit(FNeg e) { return wip; }
    public RegAllocation visit(FAdd e) { return wip; }
    public RegAllocation visit(FSub e) { return wip; }
    public RegAllocation visit(FMul e) { return wip; }
    public RegAllocation visit(FDiv e) { return wip; }
    public RegAllocation visit(Eq e) { return wip; }
    public RegAllocation visit(LE e) { return wip; }
    public RegAllocation visit(If e) { return wip; }
    public RegAllocation visit(Array e) { return wip; }
    public RegAllocation visit(Get e) { return wip; }
    public RegAllocation visit(Put e) { return wip; }
    public RegAllocation visit(Var e) { return wip; }
    public RegAllocation visit(App e) { return wip; }
    public RegAllocation visit(Tuple e) { return wip; }

    public RegAllocation visit(Let e) {
        wip.put(funcName, e.id.toString(), new Storage(StorageType.Stack, nextOffset));
        nextOffset+=4;
        return e.e2.accept(this);
    }

    public RegAllocation visit(LetRec e){
        String bakString = funcName;
        funcName = e.fd.id.toString();
        int bakInt = nextOffset;
        nextOffset = 4;

        wip.newFunc(funcName);
        Iterator<Id> it = e.fd.args.iterator();
        Id iden;
        while (it.hasNext()) {
            iden = it.next();
            wip.put(funcName, iden.toString(), new Storage(StorageType.Stack, nextOffset));
            nextOffset+=4;
        }
        e.fd.e.accept(this);

        funcName = bakString;
        nextOffset = bakInt;
        return e.e.accept(this);
    }

    public RegAllocation visit(LetTuple e){
        Iterator<Id> it = e.ids.iterator();
        Id iden;
        while (it.hasNext()) {
            iden = it.next();
            wip.put(funcName, iden.toString(), new Storage(StorageType.Stack, nextOffset));
            nextOffset+=4;
        }
        return e.e2.accept(this);
    }
}


