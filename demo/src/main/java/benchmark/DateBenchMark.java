package benchmark;

import org.joda.time.DateTime;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/6/17
 * Time: 21:36
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class DateBenchMark {

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
        Options opt = new OptionsBuilder().include(DateBenchMark.class.getSimpleName()).build();
        new Runner(opt).run();
    }
}
