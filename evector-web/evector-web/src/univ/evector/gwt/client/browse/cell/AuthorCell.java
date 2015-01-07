package univ.evector.gwt.client.browse.cell;

import lt.jmsys.spark.gwt.application.common.client.helper.LabelHelper;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.helper.ClickSource;
import lt.jmsys.spark.gwt.client.ui.browse.cell.Cell;
import univ.evector.beans.Author;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AuthorCell extends Cell<Author> {

    private ClickSource clickSource = new ClickSource();
    private Author value;

    private Label authorFirstNameLabel = LabelHelper.createFieldLabel("");
    private Label authorLastNameLabel = LabelHelper.createFieldLabel("");

    private FlowPanel cellPanel = new FlowPanel();
    {
        cellPanel.add(authorFirstNameLabel);
        cellPanel.add(authorLastNameLabel);
    }

    public AuthorCell() {

    }

    @Override
    public HasClickHandlers getClickSource() {
        return clickSource;
    }

    @Override
    public Author getValue() {
        value = value != null ? value : newValue();
        getValue(value);
        return value;
    }

    protected Author newValue() {
        return value;
    }

    protected void getValue(Author value) {

    }

    @Override
    public Widget getWidget() {
        return cellPanel;
    }

    @Override
    protected void setValue(Author value, Author arg1) {
        this.value = value;
        setCellValue(value);
    }

    protected void setCellValue(Author value) {
        value = value != null ? value : newValue();
        authorFirstNameLabel.setText(value.getAuth_first_name());
        authorLastNameLabel.setText(value.getAuth_last_name());
    }

}
