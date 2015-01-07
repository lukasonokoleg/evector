package univ.evector.gwt.client.helper;

import lt.jmsys.spark.gwt.client.helper.ClickSource;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimpleRadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class EvectorGuiHelper {

    public static HasText wrapHasText(String pDocumentId) {
        @SuppressWarnings("deprecation")
        com.google.gwt.user.client.Element element = DOM.getElementById(pDocumentId);
        String nodeName = element.getNodeName();
        // GWT.log("wrapNodeName [" + nodeName + "]");
        if ("INPUT".equals(nodeName)) {
            String type = element.getAttribute("Type");
            // GWT.log("wrapInputType [" + type + "]");
            if ("password".equals(type)) {
                return PasswordTextBox.wrap(element);
            } else if ("radio".equals(type)) {
                return PasswordTextBox.wrap(element);
            } else {
                //
            }

        } else if ("SPAN".equals(nodeName)) {
            return Label.wrap(element);
        } else if ("TEXTAREA".equals(nodeName)) {
            return TextArea.wrap(element);
        }
        return TextBox.wrap(element);
    }

    public static HasValue<Boolean> wrapHasValue(String pDocumentId) {
        @SuppressWarnings("deprecation")
        com.google.gwt.user.client.Element element = DOM.getElementById(pDocumentId);
        return element == null ? null : SimpleRadioButton.wrap(element);
    }

    public static HasClickHandlers wrapClickHandler(String pDocumentId) {
        final ClickSource cs = new ClickSource();
        @SuppressWarnings("deprecation")
        com.google.gwt.user.client.Element element = DOM.getElementById(pDocumentId);
        Event.sinkEvents(element, Event.ONCLICK);
        DOM.setEventListener(element, new EventListener() {

            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONCLICK) {
                    cs.click(null);
                }
            }
        });
        return cs;
    }

    public static String getLocation(String pFile, String pHash) {
        String path = Location.getPath();
        String subpath = path;
        int pos = path.lastIndexOf('/');
        if (pos > -1) {
            subpath = path.substring(0, pos) + pFile;
        }
        String newLocation = Location.getProtocol() + "//" + Location.getHost() + subpath + (pHash == null || pHash.isEmpty() ? "" : "#" + pHash);

        return newLocation;
    }

}
