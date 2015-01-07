package univ.evector.beans.book;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GramWord implements Serializable {

    private Long gwrd_id;

    private GramWordType type;

    private String gwrd_origin;

    private String gwrd_value;

    private String gwrd_v;

    private Boolean gwrd_is_nava_word;

    private String gwrd_part_of_speech;

    public GramWord() {

    }

    public Long getGwrd_id() {
        return gwrd_id;
    }

    public void setGwrd_id(Long gwrd_id) {
        this.gwrd_id = gwrd_id;
    }

    public GramWordType getType() {
        return type;
    }

    public void setType(GramWordType type) {
        this.type = type;
    }

    public String getGwrd_origin() {
        return gwrd_origin;
    }

    public void setGwrd_origin(String gwrd_origin) {
        this.gwrd_origin = gwrd_origin;
    }

    public String getGwrd_value() {
        return gwrd_value;
    }

    public void setGwrd_value(String gwrd_value) {
        this.gwrd_value = gwrd_value;
    }

    public String getGwrd_v() {
        return gwrd_v;
    }

    public void setGwrd_v(String gwrd_v) {
        this.gwrd_v = gwrd_v;
    }

    public Boolean getGwrd_is_nava_word() {
        return gwrd_is_nava_word;
    }

    public void setGwrd_is_nava_word(Boolean gwrd_is_nava_word) {
        this.gwrd_is_nava_word = gwrd_is_nava_word;
    }

    public String getGwrd_part_of_speech() {
        return gwrd_part_of_speech;
    }

    public void setGwrd_part_of_speech(String gwrd_part_of_speech) {
        this.gwrd_part_of_speech = gwrd_part_of_speech;
    }

    @Override
    public String toString() {
        return "GramWord [gwrd_id=" + gwrd_id + ", type=" + type + ", gwrd_origin=" + gwrd_origin + ", gwrd_value=" + gwrd_value + ", gwrd_v=" + gwrd_v + ", gwrd_is_nava_word="
                + gwrd_is_nava_word + ", gwrd_part_of_speech=" + gwrd_part_of_speech + "]";
    }

}