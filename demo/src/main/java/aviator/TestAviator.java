package aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnny on 2018/4/2.
 * https://blog.csdn.net/keda8997110/article/details/50782848
 */
public class TestAviator {

    @Test
    public void test() {
        /*Aviator的数值类型仅支持Long和Double, 任何整数都将转换成Long,
         任何浮点数都将转换为Double, 包括用户传入的变量数值*/
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);

        String name = "johnny";
        String output = (String) AviatorEvaluator.exec(" 'hello ' + yourName ", name);
        System.out.println(output);

        System.out.println(AviatorEvaluator.execute("string.length('hello')"));

        /*通过string.substring('hello', 1, 2)获取字符串'e', 然后通过函数string.contains判断e是否在'test'中。可以看到, 函数可以嵌套调用*/
        System.out.println(AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))"));

        /*Aviator没有提供if else语句, 提供三元操作符?:用于条件判断，对结果类型没有要求。*/
        System.out.println(AviatorEvaluator.exec("a>0? 'yes':'no'", 1));

        /*big int和decimal的两条规则:
         以大写字母N为后缀的整数都被认为是big int,如1N。
         超过long范围的整数字面量都将自动转换为big int类型。
         以大写字母M为后缀的数字都被认为是decimal, 如1M, 100000.9999M等。
         当big int或者decimal和其他类型的数字做运算的时候,
         按照long < big int < decimal < double的规则做提升;
         Java 的java.math.BigDecimal通过java.math.MathContext支持特定精度的计算,
         任何涉及到金额的计算都应该使用decimal类型。默认 Aviator 的计算精度为MathContext.DECIMAL128,
         你可以自定义精度, 通过:
         AviatorEvaluator.setMathContext(MathContext.DECIMAL64);
         */
        System.out.println(AviatorEvaluator.exec("99999999999999999999999999999999 + 99999999999999999999999999999999"));

        /*任何对象都比nil大,除了nil本身。null自动以nil替代。*/
        System.out.println("------nil用法------\n" + AviatorEvaluator.execute("nil == nil"));
        System.out.println(AviatorEvaluator.execute(" 3> nil"));
        System.out.println(AviatorEvaluator.execute(" true!= nil"));
        System.out.println(AviatorEvaluator.execute(" ' '>nil "));
        System.out.println(AviatorEvaluator.execute(" a==nil "));

        /*需要将日期写字符串的形式,并且要求是形如 “yyyy-MM-dd HH:mm:ss:SS”的字符串,否则都将报错。*/
        Map<String, Object> env = new HashMap<>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env.put("date", date);
        env.put("dateStr", dateStr);
        Boolean out = (Boolean) AviatorEvaluator.execute("date==dateStr", env);
        System.out.println("------日期------\n" + out);
        System.out.println(AviatorEvaluator.execute("date > '2018-02-25 00:00:00:00' ", env));
        System.out.println(AviatorEvaluator.execute("date < '2022-12-25 00:00:00:00' ", env));
        System.out.println(AviatorEvaluator.execute("date==date ", env));
    }

    /**
     * Aviator 支持类 Ruby 和 Perl 风格的表达式匹配运算,通过=~操作符
     */
    @Test
    public void test1() {
        // 两种运行模式
        //默认 AviatorEvaluator 以执行速度优先:
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        //你可以修改为编译速度优先,这样不会做编译优化:
        //AviatorEvaluator.setOptimize(AviatorEvaluator.COMPILE);
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE);

        String email = "wangjian19900404@gmail.com";
        Map<String, Object> env = new HashMap<>();
        env.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
        System.out.println(username);
    }

    @Test
    public void test2() {
        //注册函数 addFunction
        AviatorEvaluator.addFunction(new AddFunction());
        //移除函数 removeFunction
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)"));
    }

    /**
     * 先编译表达式, 返回一个编译的结果, 然后传入不同的env来复用编译结果,提高性能,推荐的做法
     */
    @Test
    public void test4() {
        String expression = "a-(b-c)>100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        //AviatorEvaluator内部有一个全局的缓存池, 缓存编译结果:
        //Expression compiledExp = AviatorEvaluator.compile(expression, true);
        AviatorEvaluator.invalidateCache(expression);//缓存失效
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
    }

    /**
     * aviator 拥有强大的操作集合和数组的 seq 库。整个库风格类似函数式编程中的高阶函数。在 aviator 中
     * 数组以及java.util.Collection下的子类都称为seq,可以直接利用 seq 库进行遍历、过滤和聚合等操作。
     */
    @Test
    public void test5() {
        Map<String, Object> env = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(20);
        list.add(10);
        env.put("list", list);
        System.out.println("计数：" + AviatorEvaluator.execute("count(list)", env));
        System.out.println("求和：" + AviatorEvaluator.execute("reduce(list,+,0)", env));
        System.out.println("过滤大于9的集合：" + AviatorEvaluator.execute("filter(list,seq.gt(9))", env));
        System.out.println("判断是否包含某元素：" + AviatorEvaluator.execute("include(list,10)", env));
        System.out.println("排序：" + AviatorEvaluator.execute("sort(list)", env));
        System.out.println("打印：" + AviatorEvaluator.execute("map(list,println)", env));
    }

}

/**
 * 自定义函数, 注册到AviatorEvaluator
 */
class AddFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Number left = FunctionUtils.getNumberValue(arg1, env);
        Number right = FunctionUtils.getNumberValue(arg2, env);
        return new AviatorDouble(left.doubleValue() + right.doubleValue());
    }

    @Override
    public String getName() {
        return "add";
    }
}