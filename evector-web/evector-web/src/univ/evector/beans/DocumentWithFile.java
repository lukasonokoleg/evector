package univ.evector.beans;

import java.io.File;

@SuppressWarnings("serial")
public class DocumentWithFile extends Document {

    private File doc_file;

    public DocumentWithFile() {

    }

    public File getDoc_file() {
        return doc_file;
    }

    public void setDoc_file(File doc_file) {
        this.doc_file = doc_file;
    }

    public static DocumentWithFile newInstance(Long size, String name, String mimeType, File file) {
        DocumentWithFile retVal = new DocumentWithFile();
        retVal.setDoc_size(size);
        retVal.setDoc_name(name);
        retVal.setDoc_mime_type(mimeType);
        retVal.setDoc_file(file);
        return retVal;
    }

    @Override
    public String toString() {
        return "DocumentWithFile [doc_file=" + doc_file + "]";
    }

}
