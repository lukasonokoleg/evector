package univ.evector.gwt.client.service;

import java.util.List;

import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.book.Book;
import univ.evector.beans.browse.FindBooks;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BookServiceAsync {

    void findBooks(String query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindBooks>> callback);

    void convertDocument(Long docId, AsyncCallback<Void> callback);

    void getBooksList(String query, int limit, AsyncCallback<List<Book>> callback);
}