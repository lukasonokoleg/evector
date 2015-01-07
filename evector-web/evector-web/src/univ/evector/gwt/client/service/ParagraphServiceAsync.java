package univ.evector.gwt.client.service;

import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.beans.browse.FindNavaParagraphs;
import univ.evector.beans.browse.FindParagraphs;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ParagraphServiceAsync {

    void updateParagraphNavaWords(Long prgId, AsyncCallback<Void> callback);

    void findParagraphs(Long bksId, String query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindParagraphs>> callback);

    void findNavaParagraphs(Long bksId, String query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindNavaParagraphs>> callback);

    void findEmotionalParagraphs(Long bksId, String query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindEmotionalParagraphs>> callback);

}
