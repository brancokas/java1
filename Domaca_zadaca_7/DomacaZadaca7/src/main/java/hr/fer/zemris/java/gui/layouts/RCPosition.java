package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RCPosition {
    private int row, column;

    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private static boolean isDigit(char znak) {
        if (znak >= '0' && znak <= '9') return true;
        return false;
    }

    public static RCPosition parse(String text) {
        if (text == null || text.length() != 3)
            throw new IllegalArgumentException("Vrijednost treba biti \"row,column\".");
        if (text.charAt(1) != ',' && !isDigit(text.charAt(0)) && !isDigit(text.charAt(2)))
            throw new IllegalArgumentException("Vrijednost treba biti \"row,column\".");
        String prvi = String.valueOf(text.charAt(0)),
            drugi = String.valueOf(text.charAt(2));
        return new RCPosition(Integer.valueOf(prvi), Integer.valueOf(drugi));
    }

    private static void check(int row, int column) {
        if (row < 1 || row > 5) throw new CalcLayoutException("Wrong constraint input");
        if (column < 1 || column > 7) throw new CalcLayoutException("Wrong constraint input");
        if (row == 1 && (column > 1 && column < 6))
            throw new CalcLayoutException("Prvi redak ima stupce 1, 6 i 7");
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
