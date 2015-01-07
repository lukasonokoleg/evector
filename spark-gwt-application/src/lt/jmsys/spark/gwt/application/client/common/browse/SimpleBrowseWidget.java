package lt.jmsys.spark.gwt.application.client.common.browse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import lt.jmsys.spark.gwt.application.client.common.TheBrowseForm;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickSource;
import lt.jmsys.spark.gwt.client.ui.browse.cell.AnchorCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.Cell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.DateCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.DecimalCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.LongCell;
import lt.jmsys.spark.gwt.client.ui.browse.cell.TextCell;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.DateColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.DecimalColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.LongColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.Range;
import lt.jmsys.spark.gwt.client.ui.browse.column.StringColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.VoidColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.paging.ListboxPagingComponent;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.browse.query.BrowseFormQueryExecutor;
import lt.jmsys.spark.gwt.client.ui.browse.query.DateRangeQueryInput;
import lt.jmsys.spark.gwt.client.ui.browse.query.DecimalRangeQueryInput;
import lt.jmsys.spark.gwt.client.ui.browse.query.LongQueryInput;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryControl;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryInput;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.client.ui.browse.query.StringQueryInput;
import lt.jmsys.spark.gwt.client.ui.browse.row.AbstractRow;
import lt.jmsys.spark.gwt.client.ui.browse.row.Row;
import lt.jmsys.spark.gwt.client.ui.browse.view.TableView;
import lt.jmsys.spark.gwt.client.ui.css.SparkCssResource;
import lt.jmsys.spark.gwt.client.ui.css.SparkResourcesFactory;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

public abstract class SimpleBrowseWidget<B extends Serializable, K, R extends Enum<R>> extends TheBrowseForm<B> {

    protected static final SparkCssResource CSS = SparkResourcesFactory.create().css();

    @SuppressWarnings("rawtypes")
    protected final List<SimpleBrowseWidgetColumn> columns = new ArrayList<SimpleBrowseWidgetColumn>();
    protected final SimpleBrowseFormTableView tableView;
    protected final ArrayList<SimpleBrowseWidgetColumn> rowSublevelColumns[];
    protected final Class<R> rowSublevels;
    final BrowseFormPresenter<B> presenter;
    boolean columnsAddedToPresenter = false;
    final SimpleBrowseFormRow currentRows[];
    private QueryControl queryControl;
    private BrowseWidgetQueryExecutor<B> queryExecutor;
    private final QueryResult<B> EMPTY_QUERYRESULT = new QueryResult<B>();
    protected final TreeSet<IsColumnGroup> availableGroups = new TreeSet<IsColumnGroup>(new Comparator<IsColumnGroup>() {

        @Override
        public int compare(IsColumnGroup o1, IsColumnGroup o2) {
            return Integer.valueOf(o1.getViewPosition()).compareTo(o2.getViewPosition());
        }

    });
    private final Class<B> beanClass;
    private final ValueClickSource<B> valueClickSource;
    private boolean headerInited;
    protected boolean hasActionColumn;
    protected boolean isActionColumnAddedManually;
    private final boolean generateSummary;

    // Hides parent member
    private Object browseFormPresenter;

    public class StringColumn extends SimpleBrowseWidgetColumn<String, StringColumn, String, R> {

        public StringColumn() {
            super(new StringColumnMetaData(), SimpleBrowseWidget.this);
        }

        @Override
        public Cell<String> createCell() {
            return new TextCell();
        }

        @Override
        protected QueryInput<String> createQueryInput() {
            return new StringQueryInput(columnMetaData);
        }

    }

    public class GenericDateColumn<T extends SimpleBrowseWidgetColumn<Date, T, Range<Date>, R>> extends SimpleBrowseWidgetColumn<Date, T, Range<Date>, R> {

        public GenericDateColumn() {
            super(new DateColumnMetaData(), SimpleBrowseWidget.this);
        }

        @Override
        protected Cell<Date> createCell() {
            return new DateCell();
        }

        @Override
        protected QueryInput<Range<Date>> createQueryInput() {
            return new DateRangeQueryInput(columnMetaData);
        }
    }

    public final class DateColumn extends GenericDateColumn<DateColumn> {

    }

    public class DecimalColumn extends SimpleBrowseWidgetColumn<Double, DecimalColumn, Range<Double>, R> {

        public DecimalColumn() {
            super(new DecimalColumnMetaData(), SimpleBrowseWidget.this);
        }

        @Override
        protected Cell<Double> createCell() {
            return new DecimalCell();
        }

        @Override
        protected QueryInput<Range<Double>> createQueryInput() {
            return new DecimalRangeQueryInput(columnMetaData);
        }

    }

    public class LongColumn extends SimpleBrowseWidgetColumn<Long, LongColumn, Long, R> {

        public LongColumn() {
            super(new LongColumnMetaData(), SimpleBrowseWidget.this);
        }

        @Override
        protected Cell<Long> createCell() {
            return new LongCell();
        }

        @Override
        protected QueryInput<Long> createQueryInput() {
            return new LongQueryInput(columnMetaData);
        }

        @SuppressWarnings("unchecked")
        public void setCurrentRowValue(Number value) {
            if (value != null) {
                setCurrentRowValue(value.longValue());
            }
        }

        @SuppressWarnings("unchecked")
        public void setSummaryValue(Number value) {
            if (value != null) {
                setSummaryValue(value.longValue());
            }
        }

    }

    public abstract class ClickableCellsColumn<C, T extends SimpleBrowseWidgetColumn<C, T, F, R>, F> extends SimpleBrowseWidgetColumn<C, T, F, R> {

        final private ValueClickSource<B> valueClickSource = new ValueClickSource<B>(SimpleBrowseWidget.this.beanClass);

        protected ClickableCellsColumn(ColumnMetaData m) {
            super(m, SimpleBrowseWidget.this);
        }

        @Override
        protected Cell<C> createAndSetupCell() {
            final Cell<C> cell = super.createAndSetupCell();
            addClickHandlerToCell(cell, new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    B value = (B) cell.getRow().getValue();
                    if (null != value) {
                        ValueClickEvent<B> newEvent = new BrowseWidgetValueClickEvent<B, C>(value, valueClickSource.getAssociatedClass(), cell);
                        valueClickSource.fireEvent(newEvent);
                    }
                }
            });
            return cell;
        }

        public void addValueClickHandler(ValueClickHandler<B> handler) {
            valueClickSource.addValueClickEventHandler(handler);
        }

        protected abstract void addClickHandlerToCell(Cell<C> cell, ClickHandler clickHandler);

    }

    public class AnchorColumn extends ClickableCellsColumn<String, AnchorColumn, String> {

        public AnchorColumn(ColumnMetaData m) {
            super(m);
        }

        public AnchorColumn() {
            super(new StringColumnMetaData());
        }

        @Override
        public Cell<String> createCell() {
            final AnchorCell cell = new AnchorCell();
            return cell;
        }

        @Override
        protected QueryInput<String> createQueryInput() {
            return new StringQueryInput(columnMetaData);
        }

        @Override
        protected void addClickHandlerToCell(Cell<String> cell, ClickHandler clickHandler) {
            AnchorCell anchor = (AnchorCell) cell;
            anchor.addClickHandler(clickHandler);
        }

    }

    protected abstract K getValueKey(B value);

    protected abstract void setValue(B value);

    protected void onRowClick(B value, int rowIndex) {

    }

    public SimpleBrowseWidget(Class<B> beanClass, Class<R> rowSublevels) {
        this(beanClass, rowSublevels, false, true);
    }

    public SimpleBrowseWidget(Class<B> beanClass, Class<R> rowSublevels, boolean hasActionColumn, boolean generateSummary) {
        this.generateSummary = generateSummary;
        this.rowSublevels = rowSublevels;
        tableView = new SimpleBrowseFormTableView(rowSublevels.getEnumConstants().length == 0);
        presenter = new BrowseFormPresenter<B>(new SimpleBrowseFormQueryExecutor(), tableView);
        this.hasActionColumn = hasActionColumn;
        currentRows = new SimpleBrowseFormRow[this.rowSublevels.getEnumConstants().length + 1];
        rowSublevelColumns = new ArrayList[currentRows.length];
        for (int i = 0; i < rowSublevelColumns.length; i++) {
            rowSublevelColumns[i] = new ArrayList<SimpleBrowseWidgetColumn>();
        }
        final ListboxPagingComponent paging = new ListboxPagingComponent(null);
        presenter.setPagingControl(paging, null, paging.asWidget());
        this.beanClass = beanClass;
        valueClickSource = new ValueClickSource<B>(beanClass);
        getBodyContainer().add(presenter.getView());
        setBrowseFormPresenter(presenter);
    }

    public IsColumnGroup addColumnsGroup(String name, IsColumnGroup... children) {
        return new ColumnGroup(name, availableGroups, children);
    }

    public int getVisibleColumnCount() {
        return columns.size() + (isActionColumnAddedManually ? 0 : 1);
    }

    public BrowseFormPresenter<B> getBrowseFormPresenter() {
        if (!columnsAddedToPresenter) {
            for (SimpleBrowseWidgetColumn c : columns) {
                c.columnIndex = rowSublevelColumns[c.rowLevel].size();
                rowSublevelColumns[c.rowLevel].add(c);
            }
            ArrayList<IsColumnGroup> listedColumns = new ArrayList<IsColumnGroup>();
            Iterator<IsColumnGroup> i = availableGroups.iterator();
            while (i.hasNext()) {
                i.next().appendElementsTo(listedColumns);
            }
            for (IsColumnGroup g : listedColumns) {
                SimpleBrowseWidgetColumn column = (SimpleBrowseWidgetColumn) g;
                ColumnMetaData cmd = column.getColumnMetaData();
                if (tableView.getActionColumn() != cmd)
                    presenter.addColumnMetaData(cmd);
            }
            if (hasActionColumn)
                tableView.addRealActionColumn();
            columnsAddedToPresenter = true;
        }
        return presenter;
    }

    protected TableView<B> getTableView() {
        return tableView;
    }

    protected boolean getMultiRowsSelect() {
        return false;
    }

    class SimpleBrowseFormTableView extends TableView<B> {

        private ColumnMetaData visibleActionColumn = new VoidColumnMetaData();

        public SimpleBrowseFormTableView(boolean reuseRows) {
            super(SimpleBrowseWidget.this.getMultiRowsSelect());
            setReuseRows(reuseRows);
        }

        @Override
        protected Row<B> createRow() {
            return new SimpleBrowseFormRow();
        }

        @Override
        protected K getValueKey(B value) {
            return SimpleBrowseWidget.this.getValueKey(value);
        }

        @Override
        protected void onClick(int rowIndex) {
            super.onClick(rowIndex);
            B value = tableView.getRow(rowIndex).getValue();
            if (null != value) {
                ValueClickEvent.fire(valueClickSource, value);
            }
        }

        boolean getMultiRowsSelect() {
            return SimpleBrowseWidget.this.getMultiRowsSelect();
        }

        @Override
        public ColumnMetaData getActionColumn() {
            return visibleActionColumn;
        }

        protected void addRealActionColumn() {
            ColumnMetaData tmp = super.getActionColumn();
            copyColumnMetaDate(visibleActionColumn, tmp);
            visibleActionColumn = tmp;
        }

        private void copyColumnMetaDate(ColumnMetaData from, ColumnMetaData to) {
            to.setName(from.getName());
            to.setWidth(from.getWidth());
        }

        FlexTable tableWithSummary;

        @Override
        protected void addSummary(FlexTable table) {
            tableWithSummary = table;
            SimpleBrowseWidget.this.addSummary(table);
        }

        @Override
        public void setData(List<B> data, AsyncCallback<Void> callback) {
            if (tableWithSummary != null && generateSummary) {
                tableWithSummary.removeRow(tableWithSummary.getRowCount() - 1);
            }
            super.setData(data, callback);
        }

        @Override
        public String getCellStyle(int rowIndex, B rowValue, ColumnMetaData column) {
            return SimpleBrowseWidget.this.getCellStyle(rowIndex, rowValue, column);
        }
    }

    public String getCellStyle(int rowIndex, B rowValue, ColumnMetaData column) {
        return null;
    }

    @SuppressWarnings("rawtypes")
    class SimpleBrowseFormRow<_1337> extends AbstractRow<B> {

        ArrayList<Cell> list = new ArrayList<Cell>();
        B value;

        private SimpleBrowseFormRow() {
            this(0);
        }

        private SimpleBrowseFormRow(int subLevel) {
            for (SimpleBrowseWidgetColumn column : SimpleBrowseWidget.this.rowSublevelColumns[subLevel]) {
                final Cell cell = column.createAndSetupCell();
                cell.setRow(this);
                list.add(cell);
                addCell(column.getColumnMetaData(), cell);
            }
        }

        @Override
        public B getValue() {
            return value;
        }

        @Override
        public void setValue(B value) {
            this.value = value;
            clearSubRows();
            try {
                SimpleBrowseWidget.this.currentRows[0] = this;
                createBranch(1);
                SimpleBrowseWidget.this.setValue(value);
            } finally {
                for (int i = 0; i < SimpleBrowseWidget.this.currentRows.length; i++)
                    SimpleBrowseWidget.this.currentRows[i] = null;
            }
        }

        private void createBranch(int fromLevel) {
            SimpleBrowseFormRow rowParent = SimpleBrowseWidget.this.currentRows[fromLevel - 1];
            for (int i = fromLevel; i < SimpleBrowseWidget.this.currentRows.length; i++) {
                SimpleBrowseFormRow subRow = new SimpleBrowseFormRow(i);
                rowParent.addSubRow(subRow);
                SimpleBrowseWidget.this.currentRows[i] = subRow;
                rowParent = subRow;
            }
        }

        ArrayList<Cell> getRowCellList() {
            return list;
        }

    }

    protected void nextSubRowFor(R level) {
        currentRows[0].createBranch(level.ordinal() + 1);
    }

    public void setQueryExecutor(BrowseWidgetQueryExecutor<B> queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    private class SimpleBrowseFormQueryExecutor extends BrowseFormQueryExecutor<B> {

        public SimpleBrowseFormQueryExecutor() {
            super(null);
        }

        @Override
        protected void execute(QueryMetaData queryMetaData, final AsyncCallback<QueryResult<B>> callback) {
            if (queryExecutor != null)
                queryExecutor.execute(queryMetaData, callback);
            else {
                callback.onSuccess(EMPTY_QUERYRESULT);
            }
        }
    }
    
    protected boolean hasFilteredColumns() {
        for (SimpleBrowseWidgetColumn c : columns)
            if (c.isFiltered())
                return true;
        return false;
    }
    
    public QueryControl getQueryControl() {
        return queryControl;
    }

    public void addQueryControl() {
        if (hasFilteredColumns()) {
            queryControl = new SimpleQueryControl();
            presenter.setQueryControl(queryControl);
        }
    }

    public void addValueClickHandler(ValueClickHandler<B> handler) {
        valueClickSource.addValueClickEventHandler(handler);
    }

    public ArrayList<K> getSelectedIds() {
        int[] selected = getBrowseFormPresenter().getRowsSelection().getSelectedIndex();
        ArrayList<K> result = new ArrayList<K>();
        if (null != selected) {
            for (int i : selected) {
                Row<B> row = getBrowseFormPresenter().getRow(i);
                result.add(getValueKey(row.getValue()));
            }
        }
        return result;
    }

    private class SimpleQueryControl implements QueryControl {

        HashMap<ColumnMetaData, QueryInput<?>> inputs = new HashMap<ColumnMetaData, QueryInput<?>>();

        private SimpleQueryControl() {
            for (SimpleBrowseWidgetColumn column : SimpleBrowseWidget.this.columns) {
                if (column.isFiltered())
                    addQueryInput(column.getQueryInput());
            }
        }

        @Override
        public Collection<QueryInput<?>> getQueryInputs() {
            return inputs.values();
        }

        @Override
        public QueryInput<?> getQueryInput(ColumnMetaData columnMeta) {
            return inputs.get(columnMeta);
        }

        @Override
        public void clearQueryInputs() {
            for (QueryInput<?> input : inputs.values()) {
                input.clear();
            }
        }

        @Override
        public void executeQuery() {
            for (Iterator<QueryInput<?>> i = getQueryInputs().iterator(); i.hasNext();) {
                try {
                    i.next().getValue();
                } catch (RuntimeException e) {
                    // invalid user input
                    return;
                }
            }
            presenter.getQueryExecutor().execute(1);
        }

        @Override
        public void addQueryInput(QueryInput<?> queryInput) {
            inputs.put(queryInput.getColumnMetaData(), queryInput);
            queryInput.setQueryControl(this);
        }

        @Override
        public QueryInput<?> getQueryInputObject(ColumnMetaData columnMeta) {
            return getQueryInput(columnMeta);
        }

        @Override
        public String getStringQueryInput(ColumnMetaData columnMeta) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean getBooleanQueryInput(ColumnMetaData columnMeta) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Range<Date> getDateQueryInput(ColumnMetaData columnMeta) {
            throw new UnsupportedOperationException();
        }

    }

    protected FlexTable createGroupedHeaders() {
        FlexTable th = new FlexTable();
        IsColumnGroup tmp[] = new IsColumnGroup[availableGroups.size()];
        tmp = availableGroups.toArray(tmp);
        ColumnGroup cg = new ColumnGroup("", availableGroups, tmp);
        layoutHeaderTree(th, 0, new int[cg.getLayer() - 1], cg.getChildren());
        if (tableView.getMultiRowsSelect()) {
            th.insertCell(0, 0);
            th.getFlexCellFormatter().setRowSpan(0, 0, th.getRowCount());
        }
        tableView.setCustomHeader(th);
        return th;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!headerInited) {
            getBrowseFormPresenter();
            createGroupedHeaders();
            addQueryControl();
            headerInited = true;
        }
    };

    private void layoutHeaderTree(FlexTable flexTable, int row, int columns[], Collection<IsColumnGroup> headerNodes) {
        for (IsColumnGroup current : headerNodes) {
            flexTable.setWidget(row, columns[row], current.getColumnHeaderWidget());
            int currentRowSpan = columns.length + 1 - row - current.getLayer();
            if (currentRowSpan > 1)
                flexTable.getFlexCellFormatter().setRowSpan(row, columns[row], currentRowSpan);
            if (current.getChildren().size() > 0) {
                flexTable.getFlexCellFormatter().setColSpan(row, columns[row], current.getColSpan());
                layoutHeaderTree(flexTable, row + currentRowSpan, columns, current.getChildren());
            }
            columns[row]++;
        }
    }

    protected void addSummary(FlexTable table) {
        if (generateSummary) {
            int lastRow = table.getRowCount();
            table.getRowFormatter().setStyleName(lastRow, "spark-TableComponent-row");
            table.getRowFormatter().addStyleName(lastRow, "spark-TableComponent-cell summaryCell");

            int columnCount = getVisibleColumnCount();
            for (int c = 0; c < columnCount; c++) {
                table.setWidget(lastRow, c, columns.get(c).createSummaryCell().getWidget());
                table.getCellFormatter().addStyleName(lastRow, c, "spark-TableComponent-cell summaryCell");
            }
        }
    }

}
