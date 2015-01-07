package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {

    private Long msg_id;

    private User user;

    public Message() {

    }

    public Long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Long msg_id) {
        this.msg_id = msg_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message [msg_id=" + msg_id + ", user=" + user + "]";
    }

}
