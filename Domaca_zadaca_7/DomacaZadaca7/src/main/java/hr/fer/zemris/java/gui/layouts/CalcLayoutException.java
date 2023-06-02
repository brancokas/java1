package hr.fer.zemris.java.gui.layouts;

public class CalcLayoutException extends RuntimeException{

    private String message;

    public CalcLayoutException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
