package json.jsonpath;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import lombok.extern.slf4j.Slf4j;
import utils.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/6/29
 * Time: 20:29
 */
@Slf4j
public class TestJsonPath {

    //https://github.com/jayway/JsonPath
    public static void main(String[] args) throws IOException {
//        String jsonStr = FileUtil.readFile("jsonPath.json");
        String jsonStr = FileUtil.readFile("D:\\Java_ex\\test\\demo\\src\\test\\resources\\jsonPath.json");
        getJsonValue(jsonStr);
        getJsonValue0(jsonStr);
        getJsonValue1(jsonStr);
        getJsonValue2(jsonStr);
        getJsonValue3(jsonStr);
        getJsonValue4(jsonStr);
    }

    private static void getJsonValue(String json) {
        //The authors of all books：获取json中store下book下的所有author值
        List<String> authors1 = JsonPath.read(json, "$.store.book[*].author");

        //All authors：获取所有json中所有author的值
        List<String> authors2 = JsonPath.read(json, "$..author");

        //All things, both books and bicycles
        //获取json中store下的所有value值，不包含key，如key有两个，book和bicycle
        List<Object> authors3 = JsonPath.read(json, "$.store.*");

        //The price of everything：获取json中store下所有price的值
        List<Object> authors4 = JsonPath.read(json, "$.store..price");

        //The third book：获取json中book数组的第3个值
        List<Object> authors5 = JsonPath.read(json, "$..book[2]");

        //The first two books：获取json中book数组的第1和第2两个个值
        List<Object> authors6 = JsonPath.read(json, "$..book[0,1]");

        //All books from index 0 (inclusive) until index 2 (exclusive)：获取json中book数组的前两个区间值
        List<Object> authors7 = JsonPath.read(json, "$..book[:2]");

        //All books from index 1 (inclusive) until index 2 (exclusive)：获取json中book数组的第2个值
        List<Object> authors8 = JsonPath.read(json, "$..book[1:2]");

        //Last two books：获取json中book数组的最后两个值
        List<Object> authors9 = JsonPath.read(json, "$..book[-2:]");

        //Book number two from tail：获取json中book数组的第3个到最后一个的区间值
        List<Object> authors10 = JsonPath.read(json, "$..book[2:]");

        //All books with an ISBN number：获取json中book数组中包含isbn的所有值
        List<Object> authors11 = JsonPath.read(json, "$..book[?(@.isbn)]");

        //All books in store cheaper than 10：获取json中book数组中price<10的所有值
        List<Object> authors12 = JsonPath.read(json, "$.store.book[?(@.price < 10)]");

        //All books in store that are not "expensive"：获取json中book数组中price<=expensive的所有值
        List<Object> authors13 = JsonPath.read(json, "$..book[?(@.price <= $['expensive'])]");

        //All books matching regex (ignore case)：获取json中book数组中的作者以REES结尾的所有值（REES不区分大小写）
        List<Object> authors14 = JsonPath.read(json, "$..book[?(@.author =~ /.*REES/i)]");

        //Give me every thing：逐层列出json中的所有值，层级由外到内
        List<Object> authors15 = JsonPath.read(json, "$..*");

        //The number of books：获取json中book数组的长度
        int size = JsonPath.read(json, "$..book.length()");
        print("all authors: method 1");
        print(authors1);
        print("all authors: method 2");
        print(authors2);
        print("All things:");
        printOb(authors3);
        print("all prices:");
        printOb(authors4);
        print("the third book");
        printOb(authors5);
        print("The first two books");
        printOb(authors6);
        print("All books from index 0 (inclusive) until index 2 (exclusive)");
        printOb(authors7);
        print("All books from index 1 (inclusive) until index 2 (exclusive)");
        printOb(authors8);
        print("Last two books");
        printOb(authors9);
        print("Book number two from tail");
        printOb(authors10);
        print("All books with an ISBN number");
        printOb(authors11);
        print("All books in store cheaper than 10");
        printOb(authors12);
        print("All books in store that are not expensive");
        printOb(authors13);
        print("All books matching regex (ignore case)");
        printOb(authors14);
        print("all values in the json file");
        printOb(authors15);
        print("number of books:");
        print("" + size);

        //You can use && and || to combine multiple predicates [?(@.price < 10 && @.category == 'fiction')] , [?(@.category == 'reference' || @.price > 10)]
    }

    /**
     * 读取json的一种写法，得到匹配表达式的所有值
     */
    private static void getJsonValue0(String json) {
        List<String> authors = JsonPath.read(json, "$.store.book[*].author");
        print(authors);
    }

    private static void getJsonValue1(String json) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        String author1 = JsonPath.read(document, "$.store.book[1].author");
        print(author1);
    }

    @SuppressWarnings("unchecked")
	private static void getJsonValue2(String json) {
        ReadContext ctx = JsonPath.parse(json);

        List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");

        List<Map<String, Object>> expensiveBooks = JsonPath
                .using(Configuration.defaultConfiguration())
                .parse(json)
                .read("$.store.book[?(@.price > 10)]", List.class);
        print(authorsOfBooksWithISBN);
        print("****************Map****************");
        printListMap(expensiveBooks);
    }

    /**
     * 读取json的一种写法
     * 得到的值是一个String，所以不能用List存储
     */
    private static void getJsonValue3(String json) {
        //Will throw an java.lang.ClassCastException
        //List<String> list = JsonPath.parse(json).read("$.store.book[0].author");
        //由于会抛异常，暂时注释上面一行，要用的话，应使用下面的格式

        //Works fine
        String author = JsonPath.parse(json).read("$.store.book[0].author");
        print(author);
    }

    /**
     * 读取json的一种写法
     * 支持逻辑表达式，&&和||
     */
    private static void getJsonValue4(String json) {
        List<Map<String, Object>> books1 = JsonPath.parse(json)
                .read("$.store.book[?(@.price < 10 && @.category == 'fiction')]");
        List<Map<String, Object>> books2 = JsonPath.parse(json)
                .read("$.store.book[?(@.category == 'reference' || @.price > 10)]");
        print("****************books1****************");
        printListMap(books1);
        print("****************books2****************");
        printListMap(books2);
    }

    private static void print(List<String> list) {
        list.forEach(System.out::println);
    }

    private static void printOb(List<Object> list) {
        list.forEach(System.out::println);
    }

    private static void printListMap(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            map.entrySet().forEach(System.out::println);
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }
}
