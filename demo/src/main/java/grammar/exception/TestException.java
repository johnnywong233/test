package grammar.exception;

public class TestException{
	/*
	 * http://weitao0912-163-com.iteye.com/blog/2072734
	 */
	public static void main(String args[]) throws MyException{
		TestException te = new TestException(); 
		System.out.println(te.getExc());
		} 

	public String getExc(){
		Integer a = 10;
		a += 15;
		try{
			MyException e = new MyException(); 
			e.getException("johnny"); 
			return a.toString();
			
//			System.exit(0); //this will stop the current VM.
//				return "Error";
		} 
		catch(MyException exc){
			System.out.println("illeagal exception message"); 
		} 
		finally{
            if (a > 20) {
                return a.toString();
            }

            System.out.println("with return in try block, I will be executed first");
		} 
		return "OK"; 
	}
}
