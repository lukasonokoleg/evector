package lt.jmsys.spark.gwt.application.common.client.helper;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.css.SparkCssResource;
import lt.jmsys.spark.gwt.client.ui.css.SparkResourcesFactory;
import lt.jmsys.spark.gwt.client.ui.form.field.Field;
import lt.jmsys.spark.gwt.client.ui.widgets.infobox.InfoBox.TextSource;
import lt.jmsys.spark.gwt.client.ui.widgets.infobox.InfoButton;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is for creating {@link Label} elements or custom {@link Widget} with {@link Label} in it.</br>
 * All styles names in this class are located in <i>resources/css/</i>: 
 * <li>{@link theme.css} (theme styles);</br>
 * <li>{@link layout.css} (layout styles);</br>
 */
public class LabelHelper {

    protected static final SparkCssResource CSS = SparkResourcesFactory.create().css();

    private static final String CSS_RED = "red";

    private static final String CSS_HINT = "hint";

    private static final String CSS_REQUIRED = "required";

    private static final String CSS_FIELD_LABEL = "spark-Field-label";

    private static final String CSS_FIELD_LABEL_WIDGET = "spark-Field-label-widget";

    private static final String CSS_TABLE_HEADER_LABEL = "spark-DataTableHeader-label";

    /**
     * Create label widget
     * @param value
     * @return
     */
    public static Label createLabel(String value) {
        Label label = new Label();
        label.setText(value);
        return label;
    }

    /**
     * Create Field label widget
     * @param text
     * @return
     */
    public static Label createFieldLabel(String text) {
        return createFieldLabel(text, false);
    }

    /**
     * Create Field label widget
     * @param text
     * @param required
     * @return
     */
    public static Label createFieldLabel(String text, boolean required) {
        return Field.createLabelWidget(text, required, null);
    }

    /**
     * Create Field label widget with horizontal alignment
     * @param text
     * @param align
     * @return
     */
    public static Label createFieldLabel(String text, HasHorizontalAlignment.HorizontalAlignmentConstant align) {
        return createFieldLabel(text, false, align);
    }

    /**
     * Create Field label widget with horizontal alignment
     * @param text
     * @param required
     * @param align
     * @return
     */
    public static Label createFieldLabel(String text, boolean required, HasHorizontalAlignment.HorizontalAlignmentConstant align) {
        Label label = createFieldLabel(text, required);
        label.setHorizontalAlignment(align);
        return label;
    }

    /**
     * Create Field label widget with info button
     * @param text
     * @param helpText
     * @return
     */
    public static Widget createFieldLabel(String text, final String helpText) {
        if (helpText == null) {
            return createFieldLabel(text);
        } else {
            return createFieldLabel(createFieldLabel(text), helpText);
        }
    }

    /**
     * Create Field label widget with info button
     * @param label
     * @param helpTextSource
     * @return
     */
    public static Widget createFieldLabel(Label label, final String helpText) {
        FlowPanel panel = new FlowPanel();
        panel.setStyleName(CSS_FIELD_LABEL_WIDGET);
        panel.add(label);
        if (!ConversionHelper.isEmpty(helpText)) {
            TextSource helpTextSource = new TextSource() {

                @Override
                protected void getText(AsyncCallback<String> callback) {
                    callback.onSuccess(helpText);
                }
            };
            InfoButton infoBtn = new InfoButton(helpTextSource);
            panel.add(infoBtn);
        }
        return panel;
    }

    /**
     * Create Field in line label widget
     * @param text
     * @return
     */
    public static InlineLabel createFieldInlineLabel(String text) {
        InlineLabel label = new InlineLabel(text);
        label.setStyleName(CSS_FIELD_LABEL);
        return label;
    }

    /**
     * Create Table header label
     * @param text
     * @return
     */
    public static Label createTableHeaderLabel(String text) {
        Label label = createFieldLabel(text);
        label.setStyleName(CSS_TABLE_HEADER_LABEL);
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        return label;
    }

    /**
     * Set Field label widget to required
     * @param label
     * @param required
     */
    public static void setFieldLabelRequired(Label label, boolean required) {
        label.setStyleName(CSS_FIELD_LABEL);
        if (required) {
            label.addStyleDependentName(CSS_REQUIRED);
            label.addStyleName(CSS_REQUIRED);
        }
    }

    /**
     * Create hint label
     * @param text
     * @return
     */
    public static Label createHintLabel(String text) {
        Label label = new Label(text);
        label.setStyleName(CSS_HINT);
        return label;
    }

    /**
     * Create flow panel with help text label bellow field label.
     * @param labelText - field label text
     * @param helpText - help text
     * @return
     */
    public static FlowPanel createFieldLabelWithHint(String labelText, String hintText) {
        return createFieldLabelWithHint(labelText, hintText, false);
    }

    /**
     * Create flow panel with help text label bellow field label.
     * @param labelText - field label text
     * @param helpText - help text
     * @param required - field label required / not required
     * @return
     */
    public static FlowPanel createFieldLabelWithHint(String labelText, String hintText, boolean required) {
        Label fieldLabel = createFieldLabel(labelText, required, HasHorizontalAlignment.ALIGN_LEFT);
        return createFieldLabelWithHint(fieldLabel, hintText);
    }

    /**
     * Create flow panel with help text label bellow field label.
     * @param fieldLabel - field label
     * @param helpText - help text
     * @return
     */
    public static FlowPanel createFieldLabelWithHint(Label fieldLabel, String hintText) {
        FlowPanel fp = new FlowPanel();
        fp.add(fieldLabel);
        fp.add(createHintLabel(hintText));
        return fp;
    }

    /**
     * Create alert label widget
     * @param text
     * @return
     */
    public static Label createAlertLabel(String text) {
        Label label = new Label(text);
        label.setStyleName(CSS_RED);
        return label;
    }

}
