package univ.evector.gwt.client.login.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;


public interface LoginText extends ClientBundle {

    @Source("text/LoginContentFirstHtml.txt")
    TextResource loginContentFirst();

    @Source("text/LoginContentSecondHtml.txt")
    TextResource loginContentSecond();

    @Source("text/LoginSecondTabHtml.txt")
    TextResource loginSecondTab();
}