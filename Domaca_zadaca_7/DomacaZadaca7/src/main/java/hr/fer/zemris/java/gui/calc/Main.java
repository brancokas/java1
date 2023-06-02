package hr.fer.zemris.java.gui.calc;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}
