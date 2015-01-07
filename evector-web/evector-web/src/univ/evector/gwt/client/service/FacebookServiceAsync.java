package univ.evector.gwt.client.service;

import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.facebook.FindFacebookPost;
import univ.evector.gwt.client.F005.bean.F005Query;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FacebookServiceAsync {

    void findDocuments(F005Query query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindFacebookPost>> callback);

}