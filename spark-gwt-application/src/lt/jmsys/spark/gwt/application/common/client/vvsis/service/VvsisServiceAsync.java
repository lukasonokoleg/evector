package lt.jmsys.spark.gwt.application.common.client.vvsis.service;

import java.util.List;

import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Klientas;
import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Objektas;
import lt.jmsys.spark.gwt.application.common.shared.vvsis.data.Paraiska;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface VvsisServiceAsync {

    void login(String pUsername, String pPassword, AsyncCallback<Klientas> callback);

    void logout(AsyncCallback<Void> callback);

    void getParaiska(String pParaiskosNumeris, String pSavininkoKodas, AsyncCallback<Paraiska> callback);

    void getParaiskos(AsyncCallback<List<Paraiska>> callback);

    void saveParaiska(Paraiska pParaiska, AsyncCallback<Void> callback);

    void getObjektai(AsyncCallback<List<Objektas>> callback);

    void getKlientas(AsyncCallback<Klientas> callback);

}
