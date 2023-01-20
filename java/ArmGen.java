import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class ArmGen {
	public ArmGen(String nom, RegAllocation reg) throws FileNotFoundException, UnsupportedEncodingException {
		this.nom = "../ARM/"+nom.substring(0, nom.length()) + ".s" ;
		this.reg=reg;
		this.stack= new Stack();
		this.stack.push("()");
		this.numLabel=0;
		this.txt = "	.text\n"
				+ "	.global _start\n"
				+ "_start:\n";
		this.label="";
		this.numVariable = 0;
		
		PrintWriter writer = new PrintWriter(this.nom, "UTF-8");
		this.arm = this;
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
	Stack stack;
	RegAllocation reg;
	int numLabel; //label counter
	int numVariable;
	String txt;
	String label;
	public static ArmGen arm;	
	
	void test_var() {
		this.newVar("10");//tmp0
		this.newVar("20");//tmp1
		this.setVar("tmp0", "5");
		this.newVar("30");//tmp2
		this.getVar("tmp0", 0);
		this.getVar("tmp1", 1);
		this.add();
		print();
		this.printSaut();

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
	
	//val est string pour + de généricité 
	public void setVar(String id, String  val) {
		Storage sto = this.reg.get(stack.peek().toString(), id);
		System.out.print("la valeur de " + id + "est " + sto.type + "\n");
		if(sto.type==StorageType.Register) {
			this.txt += "mov r" + Integer.toString(sto.value) + ", #" + val + "\n";
		}
		else {
			this.txt += "mov r0, #" + val;
			this.txt += "\nSTR r0, [sp,#"+ sto.value +"]\n";
		}
	}
	
	public String newVar(String val) {
		String varName = "tmp" + Integer.toString(this.numVariable);
		this.numVariable++;
		this.reg.put(this.stack.peek().toString(), varName);
		Storage str = this.reg.get(this.stack.peek().toString(), varName);
		System.out.print("la valeur de " + val + "est " + str.value + "\n");
		this.txt += "mov r0, #" + val
				 + "\nSTR r0, [sp,#"+ Integer.toString(str.value)+"]\n";
		return null;
	}
	
	
	public void neg() {
		this.txt += "neg r0, r0\n";
	}
	
	public void not() {
		this.txt += "mvn r0, r0\n";
	}
	
	public void add() {
		this.txt += "add r0, r0, r1";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
