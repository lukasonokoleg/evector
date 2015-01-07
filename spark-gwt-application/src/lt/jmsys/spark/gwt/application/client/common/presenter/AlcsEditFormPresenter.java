package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmHelper;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.form.presenter.EditFormPresenter;
import lt.jmsys.spark.gwt.client.ui.form.view.EditFormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public abstract class AlcsEditFormPresenter<E> extends AbstractActivity implements EditFormPresenter<E> {

    public static final CommonConstants C = GWT.create(CommonConstants.class);

    public interface AlcsEditFormDisplay<E> extends EditFormDisplay<E> {

        MessageContainer getMessageContainer();

        HasClickHandlers getSaveClickSource();

        HasClickHandlers getCancelClickSource();

        HasClickHandlers getBackClickSource();

        boolean isModified();
        
        void rememberUnmodifiedValue();

        void defaultFocus();

        void showForm(AcceptsOneWidget panel, E value);

        void showForm(AcceptsOneWidget panel);

        void showSaveFeedback();

        void showSaveFeedback(String message);

        void setPrivileges(FormPrivileges privileges);
    }

    private AlcsEditFormDisplay<E> view;

    private boolean stopConfirmed;

    private StateManager<E> stateManager = new StateManager<E>();

    public AlcsEditFormPresenter(AlcsEditFormDisplay<E> view) {
        this.view = view;
        view.getBackClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                back();
            }
        });
    }

    public AlcsEditFormDisplay<E> getView() {
        return view;
    }

    public final boolean validate() {
        MessageContainer container = getView().getMessageContainer();
        if (null != container) {
            container.clear();
        }
        boolean valid = getView().validate(container);
        if (valid) {
            valid = validate(container, getView().getValue());
        }
        container.show();
        return valid;
    }

    protected void back() {
        String message = mayStop();
        if (null == message) {
            History.back();
        } else {
            ConfirmHelper.confirm(message, new Command() {

                @Override
                public void execute() {
                    confirmStop();
                    History.back();
                }
            });
        }
    }

    protected void confirmStop() {
        stopConfirmed = true;
    }

    @Override
    public String mayStop() {
        if (!stopConfirmed && getView().isModified()) {
            return C.warnUnsavedChanges();
        } else {
            return null;
        }
    }

    @Override
    public void onStop() {
        stopConfirmed = false;
    }

    protected E getValueFromState() {
        E value = null;
        Place where = ClientFactory.getInstance().getPlaceController().getWhere();
        if (where instanceof PlaceWithId) {
            String id = ((PlaceWithId) where).getStateId();
            State<E> state = stateManager.getState(id);
            if (null != state) {
                value = state.getItem();
            }
        }
        return value;
    }

    protected void setValueToState(Double valueId, E value) {
        Place where = ClientFactory.getInstance().getPlaceController().getWhere();
        if (where instanceof PlaceWithId) {
            String stateId = ((PlaceWithId) where).getStateId();
            stateManager.updateState(stateId, ConversionHelper.toIntegerString(valueId), value);
        }
    }

    protected Object toProtectedObject(E value) {
        return value;
    }

}
