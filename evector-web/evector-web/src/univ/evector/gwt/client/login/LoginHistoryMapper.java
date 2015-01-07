package univ.evector.gwt.client.login;

import lt.jmsys.spark.gwt.application.client.application.ApplicationHistoryMapper;
import lt.jmsys.spark.gwt.application.client.application.presenter.NotImplPlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import univ.evector.gwt.client.L001.presenter.L001Place;
import univ.evector.gwt.client.L002.presenter.L002Place;

import com.google.gwt.place.shared.WithTokenizers;

import eu.itreegroup.spark.gwt.login.client.presenter.LoginPlace;

@WithTokenizers({ CompositePlace.Tokenizer.class, LoginPlace.Tokenizer.class, NotImplPlace.Tokenizer.class, L001Place.Tokenizer.class, L002Place.Tokenizer.class })
public interface LoginHistoryMapper extends ApplicationHistoryMapper {

}
