package univ.evector.gwt.client.F008.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.emotion.EmotionModel;
import univ.evector.gwt.client.service.UserEmotionsService;
import univ.evector.gwt.client.service.UserEmotionsServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F008Presenter extends BaseFormPresenter<EmotionModel, F008Place> {

    private UserEmotionsServiceAsync userEmotionService = GWT.create(UserEmotionsService.class);

    public interface F008Display extends FormDisplay<EmotionModel> {

        HasClickHandlers getSaveModelClickSrc();

    }

    public F008Presenter(F008Display view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {
        getView().getSaveModelClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    saveEmotionModel(getView().getValue());
                }
            }
        });
    }

    private void saveEmotionModel(final EmotionModel model) {
        CommonProgressShowingCallback<Void> cb = new CommonProgressShowingCallback<Void>(getMessageContainer()) {

            @Override
            protected void call() {
                userEmotionService.saveUserEmotionModel(model, this);
            }

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);

            }

        };
        cb.perform();
    }

    @Override
    public F008Display getView() {
        return (F008Display) super.getView();
    }

    @Override
    public F008Place getParameters(Place place) {
        return (F008Place) place;
    }

    @Override
    public F008Place getParameters(EmotionModel value) {
        return null;
    }

    @Override
    protected void findValue(F008Place parameters, AsyncCallback<EmotionModel> callback) {
        userEmotionService.findUserEmotionModel(parameters.getIdLong(), callback);
    }

    @Override
    protected void setValue(EmotionModel value, F008Place parameters, FormPrivileges privileges, AsyncCallback<EmotionModel> callback) {
        getView().setValue(value);
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {

    }

    @Override
    public boolean validate(MessageContainer container, EmotionModel value) {
        boolean retVal = getView().validate(container);
        return retVal;
    }

}
