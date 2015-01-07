package univ.evector.gwt.client.service;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import univ.evector.beans.Country;
import univ.evector.beans.Municipality;
import univ.evector.beans.Residence;
import univ.evector.beans.Street;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface AddressService extends RemoteService {

    List<Country> findCountries(String query) throws SparkBusinessException;

    List<Municipality> findMunicipalities(String query) throws SparkBusinessException;

    List<Residence> findResidences(String query) throws SparkBusinessException;

    List<Street> findStreets(String query) throws SparkBusinessException;

}
