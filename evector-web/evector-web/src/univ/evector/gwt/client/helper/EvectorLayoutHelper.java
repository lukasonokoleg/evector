package univ.evector.gwt.client.helper;

import univ.evector.gwt.client.index.resource.EvectorIndexConstants;
import univ.evector.gwt.client.index.view.EvectorIndexView;
import univ.evector.gwt.client.login.resource.LoginText;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class EvectorLayoutHelper {

    private static final EvectorIndexConstants CONSTANTS = EvectorIndexView.CONSTANTS;

    private static final String ID_LOGO = "logo";

    public static void addHeader() {

    }

    public static void addFooter() {

    }

    public static void addLoginContent() {
        final LoginText loginText = GWT.create(LoginText.class);

        HTML text1 = new HTML(loginText.loginContentFirst().getText());
        text1.setStyleName("loginText");
        RootPanel loginTextFirst = RootPanel.get("loginTextFirst");
        loginTextFirst.add(text1);
        loginTextFirst.setVisible(true);

        HTML text2 = new HTML(loginText.loginContentSecond().getText());
        text2.setStyleName("loginText");
        RootPanel loginTextSecond = RootPanel.get("loginTextSecond");
        loginTextSecond.add(text2);
        loginTextSecond.setVisible(true);

        Anchor homeLink = new Anchor();
        homeLink.setHref("https://www.vv.lt/");
        homeLink.setStyleName("homeLink");
        RootPanel homeLinkContainer = RootPanel.get("imageHomeLink");
        homeLinkContainer.add(homeLink);
        homeLinkContainer.setVisible(true);
    }

    public static void removeLoginContent() {
        RootPanel loginTextFirst = RootPanel.get("loginTextFirst");
        loginTextFirst.clear();
        loginTextFirst.setVisible(false);

        RootPanel loginTextSecond = RootPanel.get("loginTextSecond");
        loginTextSecond.clear();
        loginTextSecond.setVisible(false);

        RootPanel homeLinkContainer = RootPanel.get("imageHomeLink");
        homeLinkContainer.clear();
        homeLinkContainer.setVisible(false);
    }
}
