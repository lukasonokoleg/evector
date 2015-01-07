package univ.evector.gwt.client.F001.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.application.common.shared.event.HasValueClickHandlers;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickSource;
import lt.jmsys.spark.gwt.client.ui.browse.cell.ButtonCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.DateCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.LongCell;
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
import univ.evector.beans.browse.FindDocuments;
import univ.evector.gwt.client.F001.resource.F001FindDocumentsConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorButtonHelper;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.DocumentService;
import univ.evector.gwt.client.service.DocumentServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class F001FindDocumentsBrowseForm extends TheBrowseForm<FindDocuments> {

    private final static F001FindDocumentsConstants CONSTANTS = GWT.create(F001FindDocumentsConstants.class);

    private DocumentServiceAsync documentService = GWT.create(DocumentService.class);
    private BrowseFormPresenter<FindDocuments> browseFormPresenter;

    private ColumnMetaData docDateColumn = new StringColumnMetaData("doc_date", true).text(CONSTANTS.docDateColumn());
    private ColumnMetaData docNameColumn = new StringColumnMetaData("doc_name", true).text(CONSTANTS.docNameColumn());
    private ColumnMetaData docMimeTypeColumn = new StringColumnMetaData("doc_mime_type", true).text(CONSTANTS.docMimeTypeColumn());
    private ColumnMetaData docSizeColumn = new StringColumnMetaData("doc_size", false).text(CONSTANTS.docSizeColumn());
    private ColumnMetaData actionColumn = new StringColumnMetaData("ation", false).text(CONSTANTS.actionColumn());

    private final Button uploadDocumentButton = EvectorButtonHelper.getInstance().createButton(CONSTANTS.uploadDocumentButton());

    private QueryField queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, CONSTANTS.queryField());;

    private ValueClickSource<Long> actionClickSource = new ValueClickSource<>(Long.class);

    public F001FindDocumentsBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<FindDocuments>(new FindDocumentsQueryExecutor(), new FindDocumentsTableView());
        browseFormPresenter.addColumnMetaData(docDateColumn);
        browseFormPresenter.addColumnMetaData(docNameColumn);
        browseFormPresenter.addColumnMetaData(docMimeTypeColumn);
        browseFormPresenter.addColumnMetaData(docSizeColumn);
        browseFormPresenter.addColumnMetaData(actionColumn);

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);
        fp.add(uploadDocumentButton);

        StyleHelper.floatLeft(queryField);
        StyleHelper.floatRight(uploadDocumentButton);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);

    }

    private class FindDocumentsTableView extends TableView<FindDocuments> {

        @Override
        protected Row<FindDocuments> createRow() {
            return new FindDocumentsRow();
        }

        @Override
        protected Object getValueKey(FindDocuments value) {
            return value.getDoc_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);

        }

    }

    private class FindDocumentsRow extends AbstractRow<FindDocuments> {

        private DateCell docDateCell = new DateCell();
        private TextCell docNameCell = new TextCell();
        private TextCell docMimeTypeCell = new TextCell();
        private LongCell docSizeCell = new LongCell();

        private ButtonCell<Long> actionCell = new ButtonCell<>();

        private FindDocuments value;

        public FindDocumentsRow() {
            addCell(docDateColumn, docDateCell);
            addCell(docNameColumn, docNameCell);
            addCell(docMimeTypeColumn, docMimeTypeCell);
            addCell(docSizeColumn, docSizeCell);
            addCell(actionColumn, actionCell);
            actionCell.setText(CONSTANTS.actionCell());
            actionCell.getClickSource().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    Long docId = null;
                    if (getValue() != null) {
                        docId = getValue().getDoc_id();
                    }
                    ValueClickEvent.fire(actionClickSource, docId);
                }
            });

        }

        @Override
        public FindDocuments getValue() {
            return value;
        }

        @Override
        public void setValue(FindDocuments value) {
            this.value = value;

            docDateCell.setValue(value.getDoc_date());
            docNameCell.setValue(value.getDoc_name());
            docMimeTypeCell.setValue(value.getDoc_mime_type());
            docSizeCell.setValue(value.getDoc_size());

        }
    }

    private class FindDocumentsQueryExecutor extends BrowseFormQueryExecutor<FindDocuments> {

        public FindDocumentsQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindDocuments>> callback) {
            documentService.findDocuments(queryField.getValue(), queryMetaData, callback);
        }
    }

    public void defaultFocus() {
        queryField.setFocus(true);
    }

    public HasClickHandlers getUploadDocumentsClickSrc() {
        return uploadDocumentButton;
    }

    public HasValueClickHandlers<Long> getConvertDocumentClickSrc() {
        return actionClickSource;
    }

}