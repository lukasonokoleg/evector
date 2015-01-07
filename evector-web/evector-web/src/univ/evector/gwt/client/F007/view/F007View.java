package univ.evector.gwt.client.F007.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.ui.form.field.TextAreaField;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.emotion.Emotion;
import univ.evector.gwt.client.F007.presenter.F007Presenter.F007FormDisplay;
import univ.evector.gwt.client.F007.resource.F007Constants;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

public class F007View extends BaseFormView<Emotion> implements F007FormDisplay {

    public final static F007Constants C = GWT.create(F007Constants.class);

    private FlexTable skeleton = new FlexTable();

    private TextField emotionNameField = new TextField();
    private TextAreaField emotionWordsField = new TextAreaField();

    {
        emotionNameField.setRequired(true);
        emotionNameField.setLabelText(C.labelEmotionName());

        emotionWordsField.setRequired(true);
        emotionWordsField.setLabelText(C.labelEmotionWords());
    }

    private Button saveButton = EvectorButtonHelper.getInstance().createOkButton(C.buttonSave());

    public F007View() {
        getBodyContainer().add(skeleton);
        constructForm();
    }

    private void constructForm() {
        int row = 0;
        int column = 0;

        skeleton.setWidget(row, column, emotionNameField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, emotionNameField);

        column = 0;
        row++;

        skeleton.setWidget(row, column, emotionWordsField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, emotionWordsField);

        column = 1;
        row++;

        skeleton.setWidget(row, column, saveButton);
    }

    @Override
    public String getFormCaption() {
        return null;
    }

    @Override
    public void defaultFocus() {

    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean retVal = true;
        retVal = retVal & ValidationHelper.validateField(emotionNameField, container);
        retVal = retVal & ValidationHelper.validateField(emotionWordsField, container);
        return retVal;
    }

    @Override
    protected void setFormValue(Emotion value) {
        value = value != null ? value : newValue();

        emotionNameField.setValue(value.getEmo_name());
        emotionWordsField.setValue(value.getValue());

    }

    @Override
    public void getValue(Emotion value) {

        String emoName = value.getEmo_name();
        String emoValue = value.getValue();

        value.setEmo_name(emoName);
        value.setValue(emoValue);

    }

    @Override
    public Emotion newValue() {
        return new Emotion();
    }

}
