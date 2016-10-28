package test;

public class FinalizeTest {
	/*
	 * http://www.cnblogs.com/fsjohnhuang/p/4269086.html
	 */
	 public static FinalizeTest SAVE_HOOK = null;
	 
	  public void isAlive(){
	     System.out.println("yes, i am still alive;");
	  }

	  @Override 
	  protected void finalize() throws Throwable{
	    super.finalize();
	    System.out.println("finalize method executed!");
	    FinalizeTest.SAVE_HOOK = this;
	  }

	  public static void main(String[] args) throws Throwable{
	      SAVE_HOOK = new FinalizeTest();
	      SAVE_HOOK = null;
	      System.gc();
	      Thread.sleep(2500);
	      if (SAVE_HOOK == null) 
	    	  SAVE_HOOK.isAlive();
	      else 
	    	  System.out.println("no, i am dead :(");
	  }
}
