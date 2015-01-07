package lt.jmsys.spark.gwt.application.common.client.vvsis.service;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Klientas;
import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Objektas;
import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Paraiska;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("VvsisService")
public interface VvsisService extends RemoteService {

    
    Klientas login(String pUsername, String pPassword) throws SparkBusinessException;
    void logout() throws SparkBusinessException;
    Klientas getKlientas() throws SparkBusinessException;
    Paraiska getParaiska(String pParaiskosNumeris, String pSavininkoKodas) throws SparkBusinessException;
    List<Paraiska> getParaiskos() throws SparkBusinessException;
    List<Objektas> getObjektai() throws SparkBusinessException;
    void saveParaiska(Paraiska pParaiska) throws SparkBusinessException;
}
