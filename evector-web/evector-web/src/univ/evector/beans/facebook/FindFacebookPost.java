package univ.evector.beans.facebook;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FindFacebookPost implements Serializable {

    private String id;

    private String post;

    private Date postDate;

    public FindFacebookPost() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "FindFacebookPost [id=" + id + ", post=" + post + ", postDate=" + postDate + "]";
    }

}
