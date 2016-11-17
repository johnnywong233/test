package grammar.exception;

// self-defined exception
public class MyException  extends Exception  {
	private static final long serialVersionUID = 1L; 
	public void getException(String msg) throws MyException{
		System.out.println(msg); 
	} 
	
	public MyException() {
		super(); 
	} 
	
	public MyException(String message) {
		super(message); 
	} 

}
