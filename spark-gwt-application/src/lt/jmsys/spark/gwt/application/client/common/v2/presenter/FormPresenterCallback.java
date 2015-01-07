package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

public interface FormPresenterCallback {

    void closeForm(boolean warnIfModified);
    
    boolean isShownAsPopup();
        
}
