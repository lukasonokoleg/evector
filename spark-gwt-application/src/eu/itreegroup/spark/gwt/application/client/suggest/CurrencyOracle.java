package eu.itreegroup.spark.gwt.application.client.suggest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.client.suggest.oracle.TransliterationHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField.TransliteratedSuggestion;
import lt.jmsys.spark.gwt.client.ui.form.field.flag.CurrencyFlagHelper;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Image;

public class CurrencyOracle extends ClassifierServiceOracle<String> {


    private Map<String, String> currencies = new HashMap<String, String>();

    public CurrencyOracle() {
        super(null);
    }

    @Override
    public Suggestion toSuggestion(String value) {
        return new CurrencySuggestion(value);
    }

    @Override
    public String toValue(Suggestion suggestion) {
        if (null != suggestion) {
            return ((CurrencySuggestion) suggestion).getValue();
        } else {
            return null;
        }
    }

    @Override
    protected void callService(Request request, Callback callback) {
        List<String> result = new ArrayList<String>(currencies.keySet());
        asyncResponse(request, callback).onSuccess(result);
    }

    public void addCurrency(String code, String name) {
        currencies.put(code, name);
    }
    
    public void clear() {
        currencies.clear();
    }

    private class CurrencySuggestion extends AbstractHighlightedSuggestion implements TransliteratedSuggestion {

        String value;

        public CurrencySuggestion(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String getPlainTextDisplayString() {
            if (null != value) {
                return currencies.get(value);
            } else {
                return null;
            }
        }

        @Override
        public String getReplacementString() {
            return getPlainTextDisplayString();
        }

        @Override
        public String getTransliteratedMatchString() {
            return TransliterationHelper.transliterate(getPlainTextDisplayString());
        }

        @Override
        public String getDisplayString() {
            return getDisplayHTML(super.getDisplayString());
        }

        private String getDisplayHTML(String text) {
            if (text == null) {
                return null;
            }
            Image img = new Image(CurrencyFlagHelper.getCurrencyFlag(value));
            SafeHtmlBuilder buff = new SafeHtmlBuilder();
            buff.appendHtmlConstant(img.toString());
            buff.appendHtmlConstant(text);
            return buff.toSafeHtml().asString();
        }
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }
}
