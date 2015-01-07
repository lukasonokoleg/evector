package univ.evector.gwt.client.component.address;

import eu.itreegroup.spark.application.bean.ClassifierAdapter;
import univ.evector.beans.Municipality;

public class Municipality2Classifier implements ClassifierAdapter<Municipality> {

    @Override
    public String getCode(Municipality value) {
        return value.getMncp_id() != null ? value.getMncp_id().toString() : null;
    }

    @Override
    public String getDisplayValue(Municipality value) {
        return value.getMncp_name();
    }

}