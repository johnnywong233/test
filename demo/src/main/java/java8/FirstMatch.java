package java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by wajian on 2016/10/12.
 */
public class FirstMatch {
    //http://unmi.cc/java-8-return-the-first-match-element/
    public static void main(String[] args) {
        FirstMatch demo = new FirstMatch();
        System.out.println(demo.findFirstMatchJava7());
        System.out.println(demo.findFirstMatchJava8());
        System.out.println(findFirstMatch());
        System.out.println(findAllMatch());


        System.out.println(Stream.of(1, 4, 2, 5, 6, 3)
                .filter(i -> {
                    System.out.println("filter: " + i);
                    return i > 3;
                }).limit(1));
    }

    private Integer findFirstMatchJava7() {
        List<Integer> integers = Arrays.asList(1, 4, 2, 5, 6, 3);
        for (int i : integers) {
            if (i > 3) {
                return i;
            }
        }
        return null;
    }


    private Integer findFirstMatchJava8() {
        return Stream.of(1, 4, 2, 5, 6, 3)
                .filter(i -> i > 3)
                .findFirst()
                .orElse(null);
    }

    //to verify that the filter method will not go through all the stream
    private static Integer findFirstMatch() {
        return Stream.of(1, 4, 2, 5, 6, 3)
                .filter(i -> {
                    System.out.println("filter: " + i);
                    return i > 3;
                })
                .findFirst()
                .orElse(null);
    }


    //find all match use the filter method
    private static long findAllMatch() {
        return Stream.of(1, 4, 2, 5, 6, 3)
                .filter(i -> {
                    System.out.println("filter: " + i);
                    return i > 3;
                }).limit(2).count();
    }

}
