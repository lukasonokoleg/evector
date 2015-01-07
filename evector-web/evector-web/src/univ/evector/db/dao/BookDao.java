package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.stereotype.Repository;

import univ.evector.beans.book.Book;
import univ.evector.beans.browse.FindBooks;

@Repository
public interface BookDao {

    QueryResult<FindBooks> findBooks(String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    void saveBook(Book book) throws SparkBusinessException;

    List<Book> getBookList(String query, int limit) throws SparkBusinessException;

}