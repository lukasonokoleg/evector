package lt.jmsys.spark.gwt.application.client.common.field.view;

import java.util.List;

import lt.jmsys.spark.gwt.application.client.common.field.resource.ListComponentConstants;
import lt.jmsys.spark.gwt.client.ui.form.field.Field;
import lt.jmsys.spark.gwt.client.ui.message.MessageConstants;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.ValidationErrorMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.itreegroup.spark.application.bean.Classifier;

public class VClassifierCheckBoxes<E extends Classifier> extends ClassifierCheckBoxes<E> {

    private static final ListComponentConstants C = GWT.create(ListComponentConstants.class);
    private static final MessageConstants MC = GWT.create(MessageConstants.class);

    private VerticalPanel vp;
    private Grid grid;
    private boolean required;
    private String labelText;
    private Label label;

    public VClassifierCheckBoxes() {
        this(null);
    }

    public VClassifierCheckBoxes(List<E> values) {
        this(values, true);
    }

    public VClassifierCheckBoxes(List<E> values, boolean showSelectAll) {
        super(values, showSelectAll);
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public boolean validate(MessageContainer container) {
        if (isRequired() && getValue().isEmpty()) {
            container.addMessage(new ValidationErrorMessage(MC.errNotSpecified(labelText), getFocusable()));
            return false;
        } else {
            return true;
        }
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Label getLabelWidget() {
        if (null == label) {
            label = Field.createLabelWidget(labelText, isRequired(), null);
        }
        return label;
    }

    @Override
    protected void init(List<E> values, boolean showSelectAll) {
        vp = new VerticalPanel();
        Anchor selectAll = new Anchor(C.linkSelectAll());
        if (showSelectAll) {
            vp.add(selectAll);
        }
        /*        grid = new Grid(values.size(), 2);
                int r = 0;
                for (E v : values) {
                    final CheckBox cb = new CheckBox();
                    if (null == focusable) {
                        focusable = cb;
                    }
                    valuesMap.put(cb, v);
                    Label label = createLabel(cb, v.getName());
                    grid.setWidget(r, 0, label);
                    grid.setWidget(r, 1, cb);
                    r++;

                }
                grid.setStyleName("venum-checkboxes");
                vp.add(grid);*/

        initValues(values);

        if (showSelectAll) {
            selectAll.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    for (CheckBox cb : valuesMap.keySet()) {
                        cb.setValue(true);
                    }
                }
            });

            vp.setCellHorizontalAlignment(selectAll, HasHorizontalAlignment.ALIGN_RIGHT);
        }

        initWidget(vp);
    }

    public void initValues(List<E> values) {
        if (null != grid) {
            vp.remove(grid);
            grid = null;
            valuesMap.clear();
        }
        if (null != values) {
            grid = new Grid(values.size(), 2);
            int r = 0;
            for (E v : values) {
                final CheckBox cb = new CheckBox();
                if (null == focusable) {
                    focusable = cb;
                }
                valuesMap.put(cb, v);
                Label label = createLabel(cb, v.getDisplayValue());
                grid.setWidget(r, 0, label);
                grid.setWidget(r, 1, cb);
                r++;

            }
            grid.setStyleName("venum-checkboxes");
            vp.add(grid);
        }
    }
}
