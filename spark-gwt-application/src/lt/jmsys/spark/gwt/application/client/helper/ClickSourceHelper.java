package lt.jmsys.spark.gwt.application.client.helper;

import lt.jmsys.spark.gwt.client.helper.ClickSource;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

public class ClickSourceHelper {

    public static ClickSource getFocusClickSource(final TextField field) {
        final ClickSource source = new ClickSource();
        field.addFocusHandler(new FocusHandler() {

            @Override
            public void onFocus(FocusEvent event) {
                if (!field.isReadOnly()) {
                    source.click(null);
                }
            }
        });

        field.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                source.click(null);
            }
        });

        return source;
    }

    public static ClickSource createAgregatedClickSource(HasClickHandlers... clickSources) {
        final ClickSource s = new ClickSource();
        final ClickHandler h = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                s.click(event.getNativeEvent());
            }
        };
        for (HasClickHandlers c : clickSources) {
            c.addClickHandler(h);
        }

        return s;
    }

    public static ClickSource createAgregatedClickSource(HasClickHandlers clickSource, HasKeyDownHandlers field) {
        final ClickSource s = new ClickSource();
        final ClickHandler h = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                s.click(event.getNativeEvent());
            }
        };
        clickSource.addClickHandler(h);

        field.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    s.click(null);
                }
            }
        });

        return s;
    }

}
