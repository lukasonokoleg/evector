package univ.evector.gwt.client.F004.browse;

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
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.gwt.client.F004.resource.FindUserEmotionsConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorButtonHelper;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.UserEmotionsService;
import univ.evector.gwt.client.service.UserEmotionsServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class FindUserEmotionsBrowseForm extends TheBrowseForm<FindUserEmotions> {

    public final static FindUserEmotionsConstants C = GWT.create(FindUserEmotionsConstants.class);

    private UserEmotionsServiceAsync userEmotionsService = GWT.create(UserEmotionsService.class);

    private FindUserEmotionsTableView tableView = new FindUserEmotionsTableView();
    private FindUserEmotionsQueryExecutor queryExecutor = new FindUserEmotionsQueryExecutor();

    private ColumnMetaData nameColumn = new StringColumnMetaData("emo_name", true).text(C.nameColumn());
    private ColumnMetaData emotionWordsColumn = new StringColumnMetaData("words", true).text(C.wordsColumn());

    private final QueryField queryField;
    private final Button createButton = EvectorButtonHelper.getInstance().createAddButton(C.buttonCreate());

    public FindUserEmotionsBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<>(queryExecutor, tableView);
        browseFormPresenter.addColumnMetaData(nameColumn);
        browseFormPresenter.addColumnMetaData(emotionWordsColumn);

        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, C.placeholderSearch());

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);
        fp.add(createButton);

        StyleHelper.floatLeft(queryField);
        StyleHelper.floatRight(createButton);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);

    }

    private class FindUserEmotionsTableView extends TableView<FindUserEmotions> {

        @Override
        protected Row<FindUserEmotions> createRow() {
            return new FindUserEmotionsRow();
        }

        @Override
        protected Object getValueKey(FindUserEmotions value) {
            return value.getEmo_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);
        }

    }

    private class FindUserEmotionsRow extends AbstractRow<FindUserEmotions> {

        private TextCell nameCell = new TextCell();
        private TextCell emotionWordsCell = new TextCell();

        private FindUserEmotions value;

        public FindUserEmotionsRow() {
            addCell(nameColumn, nameCell);
            addCell(emotionWordsColumn, emotionWordsCell);
        }

        @Override
        public FindUserEmotions getValue() {
            return value;
        }

        private void setFormValue(FindUserEmotions value) {
            value = value != null ? value : newValue();

            nameCell.setValue(value.getEmo_name());

        }

        private FindUserEmotions newValue() {
            return new FindUserEmotions();
        }

        @Override
        public void setValue(FindUserEmotions value) {
            this.value = value;
            setFormValue(value);
        }
    }

    private class FindUserEmotionsQueryExecutor extends BrowseFormQueryExecutor<FindUserEmotions> {

        public FindUserEmotionsQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindUserEmotions>> callback) {
            userEmotionsService.findUserEmotions(queryMetaData, callback);
        }
    }

}