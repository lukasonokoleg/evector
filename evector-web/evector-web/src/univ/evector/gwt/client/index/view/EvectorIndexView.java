package univ.evector.gwt.client.index.view;

import lt.jmsys.spark.gwt.application.client.application.view.ApplicationView;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import univ.evector.beans.User;
import univ.evector.beans.UserSession;
import univ.evector.beans.helper.UserNameHelper;
import univ.evector.gwt.client.helper.EvectorLayoutHelper;
import univ.evector.gwt.client.index.presenter.EvectorIndexPresenter.EvectorApplicationDisplay;
import univ.evector.gwt.client.index.resource.EvectorIndexConstants;
import univ.evector.gwt.client.session.UserSessionHolder;

import com.google.gwt.cell.client.ButtonCellBase.DefaultAppearance.Style;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class EvectorIndexView extends ApplicationView implements EvectorApplicationDisplay {

    public static final EvectorIndexConstants CONSTANTS = GWT.create(EvectorIndexConstants.class);

    private static final String ID_HEADER = "header";
    private static final String ID_PAGE_CONTENT_HEADER = "pageContentHeader";
    private static final String ID_BODY = "body";
    private static final String ID_FOOTER = "footer";

    private Label labelUserName;

    @Override
    protected void createHeader() {
        logoutLink.setHref("facebook/logout");
        if (labelUserName == null) {
            labelUserName = new Label(CONSTANTS.linkUser());
            labelUserName.setWidth("60px");
        }

        messagesLink.hideIcon();
        messagesLink.setText(CONSTANTS.linkMessages());

        FlexTable userPanel = new FlexTable();

        int row = 0;
        int column = 2;

        userPanel.setWidget(row, column, logoutLink);
        column = 0;
        row++;
        userPanel.setWidget(row, column, labelUserName);
        column++;
        userPanel.setWidget(row, column, userLink);
        userPanel.getFlexCellFormatter().setColSpan(row, column, 2);

        settingsLink.setText(CONSTANTS.linkSettings());
        logoutLink.setText(CONSTANTS.linkLogout());
        helpLink.setText(CONSTANTS.linkLogout());

        StyleHelper.floatRight(userPanel);
        StyleHelper.floatRight(logoutLink);
        StyleHelper.floatLeft(labelUserName);

        RootPanel.get(ID_PAGE_CONTENT_HEADER).add(userPanel);

        panel.add(menu.getFirstLevelMenuBar());
    }

    @Override
    protected void createFooter() {
        EvectorLayoutHelper.addFooter();
    }

    @Override
    public void setSessionInfo(SessionInfo info) {
        UserSession userSession = UserSessionHolder.getUserSession();
        User sessionUser = userSession.getUser();

        String userLinkText = UserNameHelper.getSesUserFullUserName(sessionUser);
        userLink.setText(userLinkText);

    }

}
