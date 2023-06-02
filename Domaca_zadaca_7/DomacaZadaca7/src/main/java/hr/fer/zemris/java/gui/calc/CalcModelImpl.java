package hr.fer.zemris.java.gui.calc;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{
    private boolean isEditable = true;
    private boolean isPositive = true;
    private boolean isDecimal = false;
    private String stringNumber = "";
    private String freezedNumber;
    private double numericNumber = 0;
    private Double activeOperand;
    private DoubleBinaryOperator pendingOperation;
    private List<CalcValueListener> listeners;

    public CalcModelImpl() {
        listeners = new LinkedList<>();
    }
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return numericNumber;
    }

    @Override
    public void setValue(double value) {
        numericNumber = value;
        stringNumber = String.valueOf(Math.abs(value));
        isPositive = value > 0 ? true : false;
        isEditable = false;
        alertListeners();
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void clear() {
        numericNumber = 0;
        stringNumber = "";
        freezedNumber = null;
        isPositive = true;
        isDecimal = false;
        isEditable = true;
        alertListeners();
    }

    @Override
    public void clearAll() {
        clearActiveOperand();
        clear();
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Nije editabilno.");
        isPositive = !isPositive;
        numericNumber = numericNumber * -1;
        freezedNumber = null;
        alertListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Kalkulator nije editabilan");
        if (isDecimal) throw new CalculatorInputException("Broj sadrzi vec decimalnu tocku");
        isDecimal = true;
        if (stringNumber.isEmpty())
            throw new CalculatorInputException("Unesite broj");
        stringNumber += ".";
        freezedNumber = null;
        alertListeners();
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable) throw new CalculatorInputException("Ne moze se unjeti znamenka");
        String finite = stringNumber + digit;
        if (stringNumber.equals("0") && digit == 0);
        else if (Double.isFinite(Double.valueOf(finite))) {
            if (stringNumber.equals("0"))
                stringNumber = "";
            stringNumber += digit;
            numericNumber = Double.valueOf(stringNumber) * (isPositive ? 1 : -1);
        } else {
            throw new CalculatorInputException("Broj prevelik");
        }
        freezedNumber = null;
        alertListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperand != null;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet()) throw new IllegalStateException("Operand nije postavljen");
        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        freezeValue(String.valueOf(activeOperand));
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = null;
        pendingOperation = null;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
        isEditable = true;
    }

    @Override
    public void freezeValue(String value) {
        freezedNumber = value;
        stringNumber = "";
        numericNumber = 0;
        isPositive = true;
        isDecimal = false;
        alertListeners();
    }

    public void alertListeners() {
        listeners.forEach((t) -> t.valueChanged(this));
    }

    @Override
    public boolean hasFrozenValue() {
        return freezedNumber != null;
    }

    @Override
    public String toString() {
        if (hasFrozenValue())
            return freezedNumber;
        if (stringNumber.isEmpty())
            return isPositive ? "0" : "-0";
        if (Double.isInfinite(numericNumber))
            return isPositive ? "Infinity" : "-Infinity";
        if (Double.isNaN(numericNumber))
            return "NaN";
        return isPositive ? stringNumber : "-" + stringNumber;
    }
}
