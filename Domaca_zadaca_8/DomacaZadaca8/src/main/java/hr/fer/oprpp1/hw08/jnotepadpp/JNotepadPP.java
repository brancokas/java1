package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class JNotepadPP extends JFrame {
    private DefaultMultipleDocumentModel tabbedPane;
    private Path openedPath;
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        setLocation(0,0);
        setSize(600,600);

        initGui();
    }

    private void initGui() {
        tabbedPane = new DefaultMultipleDocumentModel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        createActions();
        createMenus();
//        createToolbars();

    }

    private Action openNewFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.createNewDocument();
        }
    };

    private Action saveFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (openedPath == null) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save document");
                if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JNotepadPP.this,
                            "Nista nije snimljeno",
                            "Upozorenje",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                openedPath = jfc.getSelectedFile().toPath();
            }
            byte[] bytes = tabbedPane.getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
            tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), openedPath);
        }
    };

    private Action saveAsFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document");
            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        "Nista nije snimljeno",
                        "Upozorenje",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    };

    private void createActions() {
        openNewFile.putValue(
                Action.NAME,
                "New");
        openNewFile.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control N"));
        openNewFile.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_N);
        openNewFile.putValue(
                Action.SHORT_DESCRIPTION,
                "Creates new file in a new tab.");
    }

    public void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.add(new JMenuItem(openNewFile));
        this.setJMenuBar(menuBar);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JNotepadPP().setVisible(true);
            }
        });
    }
}
