package univ.evector.gwt.client.F003.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickSource;
import lt.jmsys.spark.gwt.client.ui.browse.cell.ButtonCell;
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
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.gwt.client.F003.resource.FindEmotionalParagraphsConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.ParagraphService;
import univ.evector.gwt.client.service.ParagraphServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

public class F003FindEmotionalParagraphsBrowseForm extends TheBrowseForm<FindEmotionalParagraphs> {

    public final static FindEmotionalParagraphsConstants CONSTANTS = GWT.create(FindEmotionalParagraphsConstants.class);

    private ParagraphServiceAsync paragraphService = GWT.create(ParagraphService.class);
    private BrowseFormPresenter<FindEmotionalParagraphs> browseFormPresenter;

    private ColumnMetaData paragraphSeqColumn = new StringColumnMetaData("prg_seq", true).text(CONSTANTS.paragraphSeqColumn());
    private ColumnMetaData paragraphValueColumn = new StringColumnMetaData("prg_value", true).text(CONSTANTS.paragraphValueColumn());
    private ColumnMetaData emotionalParagraphValueColumn = new StringColumnMetaData("nava_value", false).text(CONSTANTS.emotionalParagraphValueColumn());
    private ColumnMetaData actionColumn = new StringColumnMetaData("ation", false).text(CONSTANTS.actionColumn());
    {
        paragraphSeqColumn.setWidth("20px");

        actionColumn.setWidth("80px");
    }

    private Long bksId;

    private ValueClickSource<Long> actionClickSource = new ValueClickSource<>(Long.class);
    private final QueryField queryField;

    public F003FindEmotionalParagraphsBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<FindEmotionalParagraphs>(new FindEmotionalParagraphsQueryExecutor(), new FindEmotionalParagraphsTableView());
        browseFormPresenter.addColumnMetaData(paragraphSeqColumn);
        browseFormPresenter.addColumnMetaData(paragraphValueColumn);
        browseFormPresenter.addColumnMetaData(emotionalParagraphValueColumn);
        browseFormPresenter.addColumnMetaData(actionColumn);

        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, CONSTANTS.placeholderSearch());

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);

        StyleHelper.floatLeft(queryField);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);
    }

    private class FindEmotionalParagraphsTableView extends TableView<FindEmotionalParagraphs> {

        @Override
        protected Row<FindEmotionalParagraphs> createRow() {
            return new FindEmotionalParagraphsRow();
        }

        @Override
        protected Object getValueKey(FindEmotionalParagraphs value) {
            return value.getPrg_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);

        }

    }

    private class FindEmotionalParagraphsRow extends AbstractRow<FindEmotionalParagraphs> {

        private LongCell paragraphSeqCell = new LongCell();
        private TextCell paragraphValueCell = new TextCell();
        private TextCell emotionalParagraphValueCell = new TextCell();
        private ButtonCell<Long> actionCell = new ButtonCell<>();

        private FindEmotionalParagraphs value;

        public FindEmotionalParagraphsRow() {
            addCell(paragraphSeqColumn, paragraphSeqCell);
            addCell(paragraphValueColumn, paragraphValueCell);
            addCell(emotionalParagraphValueColumn, emotionalParagraphValueCell);
            addCell(actionColumn, actionCell);
            actionCell.setText(CONSTANTS.actionCell());
            actionCell.getClickSource().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    Long prgId = null;
                    if (getValue() != null) {
                        prgId = getValue().getPrg_id();
                    }
                    ValueClickEvent.fire(actionClickSource, prgId);
                }
            });
        }

        @Override
        public FindEmotionalParagraphs getValue() {
            return value;
        }

        private void setFormValue(FindEmotionalParagraphs value) {
            value = value != null ? value : newValue();

            paragraphSeqCell.setValue(value.getPrg_seq());
            paragraphValueCell.setValue(value.getPrg_value());
            emotionalParagraphValueCell.setValue(value.getEmotional_value());

        }

        private FindEmotionalParagraphs newValue() {
            return new FindEmotionalParagraphs();
        }

        @Override
        public void setValue(FindEmotionalParagraphs value) {
            this.value = value;
            setFormValue(value);

        }
    }

    private class FindEmotionalParagraphsQueryExecutor extends BrowseFormQueryExecutor<FindEmotionalParagraphs> {

        public FindEmotionalParagraphsQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindEmotionalParagraphs>> callback) {
            if (bksId == null) {
                browseFormPresenter.setQueryInProgress(false);
            } else {
                paragraphService.findEmotionalParagraphs(bksId, queryField.getValue(), queryMetaData, callback);
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

    public void addValueClickHandler(ValueClickHandler<Long> handler) {
        actionClickSource.addValueClickEventHandler(handler);
    }

}
