package spark;

import static spark.Spark.*;

public class Demo {
	public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}