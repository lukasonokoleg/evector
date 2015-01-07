package lt.jmsys.spark.gwt.application.client.common.browse;

import java.io.Serializable;

import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BrowseWidgetQueryExecutor<BEAN extends Serializable> {

    void execute(QueryMetaData queryMetaData, AsyncCallback<QueryResult<BEAN>> callback);
}
