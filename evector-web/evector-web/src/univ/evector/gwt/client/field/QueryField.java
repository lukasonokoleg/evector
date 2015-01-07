package univ.evector.gwt.client.field;

import lt.jmsys.spark.gwt.client.ui.form.field.TextField;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import univ.evector.gwt.client.helper.EvectorButtonHelper;

public class QueryField extends SimplePanel {

    private TextField textField = new TextField() {

        @Override
        protected void setStyleOnFocus(Widget widget) {
            QueryField.this.addStyleDependentName("focused");
            QueryField.this.addStyleName("focused");
        }

        @Override
        protected void removeStyleOnBlur(Widget widget) {
            QueryField.this.removeStyleDependentName("focused");
            QueryField.this.removeStyleName("focused");
        }
    };

    public void setFocus(boolean focused) {
        textField.setFocus(focused);
    }

    public QueryField(String placeholder) {
        textField.setPlaceholder(placeholder);
        setWidget(textField);
        attachSearchButton();
        setStyleName("queryField");
    }

    public String getValue() {
        return textField.getValue();
    }

    public void addKeyDownHandler(KeyDownHandler handler) {
        textField.addKeyDownHandler(handler);
    }

    public void addValueChangeHandler(ValueChangeHandler<String> handler) {
        textField.addValueChangeHandler(handler);
    }

    /**
     * Attach search icon to {@link DOM} before field element.
     */
    private void attachSearchButton() {
        Anchor icon = EvectorButtonHelper.getInstance().createSearchIcon("");
        icon.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ValueChangeEvent.fire(textField, textField.getValue());
            }
        });
        getElement().insertBefore(icon.getElement(), textField.getElement());
    }

}
