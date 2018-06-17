package benchmark;

import org.joda.time.DateTime;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/6/17
 * Time: 21:36
 * 不使用注解的方式
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DateBenchMark1 {

    @Benchmark
    public Calendar runCalendar() {
        return Calendar.getInstance();
    }

    @Benchmark
    public DateTime runJoda() {
        return new DateTime();
    }

    @Benchmark
    public long runSystem() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DateBenchMark.class.getSimpleName())
                .forks(1)
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(1))
                .build();
        new Runner(opt).run();
    }
}
