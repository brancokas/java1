package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    private List<SingleDocumentModel> documentModels;
    private List<MultipleDocumentListener> documentListeners;
    private SingleDocumentModel currentDocument;

    public DefaultMultipleDocumentModel() {
        documentModels = new LinkedList<>();
        createNewDocument();
        documentListeners = new LinkedList<>();
    }

    @Override
    public JComponent getVisualComponent() {
        return currentDocument.getTextComponent();
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel singleDocumentModel = new DefaultSingleDocumentModel(null, "");
        documentModels.add(singleDocumentModel);

        ImageIcon iconModified, iconSaved;
        iconModified = createImageIcon("../../../../../floppy-disk-modified.png");
        iconSaved = createImageIcon("../../../../../floppy-disk-unmodified.png");

        this.addTab("unnamed", iconSaved, new JScrollPane(singleDocumentModel.getTextComponent()));

        SingleDocumentListener listener = new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                int index = getIndexOfDocument(model);
                if (model.isModified())
                    setIconAt(index, iconModified);
                else setIconAt(index, iconSaved);
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                int index = getIndexOfDocument(model);
                Path path = model.getFilePath();
                setTitleAt(index, path.getFileName().toString());
                setToolTipTextAt(index, path.toString());
            }
        };

        singleDocumentModel.addSingleDocumentListener(listener);
        setSelectedIndex(getIndexOfDocument(singleDocumentModel));
        return currentDocument = singleDocumentModel;
    }

    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = DefaultMultipleDocumentModel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        return null;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        model.setFilePath(newPath);
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {

    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        documentListeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        documentListeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documentModels.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        if (index < 0 && index >= documentModels.size()) throw new IndexOutOfBoundsException();
        return documentModels.get(index);
    }

    @Override
    public SingleDocumentModel findForPath(Path path) {
        return null;
    }

    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documentModels.indexOf(doc);
    }

    @Override
    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        currentDocument = getDocument(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return new DocumentIterator(documentModels);
    }

    private static class DocumentIterator implements Iterator<SingleDocumentModel> {
        private List<SingleDocumentModel> documentModels;
        private int index = 0;

        public DocumentIterator(List<SingleDocumentModel> documentModels) {
            this.documentModels = documentModels;
        }

        @Override
        public boolean hasNext() {
            if (index == documentModels.size())
                return false;
            return true;
        }

        @Override
        public SingleDocumentModel next() {
            if (hasNext()) {
                return documentModels.get(index++);
            }
            throw new IllegalStateException();
        }
    }
}
