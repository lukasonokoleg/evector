package univ.evector.gwt.client.login;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import univ.evector.gwt.client.login.presenter.EvectorLoginPresenter;
import univ.evector.gwt.client.login.view.EvectorLoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class LoginClientFactory extends ClientFactory {

    @Override
    protected PlaceHistoryMapper createHistoryMapper() {
        return GWT.create(LoginHistoryMapper.class);
    }

    @Override
    protected ApplicationPresenter createApplicationPresenter() {
        return new EvectorLoginPresenter(this, new EvectorLoginView());
    }

}