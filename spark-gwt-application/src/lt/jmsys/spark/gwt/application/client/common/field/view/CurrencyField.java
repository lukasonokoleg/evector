package lt.jmsys.spark.gwt.application.client.common.field.view;

import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField;

import com.google.gwt.user.client.ui.SparkSuggestBox;

import eu.itreegroup.spark.gwt.application.client.suggest.CurrencyOracle;

public class CurrencyField extends SuggestField<String> {

    private CurrencyOracle oracle;
    
    public CurrencyField() {
        this(new CurrencyOracle());
    }
    
    public CurrencyField(String label) {
        this(label, new CurrencyOracle());
    }
    
    protected CurrencyField(CurrencyOracle oracle) {
        this(null, oracle);
    }

    protected CurrencyField(String label, CurrencyOracle oracle) {
        this(label, new SparkSuggestBox(oracle), oracle);
    }

    protected CurrencyField(String label, SparkSuggestBox box, CurrencyOracle oracle) {
        super(box, oracle);
        this.oracle = oracle;
        setLabelText(label);
        setWidth("120px");
        box.setMinScrollWidth(100);
        box.setMaxScrollWidth(120);
    }
    
    public void addCurrency(String code, String name) {
        if (code != null && !code.isEmpty()) {
            oracle.addCurrency(code, name);
        }
    }

    public CurrencyOracle getOracle() {
        return oracle;
    }

    public void setOracle(CurrencyOracle oracle) {
        this.oracle = oracle;
    }

}
