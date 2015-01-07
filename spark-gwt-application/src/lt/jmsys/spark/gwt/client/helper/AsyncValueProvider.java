package lt.jmsys.spark.gwt.client.helper;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AsyncValueProvider<E> {

    E provideValue(AsyncCallback<E> callback);

}
