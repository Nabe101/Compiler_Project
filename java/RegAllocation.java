import java.util.Enumeration;
import java.util.Hashtable;


class RegAllocation {
    Hashtable<String,Hashtable<String,Storage>> memory;
    int offset;

    RegAllocation() {
        memory = new Hashtable<>(50);
        offset=0;
        newFunc("()");
        
    }

    // Call to specify the storage of variable varName in function funcName. For the main, funcName = "()" or null
    void put(String funcName, String varName) {
        if (funcName==null) { funcName = "()"; }
        Hashtable<String,Storage> h = memory.get(funcName);
        Storage str = new Storage(StorageType.Stack);
        h.put(varName, str);
    }

    void newFunc(String funcName) {
        memory.put(funcName, new Hashtable<>(50));
    }

    Storage get(String funcName, String varName) {
        if (funcName==null) { funcName = "()"; }
        Hashtable<String,Storage> h = memory.get(funcName);
        if (h!=null) {
            return h.get(varName);
        } else {
            return null;
        }
    }

    void print() {
        Hashtable<String,Storage> h;
        Enumeration<String> en1, en2;
        String funcName, varName;
        en1 = memory.keys();
        while (en1.hasMoreElements()) {
            funcName = en1.nextElement();
            System.out.println("---- Function "+funcName+" :");
            h = memory.get(funcName);
            en2 = h.keys();
            while(en2.hasMoreElements()) {
                varName = en2.nextElement();
                System.out.print(varName+" : ");
                h.get(varName).print();
            }
            System.out.println();
        }
    }
}