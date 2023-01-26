abstract class Type {
    private static int x = 0;
    static Type gen() {
        return new TVar("?" + x++);
    }
    
}

class TUnit extends Type { 

    @Override
    public String toString() {
        return "Unit"; 
    }
}

class TBool extends Type { 
    @Override
    public String toString() {
        return "Bool"; 
    }
}

class TInt extends Type {
    @Override
    public String toString() {
        return "Int"; 
    }
 }

class TFloat extends Type { 
    @Override
    public String toString() {
        return "Float"; 
    }
}

class TFun extends Type { }



class TString extends Type{
    @Override
    public String toString() {
        return "String"; 
    }
}

class TTuple extends Type { }

class TArray extends Type { }

class TVar extends Type {
    String v;
    TVar(String v) {
        this.v = v;
    }
    @Override
    public String toString() {
        return v; 
    }
}

