package univ.evector.gwt.client.component.address;

import eu.itreegroup.spark.application.bean.ClassifierAdapter;
import univ.evector.beans.Street;

public class Street2Classifier implements ClassifierAdapter<Street> {

    @Override
    public String getCode(Street value) {
        return value.getStr_id() != null ? value.getStr_id().toString() : null;
    }

    @Override
    public String getDisplayValue(Street value) {
        return value.getStr_name();
    }

}