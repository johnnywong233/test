package grammar.exception;

public class NoException {
    /**
     * <a href="http://qing0991.blog.51cto.com/1640542/1387200">...</a>
     */
    public static void main(String[] args) {
        System.out.println("=============NoException==================");
        System.out.println(noException());
    }

    public static int noException() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            return --i;
        } catch (Exception e) {
            --i;
            System.out.println("i in catch - form try block is��" + i);
            return --i;
        } finally {
            System.out.println("i in finally - from try or catch block is��" + i);
            return --i;
        }
    }

    public static int noException1() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            return --i;
        } catch (Exception e) {
            --i;
            System.out.println("i in catch - form try block is��" + i);
            return --i;
        } finally {
            System.out.println("i in finally - from try or catch block is��" + i);
            --i;
            System.out.println("i in finally block is��" + i);
            //return --i;
        }
    }

    @SuppressWarnings("finally")
    public static int withException() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            i = i / 0;
            return --i;
        } catch (Exception e) {
            System.out.println("i in catch - form try block is��" + i);
            --i;
            System.out.println("i in catch block is��" + i);
            return --i;
        } finally {
            System.out.println("i in finally - from try or catch block is--" + i);
            --i;
            System.out.println("i in finally block is--" + i);
            return --i;
        }
    }


    public static int withException1() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            i = i / 0;
            return --i;
        } catch (Exception e) {
            System.out.println("i in catch - form try block is��" + i);
            return --i;
        } finally {

            System.out.println("i in finally - from try or catch block is��" + i);
            --i;
            System.out.println("i in finally block is��" + i);
            //return i;
        }
    }

    @SuppressWarnings("finally")
    public static int withException2() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            i = i / 0;
            return --i;
        } catch (Exception e) {
            System.out.println("i in catch - form try block is��" + i);
            int j = i / 0;
            return --i;
        } finally {
            System.out.println("i in finally - from try or catch block is��" + i);
            --i;
            --i;
            System.out.println("i in finally block is��" + i);
            return --i;
        }
    }


    public static int withException3() {
        int i = 10;
        try {
            System.out.println("i in try block is��" + i);
            i = i / 0;
//            return --i;
        } catch (Exception e) {
            System.out.println("i in catch - form try block is��" + i);
            //int j = i/0;
            //return --i;
        } finally {
            System.out.println("i in finally - from try or catch block is��" + i);
            --i;
            --i;
            System.out.println("i in finally block is��" + i);
            //return --i;
        }
        return --i;
    }

}