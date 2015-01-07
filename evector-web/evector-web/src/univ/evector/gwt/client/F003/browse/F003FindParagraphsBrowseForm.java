package univ.evector.gwt.client.F003.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
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
import univ.evector.beans.browse.FindParagraphs;
import univ.evector.gwt.client.F003.resource.FindParagraphsConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.ParagraphService;
import univ.evector.gwt.client.service.ParagraphServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

public class F003FindParagraphsBrowseForm extends TheBrowseForm<FindParagraphs> {

    public final static FindParagraphsConstants CONSTANTS = GWT.create(FindParagraphsConstants.class);

    private ParagraphServiceAsync paragraphService = GWT.create(ParagraphService.class);
    private BrowseFormPresenter<FindParagraphs> browseFormPresenter;

    private ColumnMetaData paragraphSeqColumn = new StringColumnMetaData("prg_seq", true).text(CONSTANTS.paragraphSeqColumn());
    private ColumnMetaData paragraphValueColumn = new StringColumnMetaData("prg_value", true).text(CONSTANTS.paragraphValueColumn());

    private Long bksId;

    private final QueryField queryField;

    public F003FindParagraphsBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<FindParagraphs>(new FindParagraphsQueryExecutor(), new FindParagraphsTableView());
        browseFormPresenter.addColumnMetaData(paragraphSeqColumn);
        browseFormPresenter.addColumnMetaData(paragraphValueColumn);

        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, CONSTANTS.placeholderSearch());

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);

        StyleHelper.floatLeft(queryField);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);

    }

    public BrowseFormPresenter<FindParagraphs> getBrowseFormPresenter() {
        return browseFormPresenter;
    }

    private class FindParagraphsTableView extends TableView<FindParagraphs> {

        @Override
        protected Row<FindParagraphs> createRow() {
            return new FindParagraphsRow();
        }

        @Override
        protected Object getValueKey(FindParagraphs value) {
            return value.getPrg_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);

        }

    }

    private class FindParagraphsRow extends AbstractRow<FindParagraphs> {

        private LongCell paragraphSeqCell = new LongCell();
        private TextCell paragraphValueCell = new TextCell();

        private FindParagraphs value;

        public FindParagraphsRow() {
            addCell(paragraphSeqColumn, paragraphSeqCell);
            addCell(paragraphValueColumn, paragraphValueCell);
        }

        @Override
        public FindParagraphs getValue() {
            return value;
        }

        private void setFormValue(FindParagraphs value) {
            value = value != null ? value : newValue();

            paragraphSeqCell.setValue(value.getPrg_seq());
            paragraphValueCell.setValue(value.getPrg_value());

        }

        private FindParagraphs newValue() {
            return new FindParagraphs();
        }

        @Override
        public void setValue(FindParagraphs value) {
            this.value = value;
            setFormValue(value);

        }
    }

    private class FindParagraphsQueryExecutor extends BrowseFormQueryExecutor<FindParagraphs> {

        public FindParagraphsQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindParagraphs>> callback) {
            if (bksId == null) {
                browseFormPresenter.setQueryInProgress(false);
            } else {
                paragraphService.findParagraphs(bksId, queryField.getValue(), queryMetaData, callback);
            }
        }
    }

    public void setBookId(Long bksId) {
        this.bksId = bksId;
        refresh();
    }

    public void defaultFocus() {
        queryField.setFocus(true);
    }

}
