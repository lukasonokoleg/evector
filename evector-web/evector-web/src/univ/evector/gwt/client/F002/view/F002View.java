package univ.evector.gwt.client.F002.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.F002.browse.F002FindBooksBrowseForm;
import univ.evector.gwt.client.F002.presenter.F002Presenter.F002Display;
import univ.evector.gwt.client.F002.resource.F002Constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class F002View extends BaseFormView<Void> implements F002Display {

    private final static F002Constants CONSTANTS = GWT.create(F002Constants.class);

    private FlexTable skeleton = new FlexTable();

    private HTML captionHtml = ThemeHelper.createHeadingH1(CONSTANTS.caption());

    private F002FindBooksBrowseForm findBooksBrowseForm = new F002FindBooksBrowseForm();

    public F002View() {
        getBodyContainer().add(skeleton);

        constructBody();
    }

    private void constructBody() {

        int row = 0;
        int column = 0;

        skeleton.setWidget(row, column, captionHtml);

        row++;

        skeleton.setWidget(row, column, findBooksBrowseForm);

    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    @Override
    public String getFormCaption() {
        return CONSTANTS.caption();
    }

    @Override
    public void defaultFocus() {
        findBooksBrowseForm.defaultFocus();
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        return valid;
    }

    @Override
    protected void setFormValue(Void value) {
        findBooksBrowseForm.refresh();
    }

    @Override
    public void getValue(Void value) {

    }

    @Override
    public Void newValue() {
        return null;
    }

}
