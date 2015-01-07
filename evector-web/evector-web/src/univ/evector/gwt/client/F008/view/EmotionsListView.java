package univ.evector.gwt.client.F008.view;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.gwt.application.common.client.helper.LabelHelper;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.form.presenter.ListComponentPresenter.ItemCountChangedEvent;
import lt.jmsys.spark.gwt.client.ui.form.view.AbstractListComponentView;
import lt.jmsys.spark.gwt.client.ui.form.view.EditFormDisplay;
import lt.jmsys.spark.gwt.client.ui.form.view.EditFormDisplayFactory;
import univ.evector.beans.emotion.Emotion;
import univ.evector.gwt.client.F008.resource.EmotionsListConstants;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EmotionsListView extends AbstractListComponentView<Emotion> {

    public final static EmotionsListConstants C = GWT.create(EmotionsListConstants.class);

    public EmotionsListView() {
        super(new EditFormDisplayFactory<Emotion>() {

            @Override
            public EditFormDisplay<Emotion> create() {
                return new EmotionsListItemView();
            }

        }, new FlexTable(), 0, 1);

        createHeader();
        createFooter();

        table.setWidth("600px");

    }

    @Override
    protected FocusWidget createAddItemButton() {
        Anchor retVal = EvectorButtonHelper.getInstance().createAddAnchor(C.addItemButton());

        retVal.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                addItem(new Emotion());
            }
        });

        return retVal;
    }

    private void createFooter() {

        int row = DY + 1;
        int column = 0;

        table.setWidget(row, column, getAddItemButton());

    }

    private void createHeader() {

        Label emotionNameLabel = LabelHelper.createFieldLabel(C.colEmotionName());
        Label emotionWordsLabel = LabelHelper.createFieldInlineLabel(C.colEmotionWords());

        StyleHelper.floatLeft(emotionNameLabel);
        StyleHelper.floatLeft(emotionWordsLabel);

        int row = 0;
        int column = 0;

        table.setWidget(row, column, emotionNameLabel);
        table.getFlexCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_LEFT);
        column++;

        table.setWidget(row, column, emotionWordsLabel);
        table.getFlexCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_LEFT);

    }

    @Override
    public void setValue(List<Emotion> value) {
        List<Emotion> tmpValue = new ArrayList<>();
        if (value != null && value.size() > 0) {
            tmpValue.addAll(tmpValue);
        } else {
            tmpValue.add(new Emotion());
        }
        super.setValue(tmpValue);
    }

    @Override
    public void addItem(Emotion value) {
        int row = items.size();
        row = rowToTableRowIndex(row);
        EditFormDisplay<Emotion> tmpItemView = itemViewFactory.create();

        if (tmpItemView instanceof EmotionsListItemView) {
            EmotionsListItemView itemView = (EmotionsListItemView) tmpItemView;

            itemView.setValue(value);

            int column = DX;

            table.insertRow(row);

            table.setWidget(row, column, itemView.getEmotionNameField());

            column++;

            table.setWidget(row, column, itemView.getEmotionWordsField());

            column++;

            table.setWidget(row, column, itemView.getRemoveAnchor());

            table.getRowFormatter().setVerticalAlign(row, HasVerticalAlignment.ALIGN_TOP);
            items.add(itemView);
            eventBus.fireEvent(new ItemCountChangedEvent(true, null, items.size()));
        }
    }

    @Override
    public void removeItem(int index) {
        int size = items.size();
        if (size > 1) {
            super.removeItem(index);
        } else {
            EditFormDisplay<Emotion> itemView = items.get(index);
            itemView.setValue(new Emotion());
        }
    }

    @Override
    public Widget asWidget() {
        return table;
    }

}
