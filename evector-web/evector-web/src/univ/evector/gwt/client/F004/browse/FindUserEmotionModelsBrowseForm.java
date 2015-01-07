package univ.evector.gwt.client.F004.browse;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.helper.ClickSource;
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
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.gwt.client.F004.resource.FindUserEmotionModelsConstants;
import univ.evector.gwt.client.browse.helper.PagingHelper;
import univ.evector.gwt.client.field.QueryField;
import univ.evector.gwt.client.helper.EvectorButtonHelper;
import univ.evector.gwt.client.helper.EvectorQueryHelper;
import univ.evector.gwt.client.service.UserEmotionsService;
import univ.evector.gwt.client.service.UserEmotionsServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class FindUserEmotionModelsBrowseForm extends TheBrowseForm<FindUserEmotionModels> {

    public final static FindUserEmotionModelsConstants C = GWT.create(FindUserEmotionModelsConstants.class);

    private UserEmotionsServiceAsync userEmotionsService = GWT.create(UserEmotionsService.class);

    private FindUserEmotionModelsTableView tableView = new FindUserEmotionModelsTableView();
    private FindUserEmotionModelsQueryExecutor queryExecutor = new FindUserEmotionModelsQueryExecutor();

    private ColumnMetaData createdColumn = new StringColumnMetaData("emm_created", true).text(C.createdColumn());
    private ColumnMetaData modelNameColumn = new StringColumnMetaData("emm_name", true).text(C.modelNameColumn());

    public final String COL_CREATED_WIDTH = "100px";

    {
        createdColumn.setWidth(COL_CREATED_WIDTH);
    }

    private final QueryField queryField;
    private final Button createNewModelButton = EvectorButtonHelper.getInstance().createAddButton(C.buttonCreate());

    public FindUserEmotionModelsBrowseForm() {
        browseFormPresenter = new BrowseFormPresenter<>(queryExecutor, tableView);
        browseFormPresenter.addColumnMetaData(createdColumn);
        browseFormPresenter.addColumnMetaData(modelNameColumn);

        queryField = EvectorQueryHelper.createQueryField(browseFormPresenter, C.placeholderSearch());

        PagingHelper.addPagingComponent(browseFormPresenter);

        FlowPanel fp = new FlowPanel();
        fp.add(queryField);
        fp.add(createNewModelButton);

        StyleHelper.floatLeft(queryField);
        StyleHelper.floatRight(createNewModelButton);

        getBodyContainer().add(fp);
        getBodyContainer().add(browseFormPresenter.getView());

        setBrowseFormPresenter(browseFormPresenter);

    }

    private class FindUserEmotionModelsTableView extends TableView<FindUserEmotionModels> {

        @Override
        protected Row<FindUserEmotionModels> createRow() {
            return new FindUserEmotionModelsRow();
        }

        @Override
        protected Object getValueKey(FindUserEmotionModels value) {
            return value.getEmm_id();
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);
        }

    }

    private class FindUserEmotionModelsRow extends AbstractRow<FindUserEmotionModels> {

        private DateCell createdCell = new DateCell();
        private TextCell modelNameCell = new TextCell();

        private FindUserEmotionModels value;

        public FindUserEmotionModelsRow() {
            addCell(createdColumn, createdCell);
            addCell(modelNameColumn, modelNameCell);
        }

        @Override
        public FindUserEmotionModels getValue() {
            return value;
        }

        private void setFormValue(FindUserEmotionModels value) {
            value = value != null ? value : newValue();

            createdCell.setValue(value.getEmm_created());
            modelNameCell.setValue(value.getEmm_name());

        }

        private FindUserEmotionModels newValue() {
            return new FindUserEmotionModels();
        }

        @Override
        public void setValue(FindUserEmotionModels value) {
            this.value = value;
            setFormValue(value);
        }
    }

    private class FindUserEmotionModelsQueryExecutor extends BrowseFormQueryExecutor<FindUserEmotionModels> {

        public FindUserEmotionModelsQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<FindUserEmotionModels>> callback) {
            userEmotionsService.findUserEmotionModels(queryMetaData, callback);
        }
    }

    public HasClickHandlers getCreateNewModelClickSrc() {
        return createNewModelButton;
    }

}
