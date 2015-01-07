package univ.evector.gwt.client.F005.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.browse.cell.DateCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.TextCell;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.StringColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.browse.query.BrowseFormQueryExecutor;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.client.ui.browse.row.AbstractRow;
import lt.jmsys.spark.gwt.client.ui.browse.row.Row;
import lt.jmsys.spark.gwt.client.ui.browse.view.TableView;
import lt.jmsys.spark.gwt.client.ui.form.field.DateField;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.facebook.FindFacebookPost;
import univ.evector.gwt.client.F005.bean.F005Query;
import univ.evector.gwt.client.F005.resource.FindFacebookPostConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorButtonHelper;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.FacebookService;
import univ.evector.gwt.client.service.FacebookServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FindFacebookPostBrowseForm extends TheBrowseForm<FindFacebookPost> {

    private final static String CREATED_COL_WIDTH = "100px";

    private FacebookServiceAsync facebookService = GWT.create(FacebookService.class);

    public final static FindFacebookPostConstants C = GWT.create(FindFacebookPostConstants.class);

    private BrowseFormPresenter<FindFacebookPost> browseFormPresenter;

    private ColumnMetaData createdColumn = new StringColumnMetaData("created", true).text(C.colCreated());
    private ColumnMetaData messageColumn = new StringColumnMetaData("message", true).text(C.colMessage());
    {
        createdColumn.setWidth(CREATED_COL_WIDTH);
    }

    /*    private final QueryField queryField;*/
    private final DateField sinceDateField;
    private final DateField untilDateField;

    private Button searchButton = EvectorButtonHelper.getInstance().createButton(C.buttonSearch(), C.buttonSearchTitle());
    {
        searchButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                refresh();
            }
        });
    }

    public FindFacebookPostBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<FindFacebookPost>(new FindFacebookPostQueryExecutor(), new FindFacebookPostTableView());
        browseFormPresenter.addColumnMetaData(createdColumn);
        browseFormPresenter.addColumnMetaData(messageColumn);

        /*        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, C.placeholderSearch());*/
        sinceDateField = new DateField(C.labelSinceDateField());
        untilDateField = new DateField(C.labelUntilDateField());

        /*        StyleHelper.floatLeft(queryField);*/

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlexTable fp = new FlexTable();
        int row = 0;
        int column = 1;
        /*        fp.setWidget(row, column, queryField);
                fp.getFlexCellFormatter().setColSpan(row, column, 2);*/

        row++;
        column = 0;

        fp.setWidget(row, column, sinceDateField.getLabelWidget());
        column++;
        fp.setWidget(row, column, sinceDateField);

        row++;
        column = 0;

        fp.setWidget(row, column, untilDateField.getLabelWidget());
        column++;
        fp.setWidget(row, column, untilDateField);

        row++;
        column = 1;

        fp.setWidget(row, column, searchButton);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);
    }

    public BrowseFormPresenter<FindFacebookPost> getBrowseFormPresenter() {
        return browseFormPresenter;
    }

    private class FindFacebookPostTableView extends TableView<FindFacebookPost> {

        @Override
        protected Row<FindFacebookPost> createRow() {
            return new FindFacebookPostRow();
        }

        @Override
        protected Object getValueKey(FindFacebookPost value) {

            return value.getId();

        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);

        }

    }

    private class FindFacebookPostRow extends AbstractRow<FindFacebookPost> {

        private TextCell messageCell = new TextCell();
        private DateCell createdCell = new DateCell();

        private FindFacebookPost value;

        public FindFacebookPostRow() {
            addCell(createdColumn, createdCell);
            addCell(messageColumn, messageCell);

        }

        @Override
        public FindFacebookPost getValue() {
            return value;
        }

        private void setFormValue(FindFacebookPost value) {
            value = value != null ? value : newValue();
            messageCell.setValue(value.getPost());
            createdCell.setValue(value.getPostDate());
        }

        private FindFacebookPost newValue() {
            return new FindFacebookPost();
        }

        @Override
        public void setValue(FindFacebookPost value) {
            this.value = value;
            setFormValue(value);
        }
    }

    private class FindFacebookPostQueryExecutor extends BrowseFormQueryExecutor<FindFacebookPost> {

        public FindFacebookPostQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindFacebookPost>> callback) {
            F005Query query = new F005Query();
            query.setSearchText(null);
            query.setSinceDate(sinceDateField.getValue());
            query.setUntilDate(untilDateField.getValue());

            facebookService.findDocuments(query, queryMetaData, callback);
        }
    }

    public void defaultFocus() {
/*        queryField.setFocus(true);*/
    }

}
