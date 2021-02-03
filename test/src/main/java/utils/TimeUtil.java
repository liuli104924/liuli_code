package utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtil {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    public TimeUtil() {
    }

    public static <T, R> Function<T, R> printCostTime(Function<T, R> function, String functionName) {
        return (t) -> {
            long start = System.currentTimeMillis();
            R r = function.apply(t);
            long end = System.currentTimeMillis();
            logger.info(functionName + " cost time ms:" + (end - start));
            return r;
        };
    }

    public static <T> Consumer<T> printCostTime(Consumer<T> consumer, String functionName) {
        return (t) -> {
            long start = System.currentTimeMillis();
            consumer.accept(t);
            long end = System.currentTimeMillis();
            logger.info(functionName + " cost time ms:" + (end - start));
        };
    }

    public static <R> Supplier<R> printCostTime(Supplier<R> supplier, String functionName) {
        return () -> {
            long start = System.currentTimeMillis();
            R r = supplier.get();
            long end = System.currentTimeMillis();
            logger.info(functionName + " cost time ms:" + (end - start));
            return r;
        };
    }
}


