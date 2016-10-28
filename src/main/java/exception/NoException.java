package exception;

public class NoException {
	/*
	 * http://qing0991.blog.51cto.com/1640542/1387200
	 */
	public static void main(String[] args) {
        System.out.println("=============NoException==================");
        System.out.println(NoException());
        System.out.println("===============================");   
	}
	
	public static int NoException(){
        int i=10;
        try{
          System.out.println("i in try block is��"+i);
          return --i;
        }
        catch(Exception e){
          --i;
          System.out.println("i in catch - form try block is��"+i);
          return --i;
        }
        finally{
          System.out.println("i in finally - from try or catch block is��"+i);
          return --i;
        }  
	}

	public static int NoException1(){
        int i=10;
        try{
            System.out.println("i in try block is��"+i);
            return --i;
        }
        catch(Exception e){
            --i;
            System.out.println("i in catch - form try block is��"+i);
            return --i;
        }
        finally{           
            System.out.println("i in finally - from try or catch block is��"+i);
            --i;
            System.out.println("i in finally block is��"+i);
            //return --i;
        }
	}
	
	@SuppressWarnings("finally")
	public static int WithException(){
        int i=10;
        try{
            System.out.println("i in try block is��"+i);
            i = i/0;
            return --i;
        }
        catch(Exception e){
            System.out.println("i in catch - form try block is��"+i);
            --i;
            System.out.println("i in catch block is��"+i);
            return --i;
        }
        finally{           
            System.out.println("i in finally - from try or catch block is--"+i);
            --i;
            System.out.println("i in finally block is--"+i);
            return --i;
        }
	}
	
	
	public static int WithException1(){
        int i=10;
        try{
            System.out.println("i in try block is��"+i);
            i=i/0;
            return --i;
        }catch(Exception e){
            System.out.println("i in catch - form try block is��"+i);           
            return --i;
        }finally{
                                                                                                                                                                  
            System.out.println("i in finally - from try or catch block is��"+i);
            --i;
            System.out.println("i in finally block is��"+i);
            //return i;
        }
	}
	
	@SuppressWarnings("finally")
	public static int WithException2(){
        int i=10;
        try{
            System.out.println("i in try block is��"+i);
            i=i/0;
            return --i;
        }
        catch(Exception e){
            System.out.println("i in catch - form try block is��"+i);
            int j = i/0;
            return --i;
        }
        finally{                                                                                   
            System.out.println("i in finally - from try or catch block is��"+i);
            --i;
            --i;
            System.out.println("i in finally block is��"+i);
            return --i;
        }
	}
	
	
	public static int WithException3(){
        int i=10;
        try{
            System.out.println("i in try block is��"+i);
            i=i/0;
//            return --i;
        }
        catch(Exception e){
            System.out.println("i in catch - form try block is��"+i);
            //int j = i/0;
            //return --i;
        }
        finally{                                                                      
            System.out.println("i in finally - from try or catch block is��"+i);
            --i;
            --i;
            System.out.println("i in finally block is��"+i);
            //return --i;
        }
        return --i;
	}

}