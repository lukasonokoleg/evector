package lt.jmsys.spark.gwt.application.client.html;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;

public class HtmlHelper {

    public interface HtmlTemplate extends SafeHtmlTemplates {

        @Template("<pre class=\"{0}\">{1}</pre>")
        SafeHtml pre(String className, String text);

    };

    private static final HtmlTemplate TEMPLATE = GWT.create(HtmlTemplate.class);

    private static HTML getHtml(SafeHtml safeHtml) {
        return new HTML(safeHtml);
    }

    public static HTML preAsWidget(String className, String text) {
        HTML retVal = getHtml(preAsSafeHtml(className, text));
        return retVal;
    }

    public static SafeHtml preAsSafeHtml(String className, String text) {
        SafeHtml retVal = TEMPLATE.pre(className, text);
        return retVal;
    }
}