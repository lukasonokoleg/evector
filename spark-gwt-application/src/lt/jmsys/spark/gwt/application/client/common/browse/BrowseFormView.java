package lt.jmsys.spark.gwt.application.client.common.browse;


import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

public abstract class BrowseFormView<T> extends BaseFormView<T> implements BrowseFormDisplay<T> {

    @Override
    public String getFormCaption() {
        return null;
    }

    @Override
    public void defaultFocus() {
    }

    @Override
    public boolean validate(MessageContainer container) {
        return false;
    }

    @Override
    protected void setFormValue(T value) {
    }

    @Override
    public void getValue(T value) {

    }

    @Override
    public T newValue() {
        return null;
    }

    @Override
    abstract public void refresh();

}
