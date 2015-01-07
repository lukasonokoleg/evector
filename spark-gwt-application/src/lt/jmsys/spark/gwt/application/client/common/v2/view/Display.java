package lt.jmsys.spark.gwt.application.client.common.v2.view;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.IsModifyAble;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.Validator;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Interface for widget used to display or edit bean. 
 *
 * @param <T> - bean type
 */
public interface Display<T> extends TakesValue<T>, IsWidget, IsModifyAble, Validator {

    HasValueChangeHandlers<T> getValueChangeSource();

    void defaultFocus();

}
