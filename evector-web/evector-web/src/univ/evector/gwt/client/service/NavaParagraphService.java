package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface NavaParagraphService extends RemoteService {

    void updateBookNavaParagraph(Long prgId) throws SparkBusinessException;

}
