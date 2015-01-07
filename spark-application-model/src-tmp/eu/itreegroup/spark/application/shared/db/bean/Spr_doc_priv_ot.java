package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import java.util.Arrays;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Spr_doc_priv_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private String doc_type;

    private Double doc_id;

    private Spr_field_priv_ot[] field_privs;

    private Spr_function_priv_ot[] function_privs;

    public Spr_doc_priv_ot() {
    }

    public Spr_doc_priv_ot(String doc_type, Double doc_id, Spr_field_priv_ot[] field_privs, Spr_function_priv_ot[] function_privs) {
        this.doc_type = doc_type;
        this.doc_id = doc_id;
        this.field_privs = field_privs;
        this.function_privs = function_privs;
    }

    public Spr_doc_priv_ot(Spr_doc_priv_ot src) {
        if (src == null)
            return;

        this.doc_type = src.getDoc_type();
        this.doc_id = src.getDoc_id();
        if (src.getField_privs() != null) {
            this.field_privs = new Spr_field_priv_ot[src.getField_privs().length];
            for (int i = 0; i < this.field_privs.length; i++)
                this.field_privs[i] = new Spr_field_priv_ot(src.getField_privs()[i]);
        }
        if (src.getFunction_privs() != null) {
            this.function_privs = new Spr_function_priv_ot[src.getFunction_privs().length];
            for (int i = 0; i < this.function_privs.length; i++)
                this.function_privs[i] = new Spr_function_priv_ot(src.getFunction_privs()[i]);
        }
    }

    public String getDoc_type() {
        return this.doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public Double getDoc_id() {
        return this.doc_id;
    }

    public void setDoc_id(Double doc_id) {
        this.doc_id = doc_id;
    }

    public Spr_field_priv_ot[] getField_privs() {
        return this.field_privs;
    }

    public void setField_privs(Spr_field_priv_ot[] field_privs) {
        this.field_privs = field_privs;
    }

    public Spr_function_priv_ot[] getFunction_privs() {
        return this.function_privs;
    }

    public void setFunction_privs(Spr_function_priv_ot[] function_privs) {
        this.function_privs = function_privs;
    }

    @Override
    public String toString() {
        return Spr_doc_priv_ot.class.getName() + "@[" + "doc_type = " + doc_type + ", doc_id = " + doc_id + ", field_privs = " + Arrays.toString(field_privs)
                + ", function_privs = " + Arrays.toString(function_privs) + "]";
    }

}
