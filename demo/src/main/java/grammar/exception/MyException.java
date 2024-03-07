package grammar.exception;

// self-defined exception
public class MyException  extends Exception  {
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
