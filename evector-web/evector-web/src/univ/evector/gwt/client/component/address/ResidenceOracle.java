package univ.evector.gwt.client.component.address;

import lt.jmsys.spark.gwt.client.helper.ValueProvider;
import univ.evector.beans.Residence;
import univ.evector.gwt.client.service.AddressService;
import univ.evector.gwt.client.service.AddressServiceAsync;

import com.google.gwt.core.client.GWT;

import eu.itreegroup.spark.gwt.application.client.suggest.ClassifierServiceOracle;

public class ResidenceOracle extends ClassifierServiceOracle<Residence> {

    private ValueProvider<Long> municipalityIdProvider;
    private AddressServiceAsync service = GWT.create(AddressService.class);

    public ResidenceOracle(ValueProvider<Long> municipalityIdProvider) {
        super(new Residence2Classifier());
        this.municipalityIdProvider = municipalityIdProvider;
    }

    @Override
    protected void callService(Request request, Callback callback) {
        Long id = municipalityIdProvider.provideValue();
        if (null != id) {
            service.findResidences(request.getQuery(), asyncResponse(request, callback));
        } else {
            emptyResponse(request, callback);
        }
    }
}
