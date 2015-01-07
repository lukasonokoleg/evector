package univ.evector.gwt.client.component.address;

import eu.itreegroup.spark.application.bean.ClassifierAdapter;
import univ.evector.beans.Residence;

public class Residence2Classifier implements ClassifierAdapter<Residence> {

    @Override
    public String getCode(Residence value) {
        return value.getRsd_id() != null ? value.getRsd_id().toString() : null;
    }

    @Override
    public String getDisplayValue(Residence value) {
        return value.getRsd_name();
    }

}