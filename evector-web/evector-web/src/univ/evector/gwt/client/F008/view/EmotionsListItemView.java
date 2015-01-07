package univ.evector.gwt.client.F008.view;

import lt.jmsys.spark.gwt.client.ui.form.field.TextAreaField;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.form.view.EditFormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.emotion.Emotion;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;

public class EmotionsListItemView implements EditFormDisplay<Emotion> {

    private Emotion value;

    private TextField emotionNameField = new TextField();
    private TextAreaField emotionWordsField = new TextAreaField();

    {
        emotionNameField.setWidth("150px");

        emotionWordsField.setSize("400px", "60px");
        emotionWordsField.getElement().getStyle().setProperty("maxWidth", "400px");

    }

    private Anchor removeAnchor = EvectorButtonHelper.getInstance().createDeleteIcon("");

    public EmotionsListItemView() {

    }

    @Override
    public void setValue(Emotion value) {
        this.value = value != null ? value : newValue();
        setFormValue(this.value);
        updateFormView();
    }

    private void setFormValue(Emotion value) {
        value = value != null ? value : newValue();
        emotionNameField.setValue(value.getEmo_name());
        emotionWordsField.setValue(value.getValue());

    }

    private void updateFormView() {

    }

    @Override
    public Emotion getValue() {
        Emotion retVal = value != null ? value : newValue();
        getValue(retVal);
        return retVal;
    }

    private void getValue(Emotion value) {
        value.setEmo_name(emotionNameField.getValue());
        value.setValue(emotionWordsField.getValue());
    }

    protected Emotion newValue() {
        return new Emotion();
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean retVal = true;

        retVal = retVal & ValidationHelper.validateField(emotionNameField, container);
        retVal = retVal & ValidationHelper.validateField(emotionWordsField, container);

        return retVal;
    }

    public Widget getEmotionNameField() {
        return emotionNameField;
    }

    public Widget getEmotionWordsField() {
        return emotionWordsField;
    }

    public Anchor getRemoveAnchor() {
        return removeAnchor;
    }

}
