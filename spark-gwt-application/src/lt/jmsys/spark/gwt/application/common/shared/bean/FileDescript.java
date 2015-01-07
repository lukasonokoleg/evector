package lt.jmsys.spark.gwt.application.common.shared.bean;

import java.io.Serializable;
import java.util.Date;

public class FileDescript implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long size;
    private String name;
    private String title;
    private String notes;
    private String id;
    private String type;
    private String mimeType;
    private Double parentId;
    private String parentType;
    private Date date;
    private Boolean publicFile;

    public FileDescript(Long size, String name, String title, String notes, String id, String mimeType, Double parentId, String parentType, String type, Date date,
            Boolean publicFile) {
        this.size = size;
        this.name = name;
        this.title = title;
        this.notes = notes;
        this.id = id;
        this.mimeType = mimeType;
        this.parentId = parentId;
        this.parentType = parentType;
        this.type = type;
        this.date = date;
        this.publicFile = publicFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double als_doc_type() {
        return parentId;
    }

    public void setParentId(Double parentId) {
        this.parentId = parentId;
    }

    public Double getParentId() {
        return parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isPublicFile() {
        return publicFile;
    }

    public void setPublicFile(Boolean publicFile) {
        this.publicFile = publicFile;
    }

    @Override
    public String toString() {
        return "AgreementItem [name=" + name + ", title=" + title + ", notes=" + notes + ", size=" + size + ", id=" + id + ", mimeType=" + mimeType + ", date = " + date
                + ", publicFile = " + publicFile + "]";
    }
}
