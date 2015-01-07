package lt.jmsys.spark.gwt.application.client.common.browse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lt.jmsys.spark.gwt.application.common.client.helper.LabelHelper;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

class ColumnGroup implements IsColumnGroup {

    private final Label tableHeaderWidget;
    int position;
    private final int layer;
    private final int colSpan;
    private final List<IsColumnGroup> children = new ArrayList<IsColumnGroup>();

    ColumnGroup(String name, TreeSet<IsColumnGroup> availableGroups, IsColumnGroup... children) {
        tableHeaderWidget = LabelHelper.createTableHeaderLabel(name);
        position = children[0].getViewPosition();
        this.children.addAll(Arrays.asList(children));
        Set<IsColumnGroup> subset = availableGroups.subSet(children[0], children[children.length - 1]);
        Iterator<IsColumnGroup> iter = subset.iterator();
        int i = 0;
        IsColumnGroup group;
        while (iter.hasNext()) {
            group = iter.next();
            if (children[i] != group)
                throw new IllegalArgumentException("Wrong children in group.");
            i++;
        }
        subset.clear();
        availableGroups.remove(children[children.length - 1]);
        int maxLayer = 0;
        int colSpan = 0;
        for (IsColumnGroup cg : children) {
            if (cg.getLayer() > maxLayer) {
                maxLayer = cg.getLayer();
            }
            colSpan += cg.getColSpan();
        }
        this.colSpan = colSpan;
        layer = maxLayer + 1;
        availableGroups.add(this);
    }

    @Override
    public List<IsColumnGroup> getChildren() {
        return children;
    }

    @Override
    public Widget getColumnHeaderWidget() {
        return tableHeaderWidget;
    }

    @Override
    public int getViewPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "ColumnGroup[" + tableHeaderWidget.getText() + "]";
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public int getColSpan() {
        return colSpan;
    }

    @Override
    public void appendElementsTo(List<IsColumnGroup> list) {
        for (IsColumnGroup child : children)
            child.appendElementsTo(list);
    }
}