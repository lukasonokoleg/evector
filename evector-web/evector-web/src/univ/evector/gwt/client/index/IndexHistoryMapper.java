package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.application.ApplicationHistoryMapper;
import lt.jmsys.spark.gwt.application.client.application.presenter.NotImplPlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import univ.evector.gwt.client.F001.presenter.F001Place;
import univ.evector.gwt.client.F002.presenter.F002Place;
import univ.evector.gwt.client.F003.presenter.F003Place;
import univ.evector.gwt.client.F004.presenter.F004Place;
import univ.evector.gwt.client.F005.presenter.F005Place;
import univ.evector.gwt.client.F006.presenter.F006Place;
import univ.evector.gwt.client.F007.presenter.F007Place;
import univ.evector.gwt.client.F008.presenter.F008Place;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CompositePlace.Tokenizer.class, NotImplPlace.Tokenizer.class,
//
        F001Place.Tokenizer.class, F002Place.Tokenizer.class, F003Place.Tokenizer.class,
        //
        F004Place.Tokenizer.class, F005Place.Tokenizer.class, F006Place.Tokenizer.class,
        //
        F007Place.Tokenizer.class, F008Place.Tokenizer.class
//

//
})
public interface IndexHistoryMapper extends ApplicationHistoryMapper {

}