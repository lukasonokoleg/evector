package univ.evector.beans.browse;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FindDocuments implements Serializable {

    private Long doc_id;

    private Date doc_date;

    private String doc_name;

    private String doc_mime_type;

    private Long doc_size;

    public FindDocuments() {

    }

    public Long getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(Long doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_mime_type() {
        return doc_mime_type;
    }

    public void setDoc_mime_type(String doc_mime_type) {
        this.doc_mime_type = doc_mime_type;
    }

    public Date getDoc_date() {
        return doc_date;
    }

    public void setDoc_date(Date doc_date) {
        this.doc_date = doc_date;
    }

    public Long getDoc_size() {
        return doc_size;
    }

    public void setDoc_size(Long doc_size) {
        this.doc_size = doc_size;
    }

    @Override
    public String toString() {
        return "FindDocuments [doc_id=" + doc_id + ", doc_name=" + doc_name + ", doc_mime_type=" + doc_mime_type + ", doc_date=" + doc_date + "]";
    }

}