package univ.evector.gwt.client.service;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.book.Book;
import univ.evector.beans.browse.FindBooks;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface BookService extends RemoteService {

    QueryResult<FindBooks> findBooks(String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    void convertDocument(Long docId) throws SparkBusinessException;

    List<Book> getBooksList(String query, int limit) throws SparkBusinessException;
}