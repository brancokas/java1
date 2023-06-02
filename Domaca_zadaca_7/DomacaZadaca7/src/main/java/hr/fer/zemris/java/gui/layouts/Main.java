package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();

    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new Calculator(), BorderLayout.CENTER);
        cp.add(new JButton("Button"), BorderLayout.PAGE_END);
    }

    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
