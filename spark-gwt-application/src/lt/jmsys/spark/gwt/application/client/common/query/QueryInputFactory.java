package lt.jmsys.spark.gwt.application.client.common.query;

import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.query.ListQueryInput;

import com.google.gwt.core.client.GWT;

import eu.itreegroup.spark.application.shared.db.bean.Als_YesNo;

public class QueryInputFactory {

    private static final CommonConstants C = GWT.create(CommonConstants.class);

    public static ListQueryInput newYesNoQueryInput(ColumnMetaData columnMetaData) {
        return newYesNoQueryInput(columnMetaData, true);
    }

    public static ListQueryInput newYesNoQueryInput(ColumnMetaData columnMetaData, boolean executeOnChange) {
        ListQueryInput queryInput = new ListQueryInput(columnMetaData, executeOnChange);
        queryInput.addItem(Als_YesNo.YES, C.dbAlcsYes());
        queryInput.addItem(Als_YesNo.NO, C.dbAlcsNo());
        return queryInput;
    }

}
