package univ.evector.gwt.client.F008.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.emotion.EmotionModel;
import univ.evector.gwt.client.F008.presenter.F008Presenter.F008Display;
import univ.evector.gwt.client.F008.resource.F008Constants;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class F008View extends BaseFormView<EmotionModel> implements F008Display {

    public final static F008Constants C = GWT.create(F008Constants.class);

    public final static String EMOTION_MODEL_NAME_FIELD_WIDTH = "400px";

    private HTML emotionModelHeading = ThemeHelper.createHeadingH1(C.emotionModelHeading());
    private HTML emotionsHeading = ThemeHelper.createHeadingH1(C.emotionsHeading());

    private FlexTable formHeadTable = new FlexTable();

    private EmotionsListView emotionListView = new EmotionsListView();

    private TextField emotionModelNameField = new TextField();
    {
        emotionModelNameField.setRequired(true);
        emotionModelNameField.setLabelText(C.labelEmotionModelName());
        emotionModelNameField.setWidth(EMOTION_MODEL_NAME_FIELD_WIDTH);
    }

    private Button saveButton = EvectorButtonHelper.getInstance().createOkButton(C.buttonSave());

    public F008View() {

        getBodyContainer().add(emotionModelHeading);
        getBodyContainer().add(formHeadTable);
        getBodyContainer().add(emotionsHeading);
        getBodyContainer().add(emotionListView);

        getButtonBar().addRight(saveButton);

        constructForm();
    }

    private void constructForm() {
        int row = 0;
        int column = 0;

        formHeadTable.setWidget(row, column, emotionModelNameField.getLabelWidget());
        column++;
        formHeadTable.setWidget(row, column, emotionModelNameField);

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

        retVal = retVal & ValidationHelper.validateField(emotionModelNameField, container);

        return retVal;
    }

    @Override
    protected void setFormValue(EmotionModel value) {
        value = value != null ? value : newValue();

        emotionModelNameField.setValue(value.getEmm_name());
        emotionListView.setValue(value.getEmotions());

    }

    @Override
    public void getValue(EmotionModel value) {

        value.setEmm_name(emotionModelNameField.getValue());
        value.setEmotions(emotionListView.getValue());
    }

    @Override
    public EmotionModel newValue() {
        return new EmotionModel();
    }

    @Override
    public HasClickHandlers getSaveModelClickSrc() {
        return saveButton;
    }

}
