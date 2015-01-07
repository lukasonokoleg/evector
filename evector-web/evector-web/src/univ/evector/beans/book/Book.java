package univ.evector.beans.book;

import java.io.Serializable;
import java.util.List;

import univ.evector.beans.Author;

@SuppressWarnings("serial")
public class Book implements Serializable {

    private Long bks_id;

    private String bks_title;

    private String bks_isbn;

    private Author author;

    private BookMetaData metaData;

    private List<Paragraph> paragraphs;

    public Book() {

    }

    public Long getBks_id() {
        return bks_id;
    }

    public void setBks_id(Long bks_id) {
        this.bks_id = bks_id;
    }

    public String getBks_title() {
        return bks_title;
    }

    public void setBks_title(String bks_title) {
        this.bks_title = bks_title;
    }

    public String getBks_isbn() {
        return bks_isbn;
    }

    public void setBks_isbn(String bks_isbn) {
        this.bks_isbn = bks_isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(BookMetaData metaData) {
        this.metaData = metaData;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public String toString() {
        return "Book [bks_id=" + bks_id + ", bks_title=" + bks_title + ", bks_isbn=" + bks_isbn + ", author=" + author + ", metaData=" + metaData + ", paragraphs=" + paragraphs
                + "]";
    }

}