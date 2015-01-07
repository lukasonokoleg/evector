package lt.jmsys.spark.gwt.application.client.common.browse;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface IsColumnGroup {

    public List<IsColumnGroup> getChildren();

    public Widget getColumnHeaderWidget();

    public int getViewPosition();

    public int getLayer();

    public int getColSpan();

    public void appendElementsTo(List<IsColumnGroup> list);
}