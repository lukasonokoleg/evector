package eu.itreegroup.spark.gwt.application.client.suggest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lt.jmsys.spark.gwt.client.suggest.oracle.ServiceSuggestOracle;
import lt.jmsys.spark.gwt.client.suggest.oracle.TransliterationHelper;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField.TransliteratedSuggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.application.bean.Classifier;
import eu.itreegroup.spark.application.bean.ClassifierAdapter;

public abstract class ClassifierServiceOracle<E extends Serializable> extends ServiceSuggestOracle<E> {

    ClassifierAdapter<E> adapter;

    public ClassifierServiceOracle(ClassifierAdapter<E> adapter) {
        this.adapter = adapter != null ? adapter : new DefaultAdapter();
    }

    @Override
    public Suggestion toSuggestion(E value) {
        return new ClassifierSuggestion(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E toValue(Suggestion suggestion) {
        if (null != suggestion) {
            return ((ClassifierSuggestion) suggestion).getValue();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected void emptyResponse(final Request request, final Callback callback) {
        Response response = new Response();
        response.setSuggestions(Collections.EMPTY_LIST);
        callback.onSuggestionsReady(request, response);
    }

    protected AsyncCallback<List<E>> asyncResponse(final Request request, final Callback callback) {
        return new AsyncCallback<List<E>>() {

            @Override
            public void onFailure(Throwable caught) {
                emptyResponse(request, callback);
            }

            @Override
            public void onSuccess(List<E> result) {
                Response response = new Response();
                List<Suggestion> suggestions = new ArrayList<Suggestion>();
                if (null != result) {
                    for (E value : result) {
                        suggestions.add(toSuggestion(value));
                    }
                    response.setMoreSuggestions(result.size() > request.getLimit());
                }
                response.setSuggestions(suggestions);
                callback.onSuggestionsReady(request, response);
            }
        };
    }

    protected AsyncCallback<QueryResult<E>> asyncQueryResultResponse(final Request request, final Callback callback) {
        return new AsyncCallback<QueryResult<E>>() {

            @Override
            public void onFailure(Throwable caught) {
                emptyResponse(request, callback);
            }

            @Override
            public void onSuccess(QueryResult<E> result) {
                Response response = new Response();
                List<Suggestion> suggestions = new ArrayList<Suggestion>();
                if (null != result) {
                    for (E value : result.getData()) {
                        suggestions.add(toSuggestion(value));
                    }
                    response.setMoreSuggestions(result.getData().size() < result.getTotalCount());
                }
                response.setSuggestions(suggestions);
                callback.onSuggestionsReady(request, response);
            }
        };
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    private class DefaultAdapter implements ClassifierAdapter<E> {

        @Override
        public String getCode(E value) {
            if (value instanceof Classifier) {
                return ((Classifier) value).getCode();
            } else {
                throw new IllegalArgumentException("Value should be instance of Classifier or ClassifierAdapter must be provided");
            }
        }

        @Override
        public String getDisplayValue(E value) {
            if (value instanceof Classifier) {
                return ((Classifier) value).getDisplayValue();
            } else {
                throw new IllegalArgumentException("Value should be instance of Classifier or ClassifierAdapter must be provided");
            }
        }
    }

    private class ClassifierSuggestion extends AbstractHighlightedSuggestion implements TransliteratedSuggestion {

        E value;

        public ClassifierSuggestion(E value) {
            this.value = value;
        }

        @Override
        public String getPlainTextDisplayString() {
            if (null != value) {
                return adapter.getDisplayValue(value);
            } else {
                return null;
            }
        }

        @Override
        public String getReplacementString() {
            return getPlainTextDisplayString();
        }

        @Override
        public String getTransliteratedMatchString() {
            return TransliterationHelper.transliterate(getPlainTextDisplayString());
        }

        public E getValue() {
            return value;
        }

    }

}
