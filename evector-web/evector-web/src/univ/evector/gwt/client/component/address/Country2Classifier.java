package univ.evector.gwt.client.component.address;

import eu.itreegroup.spark.application.bean.ClassifierAdapter;
import univ.evector.beans.Country;

public class Country2Classifier implements ClassifierAdapter<Country> {

    @Override
    public String getCode(Country value) {
        return value.getCnt_iso_code();
    }

    @Override
    public String getDisplayValue(Country value) {
        return value.getCnt_name();
    }

}