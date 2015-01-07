package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter;
import lt.jmsys.spark.gwt.application.client.application.resource.MenuConstants;
import univ.evector.gwt.client.index.resource.EvectorMenuConstants;

import com.google.gwt.core.shared.GWT;

public class IndexMenuPresenter extends MenuPresenter {

    public IndexMenuPresenter(MenuDisplay view) {
        super(view);
    }

    @Override
    protected MenuConstants createMenuConstants() {
        return GWT.create(EvectorMenuConstants.class);
    }

}
