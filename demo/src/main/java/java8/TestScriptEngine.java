package java8;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Author: Johnny
 * Date: 2017/8/21
 * Time: 21:43
 */
@SuppressWarnings("restriction")
public class TestScriptEngine {

    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        Object result = engine.eval("var index=100;index+100;");
        System.out.println(result);

        ClassLoader classLoader = TestScriptEngine.class.getClassLoader();
        //load js file to get function or object
        ScriptObjectMirror fun = (ScriptObjectMirror) engine.eval(new FileReader(new File(classLoader.getResource("script/generator.js").getFile())));

        Object obj = engine.eval("var obj={username:'root'};obj;");
        // 调用函数，参数表示函数的执行对象 call apply
        System.out.println(fun.call(obj));

        Student stu = new Student();
        stu.setId(12);
        stu.setName("johnny");
        stu.setAge(28);
        // 设置作用域的变量
        engine.put("s", stu);

        // 加载一个json对象
        ScriptObjectMirror re = (ScriptObjectMirror) engine.eval(new FileReader(new File(classLoader.getResource("script/config.js").getFile())));

        //key in var object: username, pwd, sh
        ScriptObjectMirror scriptObjectMirror = ((ScriptObjectMirror) re.get("sh"));

        // 通过js对象调用函数
        scriptObjectMirror.call(re);

        // 修改某个java对象的属性值
        engine.eval("s.id=456");
        System.out.println(stu.getId());

        // 通过js创建java对象

        Student stu_re = (Student) engine.eval(new FileReader(new File(classLoader.getResource("script/newobject.js").getFile())));
        stu_re.sayHello();

        engine.put("x", 100);
        engine.put("y", 50);
        Object salary = engine.eval("((x+y)/2)+100-y;");
        System.out.println(salary);

        Stream<String> s = null;
        try {
             s = Files.lines(Paths.get("C:\\work\\test_git\\test\\demo\\src\\main\\resources\\script\\newobject.js"));
            s.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.close();
    }

}
