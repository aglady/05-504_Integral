package ru.gr05504.domain;

import java.util.function.DoubleUnaryOperator;

public class TimedIntegrationStrategy implements IntegratorStrategy {

    private final IntegratorStrategy inner;
    private long elapsed;

    public TimedIntegrationStrategy(IntegratorStrategy inner) {
        this.inner = inner;
    }

    @Override
    public double integralCalculation(DoubleUnaryOperator f, double a, double b, int n) {
        var start = System.nanoTime();
        double result = inner.integralCalculation(f, a, b, n);
        var finish = System.nanoTime();
        elapsed = finish - start;
        return result;
    }

    public long getElapsed() {
        return elapsed;
    }
}
