package lt.jmsys.spark.gwt.application.client.common.browse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import lt.jmsys.spark.gwt.client.ui.browse.cell.Cell;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryInput;

import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;

public abstract class SimpleBrowseWidgetColumn<C, T extends SimpleBrowseWidgetColumn<C, T, F, R>, F, R extends Enum<R>> implements IsColumnGroup {

    private final SimpleBrowseWidget simpleBrowseWidget;
    protected final ColumnMetaData columnMetaData;
    protected int columnIndex;
    protected int rowLevel;
    protected int viewPosition;
    protected boolean hidden;
    private QueryInput<F> queryInput;
    boolean filtered = true;
    private C summaryValue;

    private HorizontalAlignmentConstant horizontalAlignment;

    protected SimpleBrowseWidgetColumn(ColumnMetaData m, SimpleBrowseWidget widget) {
        simpleBrowseWidget = widget;
        columnMetaData = m;
        viewPosition = getNextViewIndex();
        rowLevel = 0;
        simpleBrowseWidget.columns.add(this);
        if (simpleBrowseWidget.tableView.getActionColumn() != m)
            simpleBrowseWidget.availableGroups.add(this);
        if (simpleBrowseWidget.hasActionColumn && simpleBrowseWidget.tableView.getActionColumn() == m)
            simpleBrowseWidget.isActionColumnAddedManually = true;
    }

    @Override
    public List<IsColumnGroup> getChildren() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public int getViewPosition() {
        return viewPosition;
    }

    public void hideColumn() {
        if (!hidden) {
            if (!simpleBrowseWidget.availableGroups.remove(this))
                throw new RuntimeException("Could not hide column. Check if it does not belong to a column group.");
            hidden = true;
        }

    }

    public void unhideColumn() {
        if (hidden) {
            viewPosition = getNextViewIndex();
            simpleBrowseWidget.availableGroups.add(this);
            hidden = false;
        }

    }

    private int getNextViewIndex() {
        if (simpleBrowseWidget.availableGroups.size() > 0) {
            return ((IsColumnGroup) simpleBrowseWidget.availableGroups.last()).getViewPosition() + 1;
        } else {
            return 0;
        }
    }

    public void moveBefore(IsColumnGroup column) {
        simpleBrowseWidget.availableGroups.remove(this);

        if (column != null) {
            if (!simpleBrowseWidget.availableGroups.contains(column)) {
                throw new RuntimeException("Parameter column must be visible, belong to the same browse widget and be ungrouped column or group");
            }
            SortedSet<IsColumnGroup> needShift = simpleBrowseWidget.availableGroups.tailSet(column);

            IsColumnGroup[] tmp = new IsColumnGroup[needShift.size()];
            needShift.toArray(tmp);
            needShift.clear();

            this.viewPosition = tmp[0].getViewPosition();

            for (IsColumnGroup groupToBeShifted : tmp) {
                if (groupToBeShifted instanceof SimpleBrowseWidgetColumn) {
                    ((SimpleBrowseWidgetColumn) groupToBeShifted).viewPosition++;
                } else {
                    ((ColumnGroup) groupToBeShifted).position++;
                }
            }
            simpleBrowseWidget.availableGroups.addAll(Arrays.asList(tmp));
        } else {
            this.viewPosition = getNextViewIndex();
        }
        simpleBrowseWidget.availableGroups.add(this);
        this.hidden = false;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public int getColSpan() {
        return 1;
    }

    @Override
    public Widget getColumnHeaderWidget() {
        Widget retVal = simpleBrowseWidget.presenter.getColumnHeader(columnMetaData).getWidget();
        return retVal;
    }

    public ColumnMetaData getColumnMetaData() {
        return columnMetaData;
    }

    public Cell<C> getCurrentRowCell() {
        if (simpleBrowseWidget.currentRows[rowLevel] == null)
            throw new IllegalStateException("This method can be called only in SimpleBrowseWidget.setValue implementation.");
        return (Cell<C>) simpleBrowseWidget.currentRows[rowLevel].getRowCellList().get(columnIndex);
    }

    @SuppressWarnings("unchecked")
    public void setCurrentRowValue(C value) {
        getCurrentRowCell().setValue(value);
    }

    private void assertState() {
        if (simpleBrowseWidget.columnsAddedToPresenter)
            throw new IllegalStateException("Can not change column metadata after SimpleBrowseWidget.getBrowseFormPresenter call.");
    }

    public T text(String text) {
        assertState();
        columnMetaData.setText(text);
        return (T) this;
    }

    public T filtered(boolean filtered) {
        assertState();
        this.filtered = filtered;
        return (T) this;
    }

    public T sortedBy(String title) {
        assertState();
        columnMetaData.setName(title);
        columnMetaData.setSortable(true);
        return (T) this;
    }

    public T rowSublevel(R rowLevel) {
        this.rowLevel = rowLevel.ordinal() + 1;
        return (T) this;
    }

    public T notSortable() {
        assertState();
        columnMetaData.setName(null);
        columnMetaData.setSortable(false);
        return (T) this;
    }

    public T width(String width) {
        assertState();
        columnMetaData.setWidth(width);
        return (T) this;
    }

    public T horizontalAlignment(HorizontalAlignmentConstant alignment) {
        assertState();
        horizontalAlignment = alignment;
        return (T) this;
    }

    public F getFilterValue() {
        return getQueryInput().getValue();
    }

    public boolean isFiltered() {
        return filtered;
    }

    protected QueryInput<F> getQueryInput() {
        if (!filtered)
            throw new IllegalStateException("Column is not filtered. Use .filtered()");
        if (queryInput == null)
            queryInput = createQueryInput();
        return queryInput;
    }

    public Widget getFilterWidget() {
        return getQueryInput().getWidget();
    }

    protected Cell<C> createAndSetupCell() {
        Cell<C> cell = createCell();
        if (horizontalAlignment != null)
            cell.setHorizontalAlignment(horizontalAlignment);
        return cell;
    }

    public void setSummaryValue(C summaryValue) {
        this.summaryValue = summaryValue;
    }

    public C getSummaryValue() {
        return summaryValue;
    }

    public Cell<C> createSummaryCell() {
        Cell<C> cell = createCell();
        if (summaryValue != null)
            cell.setValue(summaryValue);
        return cell;
    }

    @Override
    public void appendElementsTo(List<IsColumnGroup> list) {
        list.add(this);
    }

    protected abstract Cell<C> createCell();

    protected abstract QueryInput<F> createQueryInput();

}
