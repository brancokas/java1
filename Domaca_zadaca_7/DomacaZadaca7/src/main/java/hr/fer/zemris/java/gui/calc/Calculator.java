package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Calculator extends JFrame {

    private CalcModelImpl model = new CalcModelImpl();
    private boolean inverse = false;

    public Calculator() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        pack();
    }

    private void initGUI() {
        getContentPane().setLayout(new CalcLayout());

        JButton[] digits = new JButton[10], functions = new JButton[8], operators = new JButton[6];
        JButton[] instructions = new JButton[4];
        JButton tocka = new JButton(".");
        JLabel label = new JLabel("0", null, SwingConstants.TRAILING);
        JCheckBox inv = new JCheckBox("Inv");

        label.setBackground(Color.YELLOW);
        label.setOpaque(true);

        CalcValueListener valueListener = (model) -> {
            label.setText(model.toString());
        };



        model.addCalcValueListener(valueListener);

        instanciateDigits(digits);
        instanciateFunctions(functions);
        instanciateOperators(operators);
        instanciateInstructions(instructions);

        Container p = this;
        p.add(label, "1,1");

        p.add(digits[0], "5,3");
        p.add(digits[1], "4,3");
        p.add(digits[2], "4,4");
        p.add(digits[3], "4,5");
        p.add(digits[4], "3,3");
        p.add(digits[5], "3,4");
        p.add(digits[6], "3,5");
        p.add(digits[7], "2,3");
        p.add(digits[8], "2,4");
        p.add(digits[9], "2,5");

        p.add(functions[0], "2,1");
        p.add(functions[1], "2,2");
        p.add(functions[2], "3,1");
        p.add(functions[3], "3,2");
        p.add(functions[4], "4,1");
        p.add(functions[5], "4,2");
        p.add(functions[6], "5,1");
        p.add(functions[7], "5,2");

        p.add(operators[0], "5,4");
        p.add(operators[1], "5,6");
        p.add(operators[2], "4,6");
        p.add(operators[3], "3,6");
        p.add(operators[4], "2,6");
        p.add(operators[5], "1,6");

        p.add(tocka, "5,5");
        tocka.addActionListener((event) -> model.insertDecimalPoint());

        p.add(instructions[0], "4,7");
        p.add(instructions[1], "3,7");
        p.add(instructions[2], "2,7");
        p.add(instructions[3], "1,7");

        p.add(inv, "5,7");
        inv.addActionListener((event) -> {
            inverse = !inverse;
            model.alertListeners();
        });
    }

    private JButton instanciate(String text, Action action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void instanciateDigits(JButton[] digits) {
        for (int i = 0; i < digits.length; i++) {
            digits[i] = instanciate(String.valueOf(i), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    model.insertDigit(Integer.parseInt(button.getText()));
                }
            });
        }
    }

    private void instanciateFunctions(JButton[] functions) {
        functions[0] = new JButton("1/x");
        functions[0].addActionListener((event) -> {
            model.setValue(1 / model.getValue());
        });
        functions[1] = new JButton("sin");
        functions[1].addActionListener((e -> {
            if (inverse)
                model.setValue(Math.asin(model.getValue()));
            else model.setValue(Math.sin(model.getValue()));
        }));
        functions[2] = new JButton("log");
        functions[2].addActionListener((e -> {
            if (inverse)
                model.setValue(Math.pow(10,model.getValue()));
            else model.setValue(Math.log10(model.getValue()));
        }));
        functions[3] = new JButton("cos");
        functions[3].addActionListener((e -> {
            if (inverse)
                model.setValue(Math.acos(model.getValue()));
            else model.setValue(Math.cos(model.getValue()));
        }));
        functions[4] = new JButton("ln");
        functions[4].addActionListener((e -> {
            if (inverse)
                model.setValue(Math.exp(model.getValue()));
            else model.setValue(Math.log(model.getValue()));
        }));
        functions[5] = new JButton("tan");
        functions[5].addActionListener((e -> {
            if (inverse)
                model.setValue(Math.atan(model.getValue()));
            else model.setValue(Math.tan(model.getValue()));
        }));
        functions[6] = new JButton("x^n");
        functions[6].addActionListener((e -> {
            model.setActiveOperand(model.getValue());
            if (inverse)
                model.setPendingBinaryOperation((x,n) -> Math.pow(x, 1/n));
            else model.setPendingBinaryOperation((x,n) -> Math.pow(x,n));
        }));
        functions[7] = new JButton("ctg");
        functions[7].addActionListener((e -> {
            if (inverse)
                model.setValue(1 / Math.atan(model.getValue()));
            else model.setValue(1 / Math.tan(model.getValue()));
        }));
        CalcValueListener funkcije = (model) -> {
            if (inverse) {
                functions[1].setText("arcsin");
                functions[2].setText("10^x");
                functions[3].setText("arccos");
                functions[4].setText("e^x");
                functions[5].setText("arctan");
                functions[6].setText("x^(1/n)");
                functions[7].setText("arcctg");

            }
            else {
                functions[1].setText("sin");
                functions[2].setText("log");
                functions[3].setText("cos");
                functions[4].setText("ln");
                functions[5].setText("tan");
                functions[6].setText("x^n");
                functions[7].setText("ctg");
            }
        };
        model.addCalcValueListener(funkcije);
    }

    private void instanciateOperators(JButton[] operators) {
        operators[0] = new JButton("+/-");
        operators[0].addActionListener((event) -> model.swapSign());
        operators[1] = new JButton("+");
        operators[1].addActionListener((event) -> {
            binaryOperations();
            model.setPendingBinaryOperation((l,r) -> l+r);
        });
        operators[2] = new JButton("-");
        operators[2].addActionListener((event) -> {
            binaryOperations();
            model.setPendingBinaryOperation((l,r) -> l-r);
        });
        operators[3] = new JButton("*");
        operators[3].addActionListener((event) -> {
            binaryOperations();
            model.setPendingBinaryOperation((l,r) -> l*r);
        });
        operators[4] = new JButton("/");
        operators[4].addActionListener((event) -> {
            binaryOperations();
            model.setPendingBinaryOperation((l,r) -> l/r);
        });
        operators[5] = new JButton("=");
        operators[5].addActionListener((event) -> {
            binaryOperations();
            model.setPendingBinaryOperation(null);
        });
    }

    private void binaryOperations() {
        double value = model.getValue();
        if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
            double operand = model.getActiveOperand();
            value = model.getPendingBinaryOperation().applyAsDouble(operand, value);
        }
        model.setActiveOperand(value);
    }

    private void instanciateInstructions(JButton[] instructions) {
        instructions[0] = new JButton("pop");
        instructions[1] = new JButton("push");
        instructions[2] = new JButton("reset");
        instructions[2].addActionListener((event) -> model.clearAll());
        instructions[3] = new JButton("clr");
        instructions[3].addActionListener((event) -> model.clear());
    }

}
