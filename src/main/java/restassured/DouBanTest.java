package restassured;

import java.util.Date;

import io.restassured.RestAssured;

import org.junit.Before;
//import org.testng.annotations.Test;

import org.junit.Test;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;//not use this, but the one below
import static org.hamcrest.CoreMatchers.*;

//java.lang.SecurityException: class "org.hamcrest.Matchers"'s signer information does not match signer information of other classes in the same package


/**
 * Created by wajian on 2016/10/9.
 */
public class DouBanTest {

    @Before
    public void setUP() {
        //set proxy
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "web-proxy.sgp.hp.com");
        System.setProperty("http.proxyPort", "8080");

        //assign URL and port
        RestAssured.baseURI = "http://api.douban.com/v2/book";
        RestAssured.port = 80;
    }

    @Test
    public void testGETBook() {
        get("/1220562").then().body("title", equalTo("满月之夜白鲸现"));
    }

    @Test
    public void testSearchBook() {
        given().param("q", "java8").when().get("/search").then().body("count", equalTo(2));
    }

    @Test
    public void testJSON() {
        get("/1220562").then()
                //取顶级属性“title”
                .body("title", equalTo("满月之夜白鲸现"))
                //取下一层属性
                .body("rating.max", equalTo(10))
                //调用数组方法
                .body("tags.size()", is(8))
                //取数组第一个对象的“name”属性
                .body("tags[0].name", equalTo("片山恭一"))
                //判断数组元素
                .body("author", hasItems("[日] 片山恭一"));
    }

    @Test
    public void auth() {
//        given().auth().basic(username, password).when().get("/secured").then().statusCode(200);
//        given().auth().oauth(...);
//        given().auth().oauth2();
    }
}
