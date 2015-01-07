package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.client.application.presenter.NotImplPlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CompositePlace.Tokenizer.class, NotImplPlace.Tokenizer.class })
public interface ApplicationHistoryMapper extends PlaceHistoryMapper {

}
