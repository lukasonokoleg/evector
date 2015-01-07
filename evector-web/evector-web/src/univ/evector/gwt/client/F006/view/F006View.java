package univ.evector.gwt.client.F006.view;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.fileupload.client.FileUploadItem;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter;
import univ.evector.beans.Document;
import univ.evector.beans.helper.DocumentHelper;
import univ.evector.gwt.client.F006.presenter.F006Presenter.F006Display;
import univ.evector.gwt.client.F006.resource.F006Constants;
import univ.evector.gwt.client.application.common.client.helper.EvectorFileUploadHelper;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class F006View extends BaseFormView<List<Document>> implements F006Display {

    public final static F006Constants CONSTANTS = GWT.create(F006Constants.class);

    private HTML headingHtml = ThemeHelper.createHeadingH1(CONSTANTS.headingHtml());

    private FlexTable skeleton = new FlexTable();

    private FileUploadPresenter<FileUploadItem> fileUploadPresenter = EvectorFileUploadHelper.createFileUploadPresenter();
    {
        fileUploadPresenter.getView().setEnabledAdvancedMode(false);
    }

    private Button uploadDocumentsButton = EvectorButtonHelper.getInstance().createOkButton(CONSTANTS.uploadDocumentsButton());

    public F006View() {
        setBodyContainer();
        setButtonBar();
        constructView();
    }

    private void constructView() {
        skeleton.clear();

        int row = 0;
        int column = 0;

        skeleton.setWidget(row, column, headingHtml);
        row++;
        skeleton.setWidget(row, column, fileUploadPresenter.getView().asWidget());

    }

    private void setBodyContainer() {
        getBodyContainer().add(skeleton);
    }

    private void setButtonBar() {
        getButtonBar().addLeft(uploadDocumentsButton);
    }

    @Override
    public String getFormCaption() {
        return "";
    }

    @Override
    public void defaultFocus() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        return valid;
    }

    @Override
    protected void setFormValue(List<Document> documents) {
        fileUploadPresenter.clear();
        List<FileUploadItem> items = DocumentHelper.convertToFileUploadItems(documents);
        setFileUploadItems(items);
    }

    private void setFileUploadItems(List<FileUploadItem> items) {
        if (items != null) {
            for (FileUploadItem item : items) {
                if (item != null) {
                    fileUploadPresenter.addItem(item);
                }
            }
        }

    }

    @Override
    public void getValue(List<Document> value) {
        List<FileUploadItem> items = fileUploadPresenter.getItems();
        List<Document> documents = DocumentHelper.convertToDocuments(items);

        value.addAll(documents);
    }

    @Override
    public List<Document> newValue() {
        return new ArrayList<Document>();
    }

    @Override
    public HasClickHandlers getSaveDocumentsClickSrc() {
        return uploadDocumentsButton;
    }

}
