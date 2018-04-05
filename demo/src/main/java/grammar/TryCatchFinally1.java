package grammar;

/**
 * Author: Johnny
 * Date: 2017/10/10
 * Time: 23:49
 * 考查try-catch-finally的一道题
 * 主函数调用子函数并得到结果的过程，好比主函数准备一个空罐子，当子函数要返回结果时，先把结果放在罐子里，然后再将程序逻辑返回到主函数。
 * 所谓返回，就是子函数说，我不运行了，你主函数继续运行吧，这没什么结果可言，结果是在说这话之前放进罐子里的。
 */

public class TryCatchFinally1 {
    public static void main(String[] args) {
        System.out.println(new TryCatchFinally1().test());
    }

    static int test() {
        int x = 1;
        try {
            return x;
        } finally {
            ++x;
        }
    }
}
