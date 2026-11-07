package ru.gr05504.application;

import ru.gr05504.domain.Calculation;
import ru.gr05504.domain.MethodRealised;

public class CalculateCommand implements Command{
    private final CalculationService service;
    private final String formula;
    private final double lower;
    private final double upper;
    private final int intervals;
    private final MethodRealised method;
    private Calculation calculation;

    public CalculateCommand(
            CalculationService service,
            String formula,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        this.service = service;
        this.formula = formula;
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method;
    }

    @Override
    public void execute() {
        calculation = service.calculate(formula, lower, upper, intervals, method);
    }

    @Override
    public void undo() {
        service.delete(calculation);
    }
}
