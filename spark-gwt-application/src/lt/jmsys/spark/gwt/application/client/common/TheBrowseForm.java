package lt.jmsys.spark.gwt.application.client.common;

import java.io.Serializable;

import lt.jmsys.spark.gwt.application.client.application.AlcsApplication;
import lt.jmsys.spark.gwt.application.client.common.presenter.AlcsBrowseFormPresenter.AlcsBrowseFormDisplay;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.browse.paging.PagingControl;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TheBrowseForm<T extends Serializable> extends Composite implements AlcsBrowseFormDisplay {

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);
    protected BrowseFormPresenter<T> browseFormPresenter;

    protected VerticalPanel bodyContainer = new VerticalPanel();
    protected MessagePanel messageContainer = new MessagePanel();
    private EventBus browseFormEventBus;
    private HandlerRegistration presenceOfRefreshHandler;

    public TheBrowseForm() {

        bodyContainer.add(messageContainer);
        StyleHelper.setBodyContainer(bodyContainer);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(bodyContainer);
        initWidget(hp);
        AlcsApplication.addDebugName(this);
    }

    public VerticalPanel getBodyContainer() {
        return bodyContainer;
    }

    @Override
    public MessageContainer getMessageContainer() {
        return messageContainer;
    }

    public void setBrowseFormPresenter(BrowseFormPresenter<T> browseFormPresenter) {
        this.browseFormPresenter = browseFormPresenter;
        browseFormPresenter.getView().getTableDisplay().foldQueryControls(false);
    }

    public void setBrowseFormWidth(String width) {
        if (browseFormPresenter != null && browseFormPresenter.getView() != null) {
            browseFormPresenter.getView().asWidget().setWidth(width);
        }
    }

    @Override
    public void refresh() {
        int page = browseFormPresenter.getPagingControl().getCurrentPage();
        if (page == -1) {
            page = 1;
        }
        browseFormPresenter.getQueryExecutor().execute(page);
    }

    @Override
    public void clear() {
        browseFormPresenter.getView().getTableDisplay().setData(null, null);
        PagingControl pagingControl = browseFormPresenter.getPagingControl();
        if (null != pagingControl) {
            pagingControl.setCurrentPage(1, 0);
        }
        if (null != browseFormPresenter.getRowsSelection()) {
            browseFormPresenter.getRowsSelection().deselectAll();
        }
    }

    public void setPageSize(int size) {
        browseFormPresenter.getPagingControl().setPageSize(size);
    }

    public EventBus getBrowseFormEventBus() {
        return browseFormEventBus;
    }

    public void setBrowseFormEventBus(EventBus browseFormEventBus) {
        if (this.browseFormEventBus != null && presenceOfRefreshHandler != null) {
            presenceOfRefreshHandler.removeHandler();
            presenceOfRefreshHandler = null;
        }
        this.browseFormEventBus = browseFormEventBus;
    }

    protected void setShouldRefreshOnChangeEvents(boolean refreshRowChange) {
        if (refreshRowChange) {
            if (browseFormEventBus == null) {
                browseFormEventBus = new SimpleEventBus();
            }
            presenceOfRefreshHandler = browseFormEventBus.addHandler(ValueChangeEvent.getType(), this.refreshFormHandler);
        } else {
            if (browseFormEventBus != null && presenceOfRefreshHandler != null) {
                presenceOfRefreshHandler.removeHandler();
            }
        }
    }

    protected ValueChangeHandler<Object> refreshFormHandler = new ValueChangeHandler<Object>() {

        @Override
        public void onValueChange(ValueChangeEvent<Object> event) {
            refresh();
        }
    };
}
