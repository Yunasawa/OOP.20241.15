package CCMR.Models.Types;

public class Tuple<A, B> 
{ 
	public A A; 
	public B B; 
	
	public Tuple(A a, B b) { this.A = a; this.B = b; } 
	public Tuple() { this(null, null); }
}
