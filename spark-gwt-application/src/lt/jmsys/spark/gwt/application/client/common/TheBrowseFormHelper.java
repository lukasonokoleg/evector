package lt.jmsys.spark.gwt.application.client.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.gwt.application.shared.clsf.ClassifierFactory;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnHeader;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.presenter.BrowseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.browse.row.Row;
import lt.jmsys.spark.gwt.user.client.ui.DataTableHeader;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TheBrowseFormHelper {

    public static ColumnHeader createColumnHeader(ColumnMetaData columnMeta, Widget widget) {
        ColumnHeader columnHeader = new DataTableHeader(columnMeta);
        HorizontalPanel columnHeaderPanel = (HorizontalPanel) columnHeader.getWidget();
        Label label = (Label) columnHeaderPanel.getWidget(columnHeader.getMeta().isSortable() ? 1 : 0);
        VerticalPanel vp = new VerticalPanel();
        vp.add(widget);
        vp.setSpacing(3);
        vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        columnHeaderPanel.remove(label);
        columnHeaderPanel.add(vp);
        return columnHeader;
    }

    public static void addColumnMetaData(BrowseFormPresenter<?> browseFormPresenter, ColumnMetaData... columnMetaDatas) {
        if (columnMetaDatas != null && browseFormPresenter != null) {
            for (ColumnMetaData obj : columnMetaDatas) {
                if (obj != null) {
                    browseFormPresenter.addColumnMetaData(obj);
                }
            }
        }
    }

    public static StatusListQueryInput getStatusListQueryInput(ColumnMetaData columnMetaData, String clsfDomainName, ConstantsWithLookup CONSTS) {
        return new StatusListQueryInput(columnMetaData, ClassifierFactory.getInstance().getClassifier(clsfDomainName), CONSTS);
    }

    public static <E extends Serializable> List<E> getSelectedBrowseFormValues(BrowseFormPresenter<E> browseFormPresenter) {
        int[] selected = browseFormPresenter.getRowsSelection().getSelectedIndex();
        List<E> result = new ArrayList<E>();
        if (null != selected) {
            for (int i : selected) {
                Row<E> row = browseFormPresenter.getRow(i);
                result.add(row.getValue());
            }
        }
        return result;
    }

}
