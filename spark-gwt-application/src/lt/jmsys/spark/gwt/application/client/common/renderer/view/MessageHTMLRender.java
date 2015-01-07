package lt.jmsys.spark.gwt.application.client.common.renderer.view;

import lt.jmsys.spark.gwt.application.client.common.renderer.resource.MessageCss;
import lt.jmsys.spark.gwt.application.client.common.renderer.resource.MessageCssBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;

public class MessageHTMLRender {

    interface HtmlTemplate extends SafeHtmlTemplates {

        @Template("<div class='{0}'>{1}</div>")
        SafeHtml div(String css, SafeHtml innerHtml);

        @Template("<div >{0}</div>")
        SafeHtml div(SafeHtml innerHtml);

        @Template("<p class='{0}'>{1}</p>")
        SafeHtml p(String css, SafeHtml innerHtml);

        @Template("{0}")
        SafeHtml empty(String value);
    }

    public enum MsgDirType {

        RECEIVED, SENT;
    }

    private static final HtmlTemplate TEMPLATE = GWT.create(HtmlTemplate.class);
    private static final MessageCssBundle MSG_CSS = GWT.create(MessageCssBundle.class);
    static{
        MSG_CSS.css().ensureInjected();
    }

    public static SafeHtml renderMsg(MsgDirType msgDirType, MessageCss css, String addresseAndDate, String msgText) {
        if (null == css){
            css = MSG_CSS.css();
        }
        SafeHtmlBuilder arrow = new SafeHtmlBuilder();
        String arrowTopStyle;
        String arrowBottlmStyle;
        String messageCss;
        switch (msgDirType) {
            case RECEIVED:
                messageCss = css.message() + " " + css.messageReceived();
                arrowTopStyle = css.arrowTop() + " " + css.arrowTopRight();
                arrowBottlmStyle = css.arrowBottom() + " " + css.arrowBottomRight();
                arrow.append(TEMPLATE.div(arrowTopStyle, TEMPLATE.empty("")));
                arrow.append(TEMPLATE.div(arrowBottlmStyle, TEMPLATE.empty("")));
                break;
            case SENT:
                messageCss = css.message() + " " + css.messageSent();
                arrowTopStyle = css.arrowTop() + " " + css.arrowTopLeft();
                arrowBottlmStyle = css.arrowBottom() + " " + css.arrowBottomLeft();
                arrow.append(TEMPLATE.div(arrowTopStyle, TEMPLATE.empty("")));
                arrow.append(TEMPLATE.div(arrowBottlmStyle, TEMPLATE.empty("")));
                break;
            default:
                return TEMPLATE.empty("");

        }

        SafeHtml addresseAndDateSafeHtml = TEMPLATE.p(css.addresseeAndDate(), TEMPLATE.empty(addresseAndDate));
        SafeHtml messageText = TEMPLATE.p(css.messageText(), TEMPLATE.empty(msgText != null ? msgText : ""));

        SafeHtmlBuilder message = new SafeHtmlBuilder();
        message.append(addresseAndDateSafeHtml);
        message.append(messageText);

        SafeHtml msgHtml = TEMPLATE.div(message.toSafeHtml());

        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.append(arrow.toSafeHtml());
        builder.append(msgHtml);

        SafeHtml retVal = TEMPLATE.div(messageCss, builder.toSafeHtml());
        return retVal;
    }

    public static HTML rengerReseivedMsg(MessageCss css, String fromAdressee, String msgText) {
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        return new HTML(builder.toSafeHtml());
    }

}
