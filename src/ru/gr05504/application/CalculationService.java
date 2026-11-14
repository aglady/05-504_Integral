package ru.gr05504.application;

import ru.gr05504.domain.*;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CalculationService {
    private final CalculationRepository repository;
    private final FormulaParser parser;

    public CalculationService(CalculationRepository repository, FormulaParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    // Разбирает формулу, вычисляет интеграл и сохраняет расчёт
    public Calculation calculate(String formula, double lower, double upper,
                                 int intervals, MethodRealised method) {
        DoubleUnaryOperator f = parser.parse(formula);
        var timedStrategy = new TimedIntegrationStrategy(method.strategy());
        var timedMethod = new MethodRealised(method.name(), timedStrategy);
        double result = Integrator.integrate(f, lower, upper, intervals, timedMethod);
        long elapsed = timedStrategy.getElapsed();

        Calculation calculation = new Calculation(formula, lower, upper, intervals, method, result, elapsed);
        repository.save(calculation);
        return calculation;
    }

    public void delete(Calculation calculation) {
        repository.delete(calculation);
    }

    public void restore(Calculation calculation){
        repository.save(calculation);
    }

    public List<Calculation> getAll() {
        return repository.findAll();
    }
}