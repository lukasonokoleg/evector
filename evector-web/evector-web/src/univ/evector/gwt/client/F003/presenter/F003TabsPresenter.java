package univ.evector.gwt.client.F003.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.presenter.F003NavaParagraphsPresenter.F003NavaParagraphsDisplay;
import univ.evector.gwt.client.F003.presenter.F003ParagraphPresenter.F003ParagraphDisplay;
import univ.evector.gwt.client.F003.view.F003TabsView;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F003TabsPresenter extends BaseFormPresenter<Book, F003Place> {

    public interface F003TabsDisplay extends FormDisplay<Book> {

        HasValueChangeHandlers<Book> getBookChangeSource();

        HasSelectionHandlers<Integer> getTabsSelectionChangeSource();

        F003ParagraphDisplay getF003ParagraphDisplay();

        F003NavaParagraphsDisplay getF003NavaParagraphDisplay();

        Integer getCurrentTabIndex();
    }

    private F003ParagraphPresenter paragraphsPresenter;
    private F003NavaParagraphsPresenter navaParagraphPresenter;

    public F003TabsPresenter(F003TabsDisplay view) {
        super(view);
        initChildPresenters();
        setLocalEventHandlers();
    }

    private void initChildPresenters() {
        if (paragraphsPresenter == null) {
            paragraphsPresenter = new F003ParagraphPresenter(getView().getF003ParagraphDisplay());
        }
        if (navaParagraphPresenter == null) {
            navaParagraphPresenter = new F003NavaParagraphsPresenter(getView().getF003NavaParagraphDisplay());
        }
    }

    @Override
    public F003TabsDisplay getView() {
        return (F003TabsDisplay) super.getView();
    }

    private void setLocalEventHandlers() {
        getView().getBookChangeSource().addValueChangeHandler(new ValueChangeHandler<Book>() {

            @Override
            public void onValueChange(ValueChangeEvent<Book> event) {
                if (event != null) {
                    onBookChanged(event.getValue());
                }
            }
        });
        getView().getTabsSelectionChangeSource().addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event != null) {
                    refreshCurrentTab();
                }
            }
        });
    }

    private void onBookChanged(Book value) {
        getView().setValue(value);
        refreshCurrentTab();
    }

    private void refreshCurrentTab() {
        Integer currentTab = getView().getCurrentTabIndex();
        if (currentTab != null) {
            switch (currentTab) {
                case F003TabsView.PRG_TAB_INDEX:
                    if (!paragraphsPresenter.hasBook(getView().getValue())) {
                        paragraphsPresenter.setBook(getView().getValue());
                        paragraphsPresenter.reload();
                    }
                    break;
                case F003TabsView.PRG_AND_NAVA_TAB_INDEX:
                    break;

                case F003TabsView.PRG_AND_EMO_TAB_INDEX:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public F003Place getParameters(Place place) {
        return (F003Place) place;
    }

    @Override
    public F003Place getParameters(Book value) {
        return null;
    }

    @Override
    protected void findValue(F003Place parameters, AsyncCallback<Book> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(Book value, F003Place parameters, FormPrivileges privileges, AsyncCallback<Book> callback) {
        getView().setValue(value);
        refreshCurrentTab();
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {

    }

    @Override
    public boolean validate(MessageContainer container, Book value) {
        boolean retVal = true;
        return retVal;
    }

}