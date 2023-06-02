package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
    private Path path;
    private JTextArea textArea;
    private boolean modified = false;
    List<SingleDocumentListener> documentListeners;

    public DefaultSingleDocumentModel(Path path, String content) {
        this.path = path;
        textArea = new JTextArea(content);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!modified) {
                    modified = true;
                    notifySingleDocumentListeners();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!modified) {
                    modified = true;
                    notifySingleDocumentListeners();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!modified) {
                    modified = true;
                    notifySingleDocumentListeners();
                }
            }
        });
        documentListeners = new LinkedList<>();
    }

    private void notifySingleDocumentListeners() {
        for (var listener : documentListeners)
            listener.documentModifyStatusUpdated(this);
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return path;
    }

    @Override
    public void setFilePath(Path path) {
        if (path != null && path.equals(this.path)) {
            this.path = path;
            notifySingleDocumentListenersPaths();
        }
    }

    private void notifySingleDocumentListenersPaths() {
        for (var listeners : documentListeners)
            listeners.documentFilePathUpdated(this);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        notifySingleDocumentListeners();
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        documentListeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        documentListeners.remove(l);
    }
}
