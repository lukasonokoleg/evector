package lt.jmsys.spark.gwt.application.common.client.helper;

import com.google.gwt.user.client.ui.UIObject;

/**
 * This class is for setting/adding CLASS NAME for {@link UIObject} elements.</br>
 * This class methods must be used as default style names setting (exception: {@code *Helper} classes).</br>
 * All styles names in this class are located in <i>resources/css/</i>: 
 * <li>{@link theme.css} (theme styles);</br>
 * <li>{@link layout.css} (layout styles);</br>
 * </br>
 * <i>Methods ordered <b>alphabetically.<b></i>
 */
public class StyleHelper {

    /**
     * Add or remove widget class name
     * @param object
     * @param styleName
     * @param add
     */
    private static void addStyleName(UIObject object, String styleName, boolean add) {
        if (object != null) {
            object.setStyleName(styleName, add);
        }
    }

    /**
     * Set primary widget class name
     * @param object
     * @param styleName
     */
    private static void setStyleName(UIObject object, String styleName) {
        if (object != null) {
            object.setStyleName(styleName);
        }
    }

    /* DOM elements */
    public static void setBodyContainer(UIObject widget) {
        setStyleName(widget, "formViewBodyContainer");
    }

    public static void setButtonBar(UIObject widget) {
        setStyleName(widget, "formViewButtonBar");
    }

    public static void setFeedbackPopup(UIObject widget) {
        setStyleName(widget, "feedbackPopup");
    }

    public static void setFormBody(UIObject widget) {
        setStyleName(widget, "formBody");
    }

    public static void setLegendPanel(UIObject widget) {
        setStyleName(widget, "legendPanel");
    }

    public static void setListContainer(UIObject widget) {
        setStyleName(widget, "formViewListContainer");
    }

    public static void setPageContent(UIObject widget) {
        setStyleName(widget, "pageContent");
    }

    public static void setPageContentTop(UIObject widget) {
        setStyleName(widget, "pageContentTop");
    }

    public static void setPopupBody(UIObject widget) {
        setStyleName(widget, "formPopupBody");
    }

    public static void setSummaryCell(UIObject widget) {
        setStyleName(widget, "spark-TableComponent-cell summaryCell");
    }

    public static void setSummaryRow(UIObject widget) {
        setStyleName(widget, "spark-TableComponent-row  spark-TableComponent-row-noselect summaryRow");
    }

    public static void setTableComponentCellError(UIObject widget) {
        setStyleName(widget, "tableComponent-cellError");
    }

    public static void setVerticalFieldsPanel(UIObject widget) {
        setStyleName(widget, "verticalFieldsPanel");
    }

    public static void setVerticalFieldsPanelFieldGroup(UIObject widget) {
        setStyleName(widget, "verticalFieldsPanelFieldGroup");
    }

    public static void styleNoFirstFormHeader(UIObject widget) {
        addStyleName(widget, "no-first-header", true);
    }

    /* ACTIONS */
    public static void floatLeft(UIObject widget) {
        addStyleName(widget, "left", true);
    }

    public static void floatRight(UIObject widget) {
        addStyleName(widget, "right", true);
    }

    public static void fullWidth(UIObject widget) {
        addStyleName(widget, "fluid", true);
    }

    public static void removeFullWidth(UIObject widget) {
        addStyleName(widget, "non-fluid", true);
    }

    public static void resizeVertical(UIObject widget) {
        addStyleName(widget, "resize-y", true);
    }

    public static void resizeHorizontal(UIObject widget) {
        addStyleName(widget, "resize-x", true);
    }

    public static void selected(UIObject widget, boolean selected) {
        addStyleName(widget, "selected", selected);
    }

    public static void textCenter(UIObject widget) {
        addStyleName(widget, "text-center", true);
    }

    public static void textLeft(UIObject widget) {
        addStyleName(widget, "text-left", true);
    }

    public static void textRight(UIObject widget) {
        addStyleName(widget, "text-right", true);
    }
    
    public static void textWrap(UIObject widget) {
        addStyleName(widget, "preWrap", true);
        addStyleName(widget, "breakWord", true);
    }


}
