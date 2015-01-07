package univ.evector.gwt.client.helper;

import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import univ.evector.gwt.client.field.QueryField;

public class EvectorQueryHelper {

    /**
     * Creates query field which triggers browse form search on enter or value change 
     * @param browseFormPresenter
     * @param placeholder
     * @return 
     */
    public static QueryField createQueryField(final BrowseFormPresenter<?> browseFormPresenter, String placeholder) {
        QueryField queryField = new QueryField(placeholder);
        queryField.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
                    browseFormPresenter.getQueryExecutor().execute(1);
                }
            }
        });

        queryField.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                browseFormPresenter.getQueryExecutor().execute(1);
            }
        });

        return queryField;
    }

}
