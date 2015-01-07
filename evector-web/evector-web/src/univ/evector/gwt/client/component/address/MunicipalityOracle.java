package univ.evector.gwt.client.component.address;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.helper.ValueProvider;
import univ.evector.beans.Municipality;
import univ.evector.gwt.client.service.AddressService;
import univ.evector.gwt.client.service.AddressServiceAsync;

import com.google.gwt.core.client.GWT;

import eu.itreegroup.spark.gwt.application.client.suggest.ClassifierServiceOracle;

public class MunicipalityOracle extends ClassifierServiceOracle<Municipality> {

    private AddressServiceAsync service = GWT.create(AddressService.class);
    private ValueProvider<String> countryCodeProvider;

    public MunicipalityOracle(ValueProvider<String> countryCodeProvider) {
        super(new Municipality2Classifier());
        this.countryCodeProvider = countryCodeProvider;
    }

    @Override
    protected void callService(Request request, Callback callback) {

        String code = countryCodeProvider.provideValue();
        if (!ConversionHelper.isEmpty(code)) {
            service.findMunicipalities(request.getQuery(), asyncResponse(request, callback));
        } else {
            emptyResponse(request, callback);
        }
    }
}
