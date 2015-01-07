package univ.evector.gwt.client.F003.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.presenter.F003NavaParagraphsPresenter.F003NavaParagraphsDisplay;
import univ.evector.gwt.client.F003.presenter.F003ParagraphPresenter.F003ParagraphDisplay;
import univ.evector.gwt.client.F003.presenter.F003TabsPresenter.F003TabsDisplay;
import univ.evector.gwt.client.F003.resource.F003TabsConstants;
import univ.evector.gwt.client.oracle.BookOracle;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class F003TabsView extends BaseFormView<Book> implements F003TabsDisplay {

    public final static F003TabsConstants CONSTANTS = GWT.create(F003TabsConstants.class);

    private DecoratedTabPanel tabs = new DecoratedTabPanel();

    public static final int PRG_TAB_INDEX = 0;
    public static final int PRG_AND_NAVA_TAB_INDEX = 1;
    public static final int PRG_AND_EMO_TAB_INDEX = 2;

    private F003ParagraphsView paragraphsView = new F003ParagraphsView();
    private F003NavaParagraphsView navaParagraphsView = new F003NavaParagraphsView();
    private F003EmotionalParagraphsView emotionalParagraphsView = new F003EmotionalParagraphsView();

    private HTML selectBookHtml = ThemeHelper.createHeadingH1(CONSTANTS.selectBookHtml());
    private BookOracle bookOracle = new BookOracle();
    private SuggestField<Book> bookSuggestField = new SuggestField<>(bookOracle);

    private FlexTable skeleton = new FlexTable();

    public F003TabsView() {
        constructForm();
        constructFormTabs();
        getBodyContainer().add(skeleton);
    }

    private void constructForm() {
        skeleton.clear();
        int row = 0;
        int column = 0;

        skeleton.setWidget(row, column, selectBookHtml);
        row++;
        skeleton.setWidget(row, column, bookSuggestField);
        row++;
        skeleton.setWidget(row, column, tabs);
        row++;
    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    private void constructFormTabs() {
        tabs.clear();
        tabs.add(paragraphsView, CONSTANTS.paragraphsViewTab());
        tabs.add(navaParagraphsView, CONSTANTS.navaParagraphsViewTab());
        tabs.add(emotionalParagraphsView, CONSTANTS.emotionalParagraphsViewTab());
    }

    @Override
    public String getFormCaption() {
        return null;
    }

    @Override
    public void defaultFocus() {
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        return valid;
    }

    @Override
    protected void setFormValue(Book value) {
        paragraphsView.setValue(value);
        navaParagraphsView.setValue(value);
        emotionalParagraphsView.setValue(value);
    }

    @Override
    protected void updateFormItems() {
        super.updateFormItems();
        if (tabs.getTabBar().getSelectedTab() > 0) {

        } else {
            tabs.selectTab(PRG_TAB_INDEX);
        }

    }

    @Override
    public void getValue(Book value) {
    }

    @Override
    public Book newValue() {
        return new Book();
    }

    @Override
    public HasValueChangeHandlers<Book> getBookChangeSource() {
        return bookSuggestField;
    }

    @Override
    public HasSelectionHandlers<Integer> getTabsSelectionChangeSource() {
        return tabs;
    }

    @Override
    public F003ParagraphDisplay getF003ParagraphDisplay() {
        return paragraphsView;
    }

    @Override
    public Integer getCurrentTabIndex() {
        return tabs.getTabBar().getSelectedTab();
    }

    @Override
    public F003NavaParagraphsDisplay getF003NavaParagraphDisplay() {
        return navaParagraphsView;
    }

}
