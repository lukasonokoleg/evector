package univ.evector.gwt.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.UserSessionHolder;
import univ.evector.beans.facebook.FindFacebookPost;
import univ.evector.facebook.converter.FacebookConverter;
import univ.evector.facebook.helper.FacebookConverterHelper;
import univ.evector.facebook.reading.FacebookReadingBuilder;
import univ.evector.gwt.client.F005.bean.F005Query;
import univ.evector.gwt.client.service.FacebookService;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;

@Service
@Transactional
public class FacebookServiceImpl implements FacebookService {

    @Autowired
    private ApplicationContext context;

    @Override
    public QueryResult<FindFacebookPost> findDocuments(F005Query query, QueryMetaData queryMetaData) throws SparkBusinessException {
        QueryResult<FindFacebookPost> retVal = null;
        try {
            UserSessionHolder userSessionHolder = context.getBean(UserSessionHolder.class);
            Facebook facebook = userSessionHolder.getFacebook();

            Date sinceDate = null;
            Date untilDate = null;
            String searchText = null;

            if (query != null) {
                sinceDate = query.getSinceDate();
                untilDate = query.getUntilDate();
                searchText = query.getSearchText();
            }

            Reading reading = new FacebookReadingBuilder()

            .setSinceDate(sinceDate)

            .setUntilDate(untilDate)

            .setSearchText(searchText)

            .build();

            ResponseList<Post> response = facebook.getHome(reading);
            FacebookConverter<FindFacebookPost, Post> converter = new FacebookConverter<FindFacebookPost, Post>() {

                @Override
                public FindFacebookPost convert(Post value) {
                    FindFacebookPost retVal = new FindFacebookPost();

                    String id = null;
                    String postMessage = null;
                    Date postDate = null;

                    if (value != null) {

                        id = value.getId();
                        postMessage = value.getMessage();
                        postDate = value.getCreatedTime();

                        retVal.setId(id);
                        retVal.setPost(postMessage);
                        retVal.setPostDate(postDate);
                    }

                    return retVal;
                }
            };

            retVal = getQueryResult(response, converter);
        } catch (FacebookException e) {
            throw new RuntimeException("...", e);

        }
        return retVal;
    }

    public static <E extends Serializable, T> QueryResult<E> getQueryResult(ResponseList<T> response, FacebookConverter<E, T> converter) {
        QueryResult<E> retVal = new QueryResult<E>();

        Iterator<T> iterator = response.iterator();

        int pageNumber = 0;
        int pageSize = 0;
        int totalCount = 0;
        LinkedList<E> data = FacebookConverterHelper.convert(iterator, converter);

        retVal.setPageNumber(pageNumber);
        retVal.setPageSize(pageSize);
        retVal.setTotalCount(totalCount);
        retVal.setData(data);

        return retVal;
    }

}
