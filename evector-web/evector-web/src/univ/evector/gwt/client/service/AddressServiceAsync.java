package univ.evector.gwt.client.service;

import java.util.List;

import univ.evector.beans.Country;
import univ.evector.beans.Municipality;
import univ.evector.beans.Residence;
import univ.evector.beans.Street;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AddressServiceAsync {

    void findCountries(String query, AsyncCallback<List<Country>> callback);

    void findMunicipalities(String query, AsyncCallback<List<Municipality>> callback);

    void findStreets(String query, AsyncCallback<List<Street>> callback);

    void findResidences(String query, AsyncCallback<List<Residence>> callback);

}
