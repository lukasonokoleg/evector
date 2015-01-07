package eu.itreegroup.spark.gwt.login.client;

import lt.jmsys.spark.gwt.application.client.application.ApplicationActivityMapper;
import lt.jmsys.spark.gwt.application.client.application.SplitPointCallback;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.application.bean.User;
import eu.itreegroup.spark.gwt.login.client.presenter.Layout;
import eu.itreegroup.spark.gwt.login.client.presenter.LoginPlace;
import eu.itreegroup.spark.gwt.login.client.presenter.LoginPresenter;
import eu.itreegroup.spark.gwt.login.client.view.LoginView;

public class LoginActivityMapper extends ApplicationActivityMapper {

    @Override
    public void getActivity(Place place, final AsyncCallback<Activity> callback) {
        if (place instanceof LoginPlace) {
            GWT.runAsync(new SplitPointCallback(callback) {

                @Override
                public void onSuccess() {
                    callback.onSuccess(new FormActivity<User, Layout>(new LoginPresenter<User>(new LoginView<User>() {

                        @Override
                        public User newValue() {
                            return null;
                        }
                    })));
                }

            });
        } else {
            super.getActivity(place, callback);
        }
    }
}
