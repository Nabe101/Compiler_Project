import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class ArmGen {
	public ArmGen(String nom, RegAllocation reg) throws FileNotFoundException, UnsupportedEncodingException {
		this.nom = "../ARM/"+nom.substring(0, nom.length()) + ".s" ;
		this.reg=reg;
		this.stack= new Stack<String>();
		this.stack.push("()");
		this.numLabel=0;
		this.txt = "	.text\n"
				+ "	.global _start\n"
				+ "_start:\n";
		this.label="";
		this.numVariable = 0;
		
		PrintWriter writer = new PrintWriter(this.nom, "UTF-8");
		ArmGen.arm = this;
		this.test_var();
		writer.println(this.txt);
		writer.println("bl	_min_caml_exit");
		writer.println(this.label);		
		writer.close();
	}
	
	static ArmGen creer() {
		return arm;
	}
	
	String nom;
	Stack<String> stack;//pile d'appel
	RegAllocation reg;
	int numLabel; //label counter
	int numVariable;
	String txt;
	String label;//on met tout les label a la fin
	public static ArmGen arm;	
	
	void test_var() {

	}
	
	void print() {
		this.txt += "\nbl _min_caml_print_int\n";
	}
	void print(int i) {
		this.txt += "mov r0, #"+ Integer.toString(i)
				+ "\nbl _min_caml_print_int\n";
	}
	void print(char c) {
		this.txt += "mov r0, #'"
				+ c
				+ "'\nbl _min_caml_print_char\n";
	}
	void print(String s) {
		String la = "L" + Integer.toString(this.numLabel);
		this.label += la + ":\n"
				+ ".asciz	\""+s+"\"\n";
		this.txt +="	ldr	r0, ="+la+"\n"
				+ "	bl _min_caml_print_string\n";
	}
	void printSaut() {
		this.txt += "bl _min_caml_print_newline\n";
	}
	
	// r:on stocke le res dans r0
//	void add(int r) {
//		this.reg.p
//		this.txt += "ADD r"
//				+ Integer.toString(r)
//				+ ", r0, r1";
//	}
	
	void letRec(String name) {
		stack.push(name);
	}
	
	void letRecEnd() {
		stack.pop();
	}
	
	public void getVar(String id, int i) {
		Storage sto = this.reg.get(stack.peek().toString(), id);
		if(sto.type==StorageType.Register) {
			this.txt += "mov r" + Integer.toString(i) + ", r" + Integer.toString(sto.value) + "\n";
		}
		else {
			this.txt += "LDR r"+Integer.toString(i)+", [sp,#"+ Integer.toString(sto.value) +"]\n";
		}
	}
	
	public void setVar(String id, String  reg) {
		Storage sto = this.reg.get(stack.peek().toString(), id);
		this.txt += "STR r" + reg + ", [sp,#"+ sto.value +"]\n";
	}
	
	//creer une var avec r1. renvoi le nom de cette variable
	public String newVar() {
		String varName = "tmp" + Integer.toString(this.numVariable);
		this.numVariable++;
		this.reg.put(this.stack.peek().toString(), varName);
		Storage str = this.reg.get(this.stack.peek().toString(), varName);
		
		this.txt += "STR r1, [sp,#"+ Integer.toString(str.value)+"]\n";
		return varName;
	}
	
	public void Int(int i) {
		this.txt += "mov r1, #" + Integer.toString(i) + "\n";
	}
	public void neg() {
		this.txt += "neg r1, r1\n";
	}
	
	public void not() {
		this.txt += "mvn r1, r1\n";
	}
	
	public void add() {
		this.txt += "add r1, r1, r2\n";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
