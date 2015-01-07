package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.beans.browse.FindNavaParagraphs;
import univ.evector.beans.browse.FindParagraphs;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface ParagraphService extends RemoteService {

    void updateParagraphNavaWords(Long prgId) throws SparkBusinessException;

    QueryResult<FindParagraphs> findParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    QueryResult<FindNavaParagraphs> findNavaParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException;;

    QueryResult<FindEmotionalParagraphs> findEmotionalParagraphs(Long bksId, String query, QueryMetaData queryMetaDat) throws SparkBusinessException;;

}
