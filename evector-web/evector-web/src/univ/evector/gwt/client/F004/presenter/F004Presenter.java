package univ.evector.gwt.client.F004.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceManager;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.F008.presenter.F008Place;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F004Presenter extends BaseFormPresenter<Void, F004Place> {

    public interface F004Display extends FormDisplay<Void> {

        public HasClickHandlers getCreateNewModelClickSrc();

    }

    public F004Presenter(F004Display view) {
        super(view);
        serLocalEvents();
    }

    private void serLocalEvents() {
        getView().getCreateNewModelClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                F008Place nextPlace = new F008Place();
                PlaceManager.goToPopup(nextPlace);
            }
        });
    }

    @Override
    public F004Display getView() {
        return (F004Display) super.getView();
    }

    @Override
    public F004Place getParameters(Place place) {
        return (F004Place) place;
    }

    @Override
    public F004Place getParameters(Void value) {
        return null;
    }

    @Override
    protected void findValue(F004Place parameters, AsyncCallback<Void> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(Void value, F004Place parameters, FormPrivileges privileges, AsyncCallback<Void> callback) {
        getView().setValue(value);
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {

    }

    @Override
    public boolean validate(MessageContainer container, Void value) {
        boolean retVal = true;

        return retVal;
    }

}
