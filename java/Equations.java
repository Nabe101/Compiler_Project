public class Equations extends Type{

    Type t1;
    Type t2;

    public Equations(Type t1, Type t2){

        this.t1 = t1;
        this.t2 = t2;
    }

    public void printType (Equations eq){
        System.out.println(eq.t1.toString() + " = "+eq.t2.toString());
    }

    
    
}
