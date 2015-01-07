package univ.evector.beans;

import java.sql.Blob;

@SuppressWarnings("serial")
public class DocumentWithBlob extends Document {

    private Blob doc_blob;

    public DocumentWithBlob() {

    }

    public Blob getDoc_blob() {
        return doc_blob;
    }

    public void setDoc_blob(Blob doc_blob) {
        this.doc_blob = doc_blob;
    }

    @Override
    public String toString() {
        return "DocumentWithBlob [doc_blob=" + doc_blob + "]";
    }

}
