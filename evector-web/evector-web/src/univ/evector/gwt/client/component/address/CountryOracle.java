package univ.evector.gwt.client.component.address;

import univ.evector.beans.Country;
import univ.evector.gwt.client.service.AddressService;
import univ.evector.gwt.client.service.AddressServiceAsync;

import com.google.gwt.core.client.GWT;

import eu.itreegroup.spark.gwt.application.client.suggest.ClassifierServiceOracle;

public class CountryOracle extends ClassifierServiceOracle<Country> {

    private AddressServiceAsync service = GWT.create(AddressService.class);

    public CountryOracle() {
        super(new Country2Classifier());
    }

    @Override
    protected void callService(Request request, Callback callback) {
        service.findCountries(request.getQuery(), asyncResponse(request, callback));
    }
}
