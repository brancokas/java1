package hr.fer.zemris.java.gui.calc;

public class CalculatorInputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor.
     */
    public CalculatorInputException() {
    }

    /**
     * Konstruktor.
     * @param message poruka koja opisuje pogrešku
     */
    public CalculatorInputException(String message) {
        super(message);
    }

}
