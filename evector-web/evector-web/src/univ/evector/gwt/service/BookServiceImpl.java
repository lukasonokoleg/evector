package univ.evector.gwt.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.book.Book;
import univ.evector.beans.browse.FindBooks;
import univ.evector.db.dao.BookDao;
import univ.evector.db.dao.DocumentDao;
import univ.evector.gwt.client.service.BookService;
import univ.evector.security.SelfSecured;
import univ.evector.tika.helper.TikaApacheHelper;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private DocumentDao documentDao;

    @SelfSecured
    @Override
    public QueryResult<FindBooks> findBooks(String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        return bookDao.findBooks(query, queryMetaData);
    }

    @Override
    public void convertDocument(Long docId) throws SparkBusinessException {
        try {
            DocumentWithBlob document = documentDao.findDocumentWithBlob(docId);
            Book book = TikaApacheHelper.parseBook(document.getDoc_blob().getBinaryStream());
            bookDao.saveBook(book);

        } catch (TransformerConfigurationException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getBooksList(String query, int limit) throws SparkBusinessException {
        List<Book> retVal = bookDao.getBookList(query, limit);
        return retVal;
    }
}
