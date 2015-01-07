package univ.evector.gwt.client.browse.helper;

import lt.jmsys.spark.gwt.client.ui.browse.paging.BeforePagingEvent;
import lt.jmsys.spark.gwt.client.ui.browse.paging.BeforePagingEvent.Handler;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import univ.evector.gwt.client.browse.paging.EvectorListboxPagingComponent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class PagingHelper {

    public interface PageChangeValidator {

        void validatePageChange(BeforePagingEvent event, AsyncCallback<Boolean> callback);
    }

    public static void noPaging(BrowseFormPresenter<?> browseFormPresenter) {
        EvectorListboxPagingComponent paging = new EvectorListboxPagingComponent();
        paging.setPageSize(Integer.MAX_VALUE);
        browseFormPresenter.setPagingControl(paging, null, null);
    }

    public static void addPagingComponent(BrowseFormPresenter<?> browseFormPresenter) {
        EvectorListboxPagingComponent paging = new EvectorListboxPagingComponent();
        browseFormPresenter.setPagingControl(paging, null, paging.asWidget());
    }

    public static void addPagingComponent(final BrowseFormPresenter<?> browseFormPresenter, final PageChangeValidator pageChangeValidator) {
        final EvectorListboxPagingComponent paging = new EvectorListboxPagingComponent() {

            protected boolean beforePageChange() {
                return false;
            }
        };
        paging.addBeforePagingHandler(new Handler() {

            @Override
            public void onBeforePageOrPageSizeChanged(final BeforePagingEvent event) {
                pageChangeValidator.validatePageChange(event, new AsyncCallback<Boolean>() {

                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Boolean valid) {
                        if (valid) {
                            paging.setPageSize(event.getPageSize());
                            browseFormPresenter.getQueryExecutor().execute(event.getCurrentPage());
                        }
                    }
                });
            }
        });
        browseFormPresenter.setPagingControl(paging, null, paging.asWidget());
    }

}
