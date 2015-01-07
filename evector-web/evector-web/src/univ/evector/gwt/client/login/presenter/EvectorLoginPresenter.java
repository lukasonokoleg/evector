package univ.evector.gwt.client.login.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import univ.evector.gwt.client.L001.presenter.L001Place;

public class EvectorLoginPresenter extends ApplicationPresenter {

    public interface EvectorLoginDisplay extends ApplicationDisplay {

    }

    public EvectorLoginPresenter(ClientFactory factory, ApplicationDisplay view) {
        super(factory, view);
    }

    
    
    @Override
    public void preloadSessionData(AsyncCallback<PreloadedSessionData> callback, boolean afterRelogin) {
        callback.onSuccess(null);
    }

    @Override
    public ModulePlace getFirstPlace() {
        return new L001Place();
    }

}
