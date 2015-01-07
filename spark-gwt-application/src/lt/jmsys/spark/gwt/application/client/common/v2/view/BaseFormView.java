package lt.jmsys.spark.gwt.application.client.common.v2.view;

import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.client.view.FormView;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

import eu.itreegroup.spark.application.shared.security.DocumentFunction;
import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public abstract class BaseFormView<T> extends FormView implements FormDisplay<T> {

    private Logger log = Logger.getLogger(getClass().getName());

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    private String unmodifiedValueHash;
    private FormPrivileges privileges;
    private FormDisplayContext context;
    private FeedbackHandler feedbackHandler = createFeedbackHandler();

    private T value;

    public BaseFormView() {
        super(false, false);
    }

    @Override
    public HasValueChangeHandlers<T> getValueChangeSource() {
        return null;
    }

    @Override
    final public void setValue(T value) {
        this.value = value;
        setFormValue(value);
        updateFormItems();
        rememberUnmodifiedValue();
    }

    @Override
    public T getValue() {
        T value = this.value != null ? this.value : newValue();
        getValue(value);
        return value;
    }

    @Override
    public boolean isModified() {
        T newValue = newValue();
        if (null != newValue) {

            if (null == unmodifiedValueHash) {
                log.severe("Illegal state in isModified() - rememberUnmodifiedValue() was not called, it should be called in the end of the setValue() method");
                return false;
            }

            try {
                getValue(newValue);
            } catch (Exception e) {// invalid values entered
                return true;
            }
            String tmpHash = hash(newValue);
            if (!unmodifiedValueHash.equals(tmpHash)) {
                // Window.alert(unmodifiedValueHash.toString() + " / " + newValue.toString());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void setNotModified() {
        rememberUnmodifiedValue();
    }

    protected void rememberUnmodifiedValue() {
        T unmodifiedValue = newValue();
        unmodifiedValueHash = null;
        if (null != unmodifiedValue) {
            try {
                getValue(unmodifiedValue);
                unmodifiedValueHash = hash(unmodifiedValue);
            } catch (Exception e) {
                unmodifiedValue = null;
            }
        }
    }

    protected String hash(T value) {
        if (null != value) {
            return value.toString();// TODO: find better way to compare values.
        } else {
            return null;
        }
    }

    @Override
    public void showSaveFeedback() {
        showSaveFeedback(CC.msgSaveSuccess());
    }

    @Override
    public void showSaveFeedback(String message) {
        showFeedback(MessageType.SUCCESS, message);
    }

    @Override
    public void showFeedback(MessageType type, String message) {
        getFeedbackHandler().showFeedback(type, message);
    }

    @Override
    public void setPrivileges(FormPrivileges privileges) {
        this.privileges = privileges;
    }

    @Override
    public FormPrivileges getPrivileges() {
        return privileges;
    }

    protected boolean hasFunction(DocumentFunction function) {
        return (null != privileges && privileges.hasFunction(function));
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                defaultFocus();
            }
        });
    }

    @Override
    public boolean isButtonsContractSupported() {
        return true;
    }

    public boolean isShownAsPopup() {
        FormDisplayContext context = getContext();
        return null != context && context.isShownAsPopup();
    }

    public FormDisplayContext getContext() {
        return context;
    }

    public void setContext(FormDisplayContext context) {
        this.context = context;
        if (context != null) {
            boolean floating = context.isShownAsPopup() ? MessagePanel.FLOATING_IN_POPUP : MessagePanel.FLOATING_IN_EDIT_FORM;
            getMessageContainer().setMessagesPosition(floating);
        }
    }

    protected void resizeFormContainer() {
        if (null != getContext() && null != getContext().getFormContainer()) {
            boolean floating = context.isShownAsPopup() ? MessagePanel.FLOATING_IN_POPUP : MessagePanel.FLOATING_IN_EDIT_FORM;
            getMessageContainer().setMessagesPosition(floating);
        }
    }

    protected void updateFormItems() {

    }

    protected abstract void setFormValue(T value);

    public abstract void getValue(T value);

    public abstract T newValue();

    protected FeedbackHandler createFeedbackHandler() {
        FeedbackHandler h = GWT.create(FeedbackHandler.class);
        h.init(this);
        return h;
    }

    public FeedbackHandler getFeedbackHandler() {
        return feedbackHandler;
    }

    protected boolean hasEnabledFunction(DocumentFunction function) {
        boolean retVal = false;
        if(getPrivileges()!=null){
            retVal = getPrivileges().hasEnabledFunction(function);
        }
        return retVal;
    }

}
