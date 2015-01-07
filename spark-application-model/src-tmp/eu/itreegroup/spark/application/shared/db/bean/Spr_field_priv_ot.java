package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Spr_field_priv_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private String field_code;

    private String is_accessible;

    private String is_visible;

    private String is_editable;

    public Spr_field_priv_ot() {
    }

    public Spr_field_priv_ot(String field_code, String is_accessible, String is_visible, String is_editable) {
        this.field_code = field_code;
        this.is_accessible = is_accessible;
        this.is_visible = is_visible;
        this.is_editable = is_editable;
    }

    public Spr_field_priv_ot(Spr_field_priv_ot src) {
        if (src == null)
            return;

        this.field_code = src.getField_code();
        this.is_accessible = src.getIs_accessible();
        this.is_visible = src.getIs_visible();
        this.is_editable = src.getIs_editable();
    }

    public String getField_code() {
        return this.field_code;
    }

    public void setField_code(String field_code) {
        this.field_code = field_code;
    }

    public String getIs_accessible() {
        return this.is_accessible;
    }

    public void setIs_accessible(String is_accessible) {
        this.is_accessible = is_accessible;
    }

    public String getIs_visible() {
        return this.is_visible;
    }

    public void setIs_visible(String is_visible) {
        this.is_visible = is_visible;
    }

    public String getIs_editable() {
        return this.is_editable;
    }

    public void setIs_editable(String is_editable) {
        this.is_editable = is_editable;
    }

    @Override
    public String toString() {
        return Spr_field_priv_ot.class.getName() + "@[" + "field_code = " + field_code + ", is_accessible = " + is_accessible + ", is_visible = " + is_visible + ", is_editable = "
                + is_editable + "]";
    }

}
