package lt.jmsys.spark.gwt.application.client.application.view;

import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter.ApplicationDisplay;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter.BreadCrumbsDisplay;
import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter.MenuDisplay;
import lt.jmsys.spark.gwt.application.client.application.resource.ApplicationConstants;
import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormContainer;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.application.Application;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;
import lt.jmsys.spark.gwt.client.ui.widgets.message.MessageIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationView extends Composite implements ApplicationDisplay {

    private static final ApplicationConstants C = GWT.create(ApplicationConstants.class);

    protected VerticalPanel panel = new VerticalPanel();

    private BaseFormContainer formPlaceholder = new BaseFormContainer();

    private SimplePanel formLegendPanel = new SimplePanel();

    protected Anchor userLink = ButtonHelper.getInstance().createAnchor();

    protected Anchor settingsLink = ButtonHelper.getInstance().createAnchor(C.linkSettings());

    protected Anchor logoutLink = ButtonHelper.getInstance().createLogoutAnchor(C.linkLogout());

    protected Anchor helpLink = ButtonHelper.getInstance().createAnchor(C.linkHelp());

    protected MessageIcon messagesLink = new MessageIcon();


    protected MenuDisplay menu = new MenuView();

    private BreadCrumbsDisplay breadCrumpView = new BreadCrumbsView();

    private VerticalPanel pageContentPanel = new VerticalPanel();

    public ApplicationView() {
        logoutLink.setHref("logout");
        // Create header element
        createHeader();
        createFooter();
        SimplePanel pageContentTop = new SimplePanel();
        pageContentPanel.add(pageContentTop);
        pageContentPanel.add((Widget) Application.getApplication().getMessageContainer());
        pageContentPanel.add(new HTML("<div class=\"spark-loading\" id=\"spark-loading\"></div>"));
        pageContentPanel.add(formPlaceholder);
        pageContentPanel.add(formLegendPanel);
        StyleHelper.setPageContentTop(pageContentPanel);
        StyleHelper.setLegendPanel(formLegendPanel);
        helpLink.setTarget("_blank");
        panel.add(pageContentPanel);
        // Create footer element
        StyleHelper.fullWidth(pageContentPanel);
        StyleHelper.fullWidth(panel);
        initWidget(panel);
    }

    @Override
    public SimplePanel getFormLegendPanel() {
        return formLegendPanel;
    }

    @Override
    public AcceptsOneWidget getFormPlaceholder() {
        return formPlaceholder;
    }

    @Override
    public MenuDisplay getMenuView() {
        return menu;
    }

    @Override
    public BreadCrumbsDisplay getBreadCrumbsView() {
        return breadCrumpView;
    }

    @Override
    public HasClickHandlers getUserLink() {
        return userLink;
    }

    @Override
    public HasClickHandlers getMessagesLink() {
        return messagesLink;
    }

    @Override
    public HasClickHandlers getSettingsLink() {
        return settingsLink;
    }

    @Override
    public HasClickHandlers getLogoutLink() {
        return logoutLink;
    }

    @Override
    public HasClickHandlers getHelpLink() {
        return helpLink;
    }

    @Override
    public void setHelpUrl(String url) {
        helpLink.setHref(url);
        helpLink.setVisible(!ConversionHelper.isEmpty(url));
    }

    @Override
    public void setSessionInfo(final SessionInfo info) {
        userLink.setText(info.getFirstName() + " " + info.getLastName());
    }

    @Override
    public void setUnreadMessagesCount(int unreadMessages) {
        if (messagesLink instanceof MessageIcon) {
            ((MessageIcon) messagesLink).setNewMessagesCount(unreadMessages);
        }
    }

    /**
     * Create application footer view.</br>
     * Override this method for custom application footer creation.
     */
    protected void createFooter() {
        FooterView footer = new FooterView();
        RootPanel.get("pageFooter").add(footer);
    }

    /**
     * Create application header view.</br>
     * Override this method for custom application header creation.
     */
    protected void createHeader() {
        FlowPanel userPanel = new FlowPanel();
        userPanel.add(new Label(C.linkUser() + ":"));
        userPanel.add(userLink);
        FlowPanel rightMenu = new FlowPanel();
        rightMenu.add(userPanel);
        rightMenu.add(messagesLink);
        rightMenu.add(settingsLink);
        rightMenu.add(logoutLink);
        rightMenu.add(helpLink);
        StyleHelper.floatRight(rightMenu);
        HeaderView header = new HeaderView();
        header.add(rightMenu);
        header.add(menu.getFirstLevelMenuBar());
        header.add(menu.getSecondLevelMenuBar());
        StyleHelper.fullWidth(header);
        panel.insert(header, 0);
    }
}
