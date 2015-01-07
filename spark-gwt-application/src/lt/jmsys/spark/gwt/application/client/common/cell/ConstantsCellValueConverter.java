package lt.jmsys.spark.gwt.application.client.common.cell;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.browse.cell.CellValueConverter;
import lt.jmsys.spark.gwt.client.ui.browse.row.Row;

import com.google.gwt.i18n.client.ConstantsWithLookup;

public class ConstantsCellValueConverter implements CellValueConverter<String> {

    private ConstantsWithLookup C;

    public ConstantsCellValueConverter(ConstantsWithLookup C) {
        this.C = C;
    }

    @Override
    public String convert(String cellValue, Row<?> row) {
        if (!ConversionHelper.isEmpty(cellValue)) {
            try {
                return C.getString(cellValue);
            } catch (Exception e) {
                return cellValue;
            }
        } else {
            return "";
        }
    }
}
