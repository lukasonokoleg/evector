package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.facebook.FindFacebookPost;
import univ.evector.gwt.client.F005.bean.F005Query;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface FacebookService extends RemoteService {

    QueryResult<FindFacebookPost> findDocuments(F005Query query, QueryMetaData queryMetaData) throws SparkBusinessException;

}
