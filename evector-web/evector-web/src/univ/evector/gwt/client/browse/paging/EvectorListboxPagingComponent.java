package univ.evector.gwt.client.browse.paging;

import lt.jmsys.spark.gwt.client.ui.browse.paging.ListboxPagingComponent;
import lt.jmsys.spark.gwt.client.ui.browse.table.TableControl;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EvectorListboxPagingComponent extends ListboxPagingComponent {

    private final static int MINIMUM_RECORDS_QTY = 20;

    private SimplePanel pagingPlace = new SimplePanel();

    public EvectorListboxPagingComponent() {
        this(null);
        super.setPageSize(MINIMUM_RECORDS_QTY);
    }

    public EvectorListboxPagingComponent(TableControl<?> pageControl) {
        super(pageControl);
    }

    @Override
    protected void addPageSizeLinks(HorizontalPanel p) {
        super.addPageSizeLinks(p);
        p.setVisible(true);
    }

    @Override
    protected void addPageSizeLabel(HorizontalPanel p) {

    }

    @Override
    public Widget asWidget() {
        pagingPlace.setWidget(super.asWidget());
        pagingPlace.setVisible(false);
        return pagingPlace;
    }

    @Override
    public void setCurrentPage(int currentPage, int totalRecords) {
        if (MINIMUM_RECORDS_QTY < totalRecords) {
            super.setCurrentPage(currentPage, totalRecords);
            pagingPlace.setVisible(true);
        } else {
            super.setCurrentPage(1, totalRecords);
            pagingPlace.setVisible(false);
        }

    }
}
