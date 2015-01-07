package univ.evector.gwt.client.login.view;

import lt.jmsys.spark.gwt.application.client.application.view.ApplicationView;
import univ.evector.gwt.client.login.presenter.EvectorLoginPresenter.EvectorLoginDisplay;
import univ.evector.gwt.client.login.resource.EvectorLoginConstants;

import com.google.gwt.core.client.GWT;

public class EvectorLoginView extends ApplicationView implements EvectorLoginDisplay {

    private static final EvectorLoginConstants CONSTANTS = GWT.create(EvectorLoginConstants.class);

    public static final String MSG_PARAM_WELCOME = "welcome";

    private static final String ID_HEADER = "header";
    private static final String ID_FOOTER = "footer";

    public EvectorLoginView() {
    }

    @Override
    protected void createHeader() {
        /*  Label headerLabel = new Label("ID_HEADER");
          RootPanel.get(ID_HEADER).add(headerLabel);*/
    }

    @Override
    protected void createFooter() {
        /*        Label footerLabel = new Label("ID_FOOTER");
                RootPanel.get(ID_FOOTER).add(footerLabel);*/
    }

}
