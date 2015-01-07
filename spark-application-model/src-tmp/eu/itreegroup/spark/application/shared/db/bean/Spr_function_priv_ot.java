package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Spr_function_priv_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private String function_code;

    private String is_enabled;

    private String disabled_text_code;

    private String disabled_text;

    public Spr_function_priv_ot() {
    }

    public Spr_function_priv_ot(String function_code, String is_enabled, String disabled_text_code, String disabled_text) {
        this.function_code = function_code;
        this.is_enabled = is_enabled;
        this.disabled_text_code = disabled_text_code;
        this.disabled_text = disabled_text;
    }

    public Spr_function_priv_ot(Spr_function_priv_ot src) {
        if (src == null)
            return;

        this.function_code = src.getFunction_code();
        this.is_enabled = src.getIs_enabled();
        this.disabled_text_code = src.getDisabled_text_code();
        this.disabled_text = src.getDisabled_text();
    }

    public String getFunction_code() {
        return this.function_code;
    }

    public void setFunction_code(String function_code) {
        this.function_code = function_code;
    }

    public String getIs_enabled() {
        return this.is_enabled;
    }

    public void setIs_enabled(String is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getDisabled_text_code() {
        return this.disabled_text_code;
    }

    public void setDisabled_text_code(String disabled_text_code) {
        this.disabled_text_code = disabled_text_code;
    }

    public String getDisabled_text() {
        return this.disabled_text;
    }

    public void setDisabled_text(String disabled_text) {
        this.disabled_text = disabled_text;
    }

    @Override
    public String toString() {
        return Spr_function_priv_ot.class.getName() + "@[" + "function_code = " + function_code + ", is_enabled = " + is_enabled + ", disabled_text_code = " + disabled_text_code
                + ", disabled_text = " + disabled_text + "]";
    }

}
