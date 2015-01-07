package lt.jmsys.spark.gwt.application.common.client.helper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is for creating {@link DOM} elements or changing SPARK/GWT {@link Widget} properties.</br>
 * If needed styles must be set by using {@link StyleHelper}.
 */
public class ThemeHelper {

    private static final String CSS_HEADING_WITH_HINT = "heading-hint";

    private static final String CSS_HEADING_ROW = "heading-row";

    /**
     * Create space element 
     * @return
     */
    public static Widget space() {
        HTML html = new HTML("&nbsp;");
        return html;
    }

    /**
     * Create Heading 1 element
     * @param text
     * @return
     */
    public static HTML createHeadingH1(String text) {
        return createHtmlElement("h1", text);
    }

    /**
     * Create Heading 2 element
     * @param text
     * @return
     */
    public static HTML createHeadingH2(String text) {
        return createHtmlElement("h2", text);
    }

    /**
     * Create Heading 1 element. Add it to table and row style.
     * @param text
     * @param panel
     * @return
     */
    public static HTML createHeadingH1(String text, VerticalPanel panel) {
        HTML element = createHeadingH1(text);
        addHeadingToTable(element, panel);
        return element;
    }

    /**
     * Create Heading 2 element. Add it to table and add row style.
     * @param text
     * @param panel
     * @return
     */
    public static HTML createHeadingH2(String text, VerticalPanel panel) {
        HTML element = createHeadingH2(text);
        addHeadingToTable(element, panel);
        return element;
    }

    /**
     * Add heading element to table and add row style.
     * @param element
     * @param panel
     */
    public static void addHeadingToTable(Widget element, VerticalPanel panel) {
        if (panel != null && element != null) {
            String className = element.getElement().getClassName();
            panel.add(element);
            Element td = element.getElement().getParentElement();
            if (td != null && td.getParentElement() != null) {
                Element tr = td.getParentElement();
                if (tr != null) {
                    tr.addClassName(CSS_HEADING_ROW);
                    tr.addClassName(CSS_HEADING_ROW + "-" + className);
                }
            }
        }
    }

    /**
     * Add heading element to table and add row style.
     * @param element
     * @param panel
     */
    public static void addHeadingToTable(Widget element, FlexTable panel, int row, int col) {
        if (panel != null && element != null) {
            String className = element.getElement().getClassName();
            panel.setWidget(row, col, element);
            Element tr = panel.getRowFormatter().getElement(row);
            if (tr != null) {
                tr.addClassName(CSS_HEADING_ROW);
                tr.addClassName(CSS_HEADING_ROW + "-" + className);
            }
        }
    }

    /**
     * Create Heading 3 element
     * @param text
     * @return
     */
    public static HTML createHeadingH3(String text) {
        return createHtmlElement("h3", text);
    }

    /**
     * Create small text element
     * @param text
     * @return
     */
    public static HTML createSmallText(String text) {
        return createHtmlElement("small", text);
    }

    /**
     * Create Heading 2 element with hint text
     * @param text
     * @param hint
     * @return
     */
    public static FlowPanel createHeadingH1(FlowPanel fp, String text, String hint) {
        if (fp == null) {
            fp = new FlowPanel();
        } else {
            fp.clear();
        }
        fp.add(createHtmlElement("h1", text));
        fp.add(createHtmlElement("span", hint));
        fp.setStyleName(CSS_HEADING_WITH_HINT);
        return fp;
    }

    /**
     * Create custom DOM element
     * @param elementName
     * @param innerText
     * @return
     */
    private static HTML createHtmlElement(String elementName, String innerText) {
        final Element element = DOM.createElement(elementName);
        element.setInnerText(innerText);
        HTML html = new HTML(element.getString()) {

            @Override
            public void setText(String text) {
                if (getElement().getFirstChildElement() != null) {
                    getElement().getFirstChildElement().setInnerText(text);
                }
            };
        };
        html.setStyleName(elementName);
        return html;
    }

    /**
     * Create empty 20px height rows.
     * @param flexTable
     * @param row
     * @param count
     * @return
     */
    public static Widget createEmptyRows(FlexTable flexTable, int row, int count) {
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                flexTable.insertRow(row);
            }
            flexTable.setWidget(row, 0, null);
            flexTable.getCellFormatter().setHeight(row, 0, "20px");
        }
        return flexTable;
    }

    /**
     * Align widget left.
     * @param widget
     */
    public static void alignLeft(HasHorizontalAlignment widget) {
        widget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    }

    /**
     * Align widget right.
     * @param widget
     */
    public static void alignRight(HasHorizontalAlignment widget) {
        widget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    }

    /**
     * Align widget center.
     * @param widget
     */
    public static void alignCenter(HasHorizontalAlignment widget) {
        widget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    }
}
