package univ.evector.gwt.client.oracle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lt.jmsys.spark.gwt.client.suggest.oracle.ServiceSuggestOracle;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class EvectorServiceSuggestOracle<E> extends ServiceSuggestOracle<E> {

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

}
