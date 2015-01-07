package univ.evector.gwt.client.component.address;

import lt.jmsys.spark.gwt.client.helper.ValueProvider;
import univ.evector.beans.Street;
import univ.evector.gwt.client.service.AddressService;
import univ.evector.gwt.client.service.AddressServiceAsync;

import com.google.gwt.core.client.GWT;

import eu.itreegroup.spark.gwt.application.client.suggest.ClassifierServiceOracle;

public class StreetOracle extends ClassifierServiceOracle<Street> {

    private ValueProvider<Long> residenceIdProvider;

    private AddressServiceAsync service = GWT.create(AddressService.class);

    public StreetOracle(ValueProvider<Long> residenceIdProvider) {
        super(new Street2Classifier());
        this.residenceIdProvider = residenceIdProvider;
    }

    @Override
    protected void callService(Request request, Callback callback) {
        Long id = residenceIdProvider.provideValue();
        if (null != id) {
            service.findStreets(request.getQuery(), asyncResponse(request, callback));
        } else {
            emptyResponse(request, callback);
        }
    }
}
