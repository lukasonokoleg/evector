package lt.jmsys.spark.gwt.application.client.common;

import java.util.List;

import lt.jmsys.spark.gwt.application.client.common.cell.ConstantsCellValueConverter;
import lt.jmsys.spark.gwt.client.ui.browse.cell.CellValueConverter;
import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.query.ListQueryInput;

import com.google.gwt.i18n.client.ConstantsWithLookup;

import eu.itreegroup.spark.application.bean.Classifier;

public class StatusListQueryInput extends ListQueryInput {

    private final ConstantsWithLookup CONSTS;

    public StatusListQueryInput(ColumnMetaData columnMetaData, List<Classifier> listClsf, ConstantsWithLookup CONSTS) {
        super(columnMetaData);
        this.CONSTS = CONSTS;
        for (Classifier classifier : listClsf) {
            addItem(classifier.getCode(), classifier.getDisplayValue());
        }
    }

    public CellValueConverter<String> getCellValueConverter() {
        return new ConstantsCellValueConverter(CONSTS);
    }

}
