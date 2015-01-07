package lt.jmsys.spark.gwt.application.client.common.field.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.client.ui.form.field.ListField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.form.view.EditFormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import eu.itreegroup.spark.application.bean.Classifier;

public class ClassifierListBox<E extends Classifier> extends Composite implements EditFormDisplay<E> {

    Map<String, E> valuesMap = new HashMap<String, E>();
    ListField listBox;

    public ClassifierListBox(String labelText, List<E> values) {
        this(new ListField(labelText), values);
    }

    public ClassifierListBox(ListField listField, List<E> values) {
        listBox = listField;
        init(values);
    }

    public ClassifierListBox(List<E> values) {
        listBox = new ListField(true);
        init(values);
    }

    public ClassifierListBox(List<E> values, boolean addEmptyItem) {
        listBox = new ListField(addEmptyItem);
        init(values);
    }

    protected void init(List<E> values) {
        setValues(values);
        initWidget(listBox);
    }

    public void setValues(List<E> values) {
        listBox.clear();
        valuesMap.clear();
        if (null != values) {
            for (E v : values) {
                listBox.addItem(v.getCode(), v.getDisplayValue());
                valuesMap.put(v.getCode(), v);
            }
        }
    }

    public void setValue(E value) {
        if (null != value) {
            listBox.setValue(value.getCode());
        } else {
            listBox.setValue("");
        }
    }

    public void setValueCode(String value) {
        if (null != value) {
            listBox.setValue(value);
        } else {
            listBox.setValue("");
        }
    }

    /*    public void setValue(Double value) {
            String s;
            if (null == value) {
                s = "";
            } else {
                s = Integer.toString(value.intValue());
            }
            listBox.setValue(s);
        }*/

    public E getValue() {
        return valuesMap.get(listBox.getValue());
    }

    public String getValueCode() {
        E v = getValue();
        if (null != v) {
            return v.getCode();
        } else {
            return null;
        }
    }

    public ListField getListField() {
        return listBox;
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public boolean validate(MessageContainer container) {
        return ValidationHelper.validateField(getListField(), container);
    }

    public void setLabelText(String labelText) {
        listBox.setLabelText(labelText);
    }

    public String getLabelText() {
        return listBox.getLabelWidget().getText();
    }

    public Label getLabelWidget() {
        return listBox.getLabelWidget();
    }

    public void setReadOnly(Boolean readonly) {
        listBox.setReadOnly(readonly);
    }

    public boolean hasValue() {
        return listBox != null && listBox.hasValue();
    }

    public void setFocus(boolean focused) {
        getListField().setFocus(focused);
    }

    public void setRequired(boolean required) {
        getListField().setRequired(required);
    }

    public void setDisplayOnly(boolean displayOnly) {
        getListField().setDisplayOnly(displayOnly);
    }
}
