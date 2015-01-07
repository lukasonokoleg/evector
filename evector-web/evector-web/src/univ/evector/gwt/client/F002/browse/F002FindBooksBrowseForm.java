package univ.evector.gwt.client.F002.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.browse.cell.TextCell;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.StringColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.browse.query.BrowseFormQueryExecutor;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.client.ui.browse.row.AbstractRow;
import lt.jmsys.spark.gwt.client.ui.browse.row.Row;
import lt.jmsys.spark.gwt.client.ui.browse.view.TableView;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindBooks;
import univ.evector.gwt.client.F002.resource.F002FindBooksConstants;
import univ.evector.gwt.client.browse.cell.AuthorCell;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.BookService;
import univ.evector.gwt.client.service.BookServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

public class F002FindBooksBrowseForm extends TheBrowseForm<FindBooks> {

    private final static F002FindBooksConstants CONSTANTS = GWT.create(F002FindBooksConstants.class);

    private BookServiceAsync bookService = GWT.create(BookService.class);
    private BrowseFormPresenter<FindBooks> browseFormPresenter;

    private ColumnMetaData bookTitleColumn = new StringColumnMetaData("bks_title", true).text(CONSTANTS.bookTitleColumn());
    private ColumnMetaData bookIsbnColumn = new StringColumnMetaData("bks_isbn", true).text(CONSTANTS.bookIsbnColumn());
    private ColumnMetaData authorColumn = new StringColumnMetaData("author", false).text(CONSTANTS.authorColumn());

    private final QueryField queryField;

    public F002FindBooksBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<FindBooks>(new FindBooksQueryExecutor(), new FindBooksTableView());
        browseFormPresenter.addColumnMetaData(bookTitleColumn);
        browseFormPresenter.addColumnMetaData(bookIsbnColumn);
        browseFormPresenter.addColumnMetaData(authorColumn);

        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, CONSTANTS.placeholderSearch());

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);

        StyleHelper.floatLeft(queryField);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);

    }

    public BrowseFormPresenter<FindBooks> getBrowseFormPresenter() {
        return browseFormPresenter;
    }

    private class FindBooksTableView extends TableView<FindBooks> {

        @Override
        protected Row<FindBooks> createRow() {
            return new FindBooksRow();
        }

        @Override
        protected Object getValueKey(FindBooks value) {
            return value.getBks_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);

        }

    }

    private class FindBooksRow extends AbstractRow<FindBooks> {

        private TextCell bookTitleCell = new TextCell();
        private TextCell bookIsbnCell = new TextCell();
        private AuthorCell authorCell = new AuthorCell();
        private FindBooks value;

        public FindBooksRow() {
            addCell(bookTitleColumn, bookTitleCell);
            addCell(bookIsbnColumn, bookIsbnCell);
            addCell(authorColumn, authorCell);

        }

        @Override
        public FindBooks getValue() {
            return value;
        }

        private void setFormValue(FindBooks value) {
            value = value != null ? value : newValue();

            bookTitleCell.setValue(value.getBks_title());
            bookIsbnCell.setValue(value.getBks_isbn());
            authorCell.setValue(value.getAuthor());

            
        }

        private FindBooks newValue() {
            return new FindBooks();
        }

        @Override
        public void setValue(FindBooks value) {
            this.value = value;
            setFormValue(value);

        }
    }

    private class FindBooksQueryExecutor extends BrowseFormQueryExecutor<FindBooks> {

        public FindBooksQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindBooks>> callback) {
            bookService.findBooks(null, queryMetaData, callback);
        }
    }

    public void defaultFocus() {
        queryField.setFocus(true);
    }

}
