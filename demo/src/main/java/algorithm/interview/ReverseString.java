package algorithm.interview;

/**
 * Author: Johnny
 * Date: 2018/5/13
 * Time: 0:32
 */
public class ReverseString {
    public static void main(String[] args) {
        System.out.println(reverse1("This is a silly interview question."));
    }

    private static String reverse(String input) {
        String words[] = input.split(" ");
        StringBuilder str = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            str.append(words[i]).append(" ");
        }
        return str.toString();
    }

    private static String reverse1(String input) {
        String words[] = input.substring(0, input.length() - 1).split(" ");
        StringBuilder str = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            str.append(" ").append(words[i]);
        }
        return str.append(".").toString().trim();
    }
}
