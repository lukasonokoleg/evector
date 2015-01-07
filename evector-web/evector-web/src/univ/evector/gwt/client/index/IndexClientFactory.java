package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import univ.evector.gwt.client.index.presenter.EvectorIndexPresenter;
import univ.evector.gwt.client.index.view.EvectorIndexView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class IndexClientFactory extends ClientFactory {

    @Override
    protected PlaceHistoryMapper createHistoryMapper() {
        return GWT.create(IndexHistoryMapper.class);
    }

    @Override
    protected ApplicationPresenter createApplicationPresenter() {
        return new EvectorIndexPresenter(this, new EvectorIndexView());
    }
}
