package univ.evector.gwt.client.F007.presenter;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.emotion.Emotion;
import univ.evector.gwt.client.service.UserEmotionsService;
import univ.evector.gwt.client.service.UserEmotionsServiceAsync;

public class F007Presenter extends BaseFormPresenter<Emotion, F007Place> {

    private UserEmotionsServiceAsync userEmotionsService = GWT.create(UserEmotionsService.class);

    public interface F007FormDisplay extends FormDisplay<Emotion> {

    }

    public F007Presenter(F007FormDisplay view) {
        super(view);

    }

    @Override
    public F007Place getParameters(Place place) {
        return (F007Place) place;
    }

    @Override
    public F007Place getParameters(Emotion value) {
        return null;
    }

    @Override
    protected void findValue(F007Place parameters, AsyncCallback<Emotion> callback) {
        userEmotionsService.findUserEmotion(parameters.getIdLong(), callback);
    }

    @Override
    protected void setValue(Emotion value, F007Place parameters, FormPrivileges privileges, AsyncCallback<Emotion> callback) {
        getView().setValue(value);
        callback.onSuccess(value);

    }

    @Override
    protected void attachHandlers(EventBus eventBus) {

    }

    @Override
    public boolean validate(MessageContainer container, Emotion value) {
        boolean retVal = true;
        return retVal;
    }

}