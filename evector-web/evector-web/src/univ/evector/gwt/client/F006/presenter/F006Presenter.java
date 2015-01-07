package univ.evector.gwt.client.F006.presenter;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.Document;
import univ.evector.gwt.client.F006.view.F006View;
import univ.evector.gwt.client.service.DocumentService;
import univ.evector.gwt.client.service.DocumentServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F006Presenter extends BaseFormPresenter<List<Document>, F006Place> {

    private DocumentServiceAsync documentService = GWT.create(DocumentService.class);

    public interface F006Display extends FormDisplay<List<Document>> {

        HasClickHandlers getSaveDocumentsClickSrc();

    }

    public F006Presenter(F006Display view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {
        getView().getSaveDocumentsClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<Document> documents = getView().getValue();
                saveDocuments(documents);
            }
        });
    }

    private void saveDocuments(final List<Document> documents) {
        CommonProgressShowingCallback<Void> callback = new CommonProgressShowingCallback<Void>(getMessageContainer()) {

            @Override
            protected void call() {
                documentService.saveDocuments(documents, this);
            }

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                getView().setValue(new ArrayList<Document>());
                getView().setNotModified();
                getView().showSaveFeedback(F006View.CONSTANTS.msgSaveSuccess());
            }

        };
        callback.perform();
    }

    @Override
    public F006Display getView() {
        return (F006Display) super.getView();
    }

    @Override
    public F006Place getParameters(Place place) {
        return (F006Place) place;
    }

    @Override
    public F006Place getParameters(List<Document> value) {
        return null;
    }

    @Override
    protected void findValue(F006Place parameters, AsyncCallback<List<Document>> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(List<Document> value, F006Place parameters, FormPrivileges privileges, AsyncCallback<List<Document>> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validate(MessageContainer container, List<Document> value) {
        boolean retVal = true;

        return retVal;
    }

}
