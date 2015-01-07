package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public abstract class AlcsPopupEditFormPresenter<E> extends AlcsEditFormPresenter<E> {

    public interface AlcsEditFormPopupDisplay<E> extends AlcsEditFormDisplay<E>, HasCloseHandlers<AlcsEditFormPopupDisplay<E>> {

        void showPopup();

        void showPopup(E value);

        void hidePopup(boolean warnIfModified);

        void setPopupCaption(String caption);

        boolean isPopupShowing();
        
        void onRestoreManagerAttached(boolean attached);

    }

    public interface AlcsPopupDisplay<E> extends AlcsEditFormPopupDisplay<E> {

        void showPopup(E value, HasClickHandlers clickEventSource);

    }

    private PopupRestoreManager<E> popupRestoreManager = new PopupRestoreManager<E>();

    public AlcsPopupEditFormPresenter(AlcsEditFormDisplay<E> view) {
        super(view);
        popupRestoreManager.attach(this);
    }

    public void showPopup(final MessageContainer parentMsgPanel, final EventBus eventBus, final E value) {
        final AsyncCallback<FormPrivileges> privilegesCallback = new AsyncCallback<FormPrivileges>() {

            @Override
            public void onFailure(Throwable caught) {
                onStartFailedForPopup(parentMsgPanel, eventBus, caught);
            }

            @Override
            public void onSuccess(FormPrivileges privileges) {
                AlcsEditFormDisplay<E> view = getView();
                view.setPrivileges(privileges);
                if (view instanceof AlcsEditFormPopupDisplay<?>) {
                    AlcsEditFormPopupDisplay<E> tmpView = (AlcsEditFormPopupDisplay<E>) view;
                    tmpView.showPopup(value);
                }
            }
        };
        FormPrivileges.getPrivileges(toProtectedObject(value), privilegesCallback);
    }

    public void showPopup(final MessageContainer parentMsgPanel, final EventBus eventBus, Double valueId) {
        final AsyncCallback<E> valueCallback = new AsyncCallback<E>() {

            @Override
            public void onFailure(Throwable caught) {
                onStartFailedForPopup(parentMsgPanel, eventBus, caught);
            }

            @Override
            public void onSuccess(final E protectedObject) {
                showPopup(parentMsgPanel, eventBus, protectedObject);
            }

        };
        startFindValue(valueId, valueCallback);
    }

    protected void onStartFailedForPopup(MessageContainer messagePanel, EventBus eventBus, Throwable caught) {

        new CommonProgressShowingCallback<Void>(messagePanel) {

            @Override
            protected void call() {
            }
        }.onFailure(caught);
    }

    /**
     * 
     * @param valueCallback
     */
    protected abstract void startFindValue(final Double id, final AsyncCallback<E> valueCallback);

    protected void restorePopup() {
        if (getView() instanceof AlcsEditFormPopupDisplay<?>) {
            AlcsEditFormPopupDisplay<E> tmpView = (AlcsEditFormPopupDisplay<E>) getView();
            tmpView.showPopup();
        }
    }

}
