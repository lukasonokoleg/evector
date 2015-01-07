package univ.evector.db.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.Author;
import univ.evector.beans.book.Book;
import univ.evector.beans.book.helper.BookHelper;
import univ.evector.beans.browse.FindBooks;
import univ.evector.db.dao.BookDao;
import univ.evector.db.dao.ParagraphDao;

@Repository
public class BookDaoImpl extends CommonDaoImpl implements BookDao {

    private final BeanPropertyRowMapper<Author> AUTHOR_ROW = new BeanPropertyRowMapper<>(Author.class);
    private final BeanPropertyRowMapper<Book> BOOK_ROW_MAPPER = new BeanPropertyRowMapper<>(Book.class);

    @Autowired
    private ParagraphDao paragraphDao;

    @Override
    public QueryResult<FindBooks> findBooks(String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        String select = "SELECT bks.*, "//
                + "auth.* ";
        String from = "FROM "//
                + "evo_books bks LEFT JOIN evo_authors auth ON bks.bks_auth_id = auth.auth_id ";
        String where = "";
        BeanPropertyRowMapper<FindBooks> rowMapper = new BeanPropertyRowMapper<FindBooks>(FindBooks.class) {

            @Override
            public FindBooks mapRow(ResultSet rs, int rowNumber) throws SQLException {
                FindBooks retVal = super.mapRow(rs, rowNumber);
                Author author = AUTHOR_ROW.mapRow(rs, rowNumber);
                retVal.setAuthor(author);
                return retVal;
            }

        };

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", query);

        return browseJdbcHelper.browse(select, from, where, queryMetaData, rowMapper, paramMap);
    }

    @Override
    public void saveBook(Book book) throws SparkBusinessException {
        if (book == null) {
            throw new IllegalArgumentException();
        }
        if (BookHelper.hasBksId(book)) {
            updateBook(book);
        } else {
            insertBook(book);
        }
        paragraphDao.saveParagraphs(book);
    }

    private void insertBook(Book book) {
        String sql = "INSERT INTO evo_books "//
                + "( "//
                + "bks_title, "//
                + "bks_isbn "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":bks_title, "//
                + ":bks_isbn "//
                + ") ";

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(book);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        Long bksId = generatedKeyHolder.getKey().longValue();
        book.setBks_id(bksId);

    }

    private void updateBook(Book book) {

    }

    @Override
    public List<Book> getBookList(String query, int limit) throws SparkBusinessException {

        String sql = "SELECT bks.* "//
                + "" //
                + "FROM "//
                + "evo_books bks "//
                + "";

        if (query != null) {
            sql = sql//
                    + "WHERE "//
                    + "bks.bks_title LIKE :query ";

        }

        String limitSql = " LIMIT 0, :limit ";
        sql = sql + limitSql;

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", query);
        paramMap.put("limit", limit);

        List<Book> retVal = namedParamJdbcTemplate.query(sql, paramMap, BOOK_ROW_MAPPER);
        return retVal;
    }

}