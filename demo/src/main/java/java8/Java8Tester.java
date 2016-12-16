package java8;

import com.google.common.collect.Lists;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by wajian on 2016/10/9.
 * test on java8
 */
public class Java8Tester {
    //https://www.tutorialspoint.com/java8/index.htm
    public static void main(String args[]) {
        List<String> names2 = new ArrayList<>();
        names2.add("stream");
        names2.add("lambda");
        names2.add("function interface");
        names2.add("method reference");
        names2.add("optional");

        //method reference
        names2.forEach(System.out::println);

        Java8Tester tester = new Java8Tester();
        System.out.println("Sort using Java 8 syntax: ");
        tester.sortUsingJava8(names2);
        System.out.println(names2);

        //with type declaration
        MathOperation addition = (int a, int b) -> a + b;
        //with out type declaration
        MathOperation subtraction = (a, b) -> a - b;
        //with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        //without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;
        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));
        //with parenthesis
        GreetingService greetService1 = message -> System.out.println("Hello");
        //without parenthesis
        GreetingService greetService2 = (message) -> System.out.println("Hello");

        greetService1.sayMessage("java8");
        greetService2.sayMessage("lambda");


        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // Predicate<Integer> predicate = n -> true
        // n is passed as parameter to test method of Predicate interface
        // test method will always return true no matter what value n has.
        System.out.println("Print all numbers:");
        //pass n as parameter 
        eval(list, n -> true);
        // Predicate<Integer> predicate1 = n -> n%2 == 0
        // n is passed as parameter to test method of Predicate interface
        // test method will return true if n%2 comes to be zero
        System.out.println("Print even numbers:");
        eval(list, n -> n % 2 == 0);
        // Predicate<Integer> predicate2 = n -> n > 3
        // n is passed as parameter to test method of Predicate interface
        // test method will return true if n is greater than 3.
        System.out.println("Print numbers greater than 3:");
        eval(list, n -> n > 3);

        List<String> names = new ArrayList<>();
        names.add("TaoBao");
        names.add("ZhiFuBao");
        List<String> lowercaseNames = names.stream().map(String::toLowerCase).collect(Collectors.toList());
        System.out.println(lowercaseNames);

        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:" + nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::println).skip(2).limit(4).sum());

        List<Integer> num1 = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        List<Integer> numWithoutNull = num1.stream().filter(num -> num != null).
                collect(ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);
        System.out.println(numWithoutNull);


        //java 8 date & time API
        tester.testLocalDateTime();
        tester.testZonedDateTime();
        tester.testChronoUnits();
        tester.testPeriod();
        tester.testDuration();
        tester.testAdjusters();
        tester.testBackwardCompatability();

        //java 8 Base64
        try {
            // Encode using basic encoder
            String base64encodedString = Base64.getEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
            System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);

            // Decode
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);

            System.out.println("Original String: " + new String(base64decodedBytes, "utf-8"));
            base64encodedString = Base64.getUrlEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
            System.out.println("Base64 Encoded String (URL) :" + base64encodedString);

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 10; ++i) {
                stringBuilder.append(UUID.randomUUID().toString());
            }
            byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
            String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
            System.out.println("Base64 Encoded String (MIME) :" + mimeEncodedString);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }


        //java8 Nashorn JavaScript
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine se = scriptEngineManager.getEngineByName("nashorn");//engine name

        String name = "fantasy";
        Integer result = null;
        try {
            se.eval("print('" + name + "')");
            result = (Integer) se.eval("10 + 2");
        } catch (ScriptException e) {
            System.out.println("Error executing script: " + e.getMessage());
        }
        System.out.println(result != null ? result.toString() : null);


        //java 8 optional
        Integer value1 = null;
        Integer value2 = 10;

        //Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println(tester.sum(a, b));

    }

    private Integer sum(Optional<Integer> a, Optional<Integer> b) {
        //Optional.isPresent - checks the value is present or not

        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());

        //Optional.orElse - returns the value if present otherwise returns the default value passed.
        Integer value1 = a.orElse(0);

        //Optional.get - gets the value, value should be present
        Integer value2 = b.get();
        return value1 + value2;
    }

    private void testLocalDateTime() {
        // Get the current date and time
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Current DateTime: " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1);

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("Month: " + month + "day: " + day + "seconds: " + seconds);

        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
        System.out.println("date2: " + date2);

        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

        //22 hour 15 minutes
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        //parse a string
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);
    }

    private void testZonedDateTime() {
        ZonedDateTime date1 = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]");
        System.out.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("CurrentZone: " + currentZone);
    }

    private void testChronoUnits() {
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);

        //add 1 week to the current date
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Next week: " + nextWeek);

        LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + nextMonth);

        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Next year: " + nextYear);

        LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
        System.out.println("Date after ten year: " + nextDecade);
    }

    private void testPeriod() {
        LocalDate date1 = LocalDate.now();
        System.out.println("Current date: " + date1);

        //add 1 month to the current date
        LocalDate date2 = date1.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + date2);

        Period period = Period.between(date2, date1);
        System.out.println("Period: " + period);
    }

    private void testDuration() {
        LocalTime time1 = LocalTime.now();
        Duration twoHours = Duration.ofHours(2);

        LocalTime time2 = time1.plus(twoHours);
        Duration duration = Duration.between(time1, time2);

        System.out.println("Duration: " + duration);
    }

    private void testAdjusters() {
        LocalDate date1 = LocalDate.now();
        System.out.println("Current date: " + date1);

        //get the next tuesday
        LocalDate nextTuesday = date1.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        System.out.println("Next Tuesday on : " + nextTuesday);

        //get the second saturday of next month
        LocalDate firstInYear = LocalDate.of(date1.getYear(), date1.getMonth(), 1);
        LocalDate secondSaturday = firstInYear.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println("Second Saturday on : " + secondSaturday);
    }

    private void testBackwardCompatability() {
        Date currentDate = new Date();
        System.out.println("Current date: " + currentDate);

        //Get the instant of current date in terms of milliseconds
        Instant now = currentDate.toInstant();
        ZoneId currentZone = ZoneId.systemDefault();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
        System.out.println("Local date: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
        System.out.println("Zoned date: " + zonedDateTime);
    }

    private static void eval(List<Integer> list, Predicate<Integer> predicate) {
        list.stream().filter(predicate).forEach(n -> System.out.println(n + " "));
    }

    private void sortUsingJava8(List<String> names) {
        Collections.sort(names, String::compareTo);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}