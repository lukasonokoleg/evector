package univ.evector.gwt.client.F001.presenter;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;
import univ.evector.gwt.client.F001.resource.F001Constants;
import univ.evector.gwt.client.F006.presenter.F006Place;
import univ.evector.gwt.client.service.BookService;
import univ.evector.gwt.client.service.BookServiceAsync;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceManager;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.shared.event.HasValueClickHandlers;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;

public class F001Presenter extends BaseFormPresenter<Void, F001Place> {

    public final static F001Constants CONSTANTS = GWT.create(F001Constants.class);

    private BookServiceAsync bookService = GWT.create(BookService.class);

    public interface F001Display extends FormDisplay<Void> {

        HasClickHandlers getUploadDocumentsClickSrc();

        HasValueClickHandlers<Long> getConvertDocumentClickSrc();

    }

    public F001Presenter(F001Display view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {
        getView().getUploadDocumentsClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                goToUploadDocumentsForm();
            }
        });
        getView().getConvertDocumentClickSrc().addValueClickEventHandler(new ValueClickHandler<Long>() {

            @Override
            public void onClick(ValueClickEvent<Long> event) {
                convertDocument(event.getValue());
            }
        });
    }

    private void convertDocument(final Long doc_id) {
        CommonProgressShowingCallback<Void> callback = new CommonProgressShowingCallback<Void>(getMessageContainer()) {

            @Override
            protected void call() {
                bookService.convertDocument(doc_id, this);
            }

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                getView().showFeedback(MessageType.SUCCESS, CONSTANTS.successConvertDocument());
            }
        };
        callback.perform();
    }

    private void goToUploadDocumentsForm() {
        F006Place nextPlace = new F006Place();
        PlaceManager.goToPopup(nextPlace);
    }

    @Override
    public F001Display getView() {
        return (F001Display) super.getView();
    }

    @Override
    public F001Place getParameters(Place place) {
        return (F001Place) place;
    }

    @Override
    public F001Place getParameters(Void value) {
        return null;
    }

    @Override
    protected void findValue(F001Place parameters, AsyncCallback<Void> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(Void value, F001Place parameters, FormPrivileges privileges, AsyncCallback<Void> callback) {
        getView().setValue(value);
        callback.onSuccess(null);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validate(MessageContainer container, Void value) {
        boolean valid = true;
        return valid;
    }

}
