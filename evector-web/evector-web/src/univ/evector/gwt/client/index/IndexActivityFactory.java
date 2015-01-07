package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.application.SplitPointCallback;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;
import univ.evector.gwt.client.F002.presenter.F002Place;
import univ.evector.gwt.client.F002.presenter.F002Presenter;
import univ.evector.gwt.client.F002.view.F002View;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unused")
public class IndexActivityFactory {

    private static final IndexActivityFactory instance = GWT.create(IndexActivityFactory.class);

    public static IndexActivityFactory getInstance() {
        return instance;
    }

}
