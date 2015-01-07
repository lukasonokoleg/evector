package lt.jmsys.spark.gwt.application.client.common.field.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.application.client.common.field.resource.ListComponentConstants;
import lt.jmsys.spark.gwt.application.common.client.helper.LabelHelper;
import lt.jmsys.spark.gwt.application.shared.clsf.ClassifierHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.itreegroup.spark.application.bean.Classifier;

public class ClassifierCheckBoxes<E extends Classifier> extends Composite {

    private static final ListComponentConstants C = GWT.create(ListComponentConstants.class);

    Map<CheckBox, E> valuesMap = new HashMap<CheckBox, E>();
    Map<String, E> valuesByCode;

    private VerticalPanel vp;
    private Grid grid;

    protected Focusable focusable;
    private boolean readOnly;

    public ClassifierCheckBoxes(List<E> values) {
        this(values, true);
    }

    public ClassifierCheckBoxes(List<E> values, boolean showSelectAll) {
        this.valuesByCode = ClassifierHelper.getValuesMap(values);
        init(values, showSelectAll);
    }

    protected void init(List<E> values, boolean showSelectAll) {
        vp = new VerticalPanel();
        vp.setWidth("100%");
        if (showSelectAll) {
            Anchor selectAll = new Anchor(C.linkSelectAll());
            vp.add(selectAll);
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

        initValues(values);

        initWidget(vp);
    }

    public void initValues(List<E> values) {
        if (null != grid) {
            vp.remove(grid);
            grid = null;
            valuesMap.clear();
        }
        if (null != values) {
            grid = new Grid(2, values.size());
            grid.setWidth("100%");
            int c = 0;
            for (E v : values) {
                CheckBox cb = new CheckBox();
                if (null == focusable) {
                    focusable = cb;
                }
                valuesMap.put(cb, v);
                Label label = createLabel(cb, v.getDisplayValue());
                grid.setWidget(0, c, label);
                grid.setWidget(1, c, cb);
                grid.getColumnFormatter().setWidth(c, Integer.toString(100 / values.size()) + "%");
                c++;

            }
            grid.setStyleName("classificator-checkboxes");
            vp.add(grid);
        }
    }

    public void setValue(E[] value) {
        if (null != value) {
            List<E> list = Arrays.asList(value);
            setValue(list);
        } else {
            setValue((List<E>) null);
        }
    }

    public void setValue(List<E> list) {
        for (Map.Entry<CheckBox, E> entry : valuesMap.entrySet()) {
            if (contains(list, entry.getValue())) {
                entry.getKey().setValue(true);
            } else {
                entry.getKey().setValue(false);
            }
        }
    }

    private boolean contains(List<E> list, E value) {
        if (list != null) {
            for (E v : list) {
                if (v.getCode().equals(value.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    /*	public void setValue(Double [] value){
    		setValue(ClassificatorHelper.toClassificatorList(value, valuesByCode));
    	}*/

    public List<E> getValue() {
        List<E> l = new ArrayList<E>();
        for (Map.Entry<CheckBox, E> entry : valuesMap.entrySet()) {
            if (entry.getKey().getValue()) {
                l.add(entry.getValue());
            }
        }
        return l;
    }

    protected Label createLabel(final CheckBox cb, String name) {
        Label label = LabelHelper.createFieldLabel(name);
        label.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!readOnly){
                    cb.setValue(!cb.getValue());
                }
            }
        });
        return label;
    }

    public Focusable getFocusable() {
        return focusable;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        for (CheckBox cb : valuesMap.keySet()) {
            cb.setEnabled(!readOnly);
        }
    }

}
