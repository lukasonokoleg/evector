/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.user.client.ui;

import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;

/**
 * A form of popup that has a caption area at the top and can be dragged by the
 * user. Unlike a PopupPanel, calls to {@link #setWidth(String)} and
 * {@link #setHeight(String)} will set the width and height of the dialog box
 * itself, even if a widget has not been added as yet.
 * <p>
 * <img class='gallery' src='DialogBox.png'/>
 * </p>
 * <h3>CSS Style Rules</h3>
 *
 * <ul>
 * <li>.gwt-DialogBox { the outside of the dialog }</li>
 * <li>.gwt-DialogBox .Caption { the caption }</li>
 * <li>.gwt-DialogBox .dialogContent { the wrapper around the content }</li>
 * <li>.gwt-DialogBox .dialogTopLeft { the top left cell }</li>
 * <li>.gwt-DialogBox .dialogTopLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopCenter { the top center cell, where the caption
 * is located }</li>
 * <li>.gwt-DialogBox .dialogTopCenterInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopRight { the top right cell }</li>
 * <li>.gwt-DialogBox .dialogTopRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeft { the middle left cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenter { the middle center cell, where the
 * content is located }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenterInner { the inner element of the cell }
 * </li>
 * <li>.gwt-DialogBox .dialogMiddleRight { the middle right cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeft { the bottom left cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenter { the bottom center cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenterInner { the inner element of the cell }
 * </li>
 * <li>.gwt-DialogBox .dialogBottomRight { the bottom right cell }</li>
 * <li>.gwt-DialogBox .dialogBottomRightInner { the inner element of the cell }</li>
 * </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.DialogBoxExample}
 * </p>
 */
@SuppressWarnings("deprecation")
public class TheDialogBox extends DecoratedPopupPanel implements HasHTML, HasText, MouseListener {

    /**
     * Set of characteristic interfaces supported by the {@link DialogBox} caption
     *
     * Note that this set might expand over time, so implement this interface at
     * your own risk.
     */
    public interface Caption extends HasAllMouseHandlers {
    }

    private class CaptionTitle extends HTML implements Caption {

    }

    private class CaptionImpl extends Composite {

        CaptionTitle title = new CaptionTitle();

        CaptionImpl() {
            TheConstants cnst = GWT.create(TheConstants.class);
            Anchor close = ButtonHelper.getInstance().createCloseIcon(cnst.close());
            close.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    TheDialogBox.this.hide();
                }
            });
            HorizontalPanel panel = new HorizontalPanel();
            panel.add(title);
            panel.add(close);
            panel.setWidth("100%");
            panel.setCellWidth(close, "16px");
            panel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_LEFT);
            panel.setCellHorizontalAlignment(close, HasHorizontalAlignment.ALIGN_CENTER);
            initWidget(panel);
        }
    }

    private class MouseHandler implements MouseDownHandler, MouseUpHandler, MouseOutHandler, MouseOverHandler, MouseMoveHandler {

        @Override
        public void onMouseDown(MouseDownEvent event) {
            beginDragging(event);
        }

        @Override
        public void onMouseMove(MouseMoveEvent event) {
            continueDragging(event);
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            TheDialogBox.this.onMouseLeave(caption);
        }

        @Override
        public void onMouseOver(MouseOverEvent event) {
            TheDialogBox.this.onMouseEnter(caption);
        }

        @Override
        public void onMouseUp(MouseUpEvent event) {
            endDragging(event);
        }
    }

    /**
     * The default style name.
     */
    private static final String DEFAULT_STYLENAME = "gwt-DialogBox";

    private CaptionImpl caption = new CaptionImpl();
    private boolean dragging;
    private int dragStartX, dragStartY;
    private int windowWidth;
    private int clientLeft;
    private int clientTop;
    private boolean closeOnEscEnabled = true;

    // private InactivePanel inactivePanel = new InactivePanel();

    private HandlerRegistration resizeHandlerRegistration;

    /**
     * Creates an empty dialog box. It should not be shown until its child widget
     * has been added using {@link #add(Widget)}.
     */
    public TheDialogBox() {
        this(false);
    }

    /**
     * Creates an empty dialog box specifying its "auto-hide" property. It should
     * not be shown until its child widget has been added using
     * {@link #add(Widget)}.
     *
     * @param autoHide <code>true</code> if the dialog should be automatically
     *          hidden when the user clicks outside of it
     */
    public TheDialogBox(boolean autoHide) {
        this(autoHide, true);
    }

    /**
     * Creates an empty dialog box specifying its "auto-hide" property. It should
     * not be shown until its child widget has been added using
     * {@link #add(Widget)}.
     *
     * @param autoHide <code>true</code> if the dialog should be automatically
     *          hidden when the user clicks outside of it
     * @param modal <code>true</code> if keyboard and mouse events for widgets not
     *          contained by the dialog should be ignored
     */
    public TheDialogBox(boolean autoHide, boolean modal) {
        super(autoHide, modal, "dialog");
        this.setGlassEnabled(true);
        // inactivePanel.setStylePrimaryName("spark-ModalWidget");

        // Add the caption to the top row of the decorator panel. We need to
        // logically adopt the caption so we can catch mouse events.
        Element td = getCellElement(0, 1);
        DOM.appendChild(td, caption.getElement());
        adopt(caption);
        caption.setStyleName("Caption");

        // Set the style name
        setStyleName(DEFAULT_STYLENAME);

        windowWidth = Window.getClientWidth();
        clientLeft = Document.get().getBodyOffsetLeft();
        clientTop = Document.get().getBodyOffsetTop();

        MouseHandler mouseHandler = new MouseHandler();
        addDomHandler(mouseHandler, MouseDownEvent.getType());
        addDomHandler(mouseHandler, MouseUpEvent.getType());
        addDomHandler(mouseHandler, MouseMoveEvent.getType());
        addDomHandler(mouseHandler, MouseOverEvent.getType());
        addDomHandler(mouseHandler, MouseOutEvent.getType());
    }

    /**
     * Provides access to the dialog's caption.
     *
     * This method is final because the Caption interface will expand. Therefore
     * it is highly likely that subclasses which implemented this method would end
     * up breaking.
     *
     * @return the logical caption for this dialog box
     */
    public final Caption getCaption() {
        return caption.title;
    }

    @Override
    public String getHTML() {
        return caption.title.getHTML();
    }

    @Override
    public String getText() {
        return caption.title.getText();
    }

    public boolean isCloseOnEscEnabled() {
        return closeOnEscEnabled;
    }

    public void setCloseOnEscEnabled(boolean closeOnEscEnabled) {
        this.closeOnEscEnabled = closeOnEscEnabled;
    }

    @Override
    public void hide() {
        // inactivePanel.hide();
        if (resizeHandlerRegistration != null) {
            resizeHandlerRegistration.removeHandler();
            resizeHandlerRegistration = null;
        }
        super.hide();
    }

    @Override
    public void onBrowserEvent(Event event) {
        // If we're not yet dragging, only trigger mouse events if the event occurs
        // in the caption wrapper
        switch (event.getTypeInt()) {
            case Event.ONMOUSEDOWN:
            case Event.ONMOUSEUP:
            case Event.ONMOUSEMOVE:
            case Event.ONMOUSEOVER:
            case Event.ONMOUSEOUT:
                if (!dragging && !isCaptionEvent(event)) {
                    return;
                }
        }

        super.onBrowserEvent(event);
    }

    /**
     * @deprecated Use {@link #beginDragging} and {@link #getCaption}
     *             instead
     */
    @Override
    @Deprecated
    public void onMouseDown(Widget sender, int x, int y) {
        dragging = true;
        DOM.setCapture(getElement());
        dragStartX = x;
        dragStartY = y;
    }

    /**
     * @deprecated Use {@link Caption#addMouseOverHandler} instead
     */
    @Override
    @Deprecated
    public void onMouseEnter(Widget sender) {
    }

    /**
     * @deprecated Use {@link Caption#addMouseOutHandler} instead
     */
    @Override
    @Deprecated
    public void onMouseLeave(Widget sender) {
    }

    /**
     * @deprecated Use {@link #continueDragging} and {@link #getCaption} instead
     */
    @Override
    @Deprecated
    public void onMouseMove(Widget sender, int x, int y) {
        if (dragging) {
            int absX = x + getAbsoluteLeft();
            int absY = y + getAbsoluteTop();

            // if the mouse is off the screen to the left, right, or top, don't
            // move the dialog box. This would let users lose dialog boxes, which
            // would be bad for modal popups.
            if (absX < clientLeft || absX >= windowWidth || absY < clientTop) {
                return;
            }

            setPopupPosition(absX - dragStartX, absY - dragStartY);
        }
    }

    /**
     * @deprecated Use {@link #endDragging} and {@link #getCaption} instead
     */
    @Override
    @Deprecated
    public void onMouseUp(Widget sender, int x, int y) {
        dragging = false;
        DOM.releaseCapture(getElement());
    }

    /**
     * Sets the html string inside the caption.
     *
     * Use {@link #setWidget(Widget)} to set the contents inside the
     * {@link DialogBox}.
     *
     * @param html the object's new HTML
     */
    @Override
    public void setHTML(String html) {
        caption.title.setHTML(html);
    }

    /**
     * Sets the text inside the caption.
     *
     * Use {@link #setWidget(Widget)} to set the contents inside the
     * {@link DialogBox}.
     *
     * @param text the object's new text
     */
    @Override
    public void setText(String text) {
        caption.title.setText(text);
    }

    @Override
    public void show() {
        // inactivePanel.show();
        if (resizeHandlerRegistration == null) {
            resizeHandlerRegistration = Window.addResizeHandler(new ResizeHandler() {

                @Override
                public void onResize(ResizeEvent event) {
                    windowWidth = event.getWidth();
                }
            });
        }
        super.show();
    }

    /**
     * Called on mouse down in the caption area, begins the dragging loop by
     * turning on event capture.
     *
     * @see DOM#setCapture
     * @see #continueDragging
     * @param event the mouse down event that triggered dragging
     */
    protected void beginDragging(MouseDownEvent event) {
        onMouseDown(caption, event.getX(), event.getY());
    }

    /**
     * Called on mouse move in the caption area, continues dragging if it was
     * started by {@link #beginDragging}.
     *
     * @see #beginDragging
     * @see #endDragging
     * @param event the mouse move event that continues dragging
     */
    protected void continueDragging(MouseMoveEvent event) {
        onMouseMove(caption, event.getX(), event.getY());
    }

    @Override
    protected void doAttachChildren() {
        super.doAttachChildren();

        // See comment in doDetachChildren for an explanation of this call
        caption.onAttach();
    }

    @Override
    protected void doDetachChildren() {
        super.doDetachChildren();

        // We need to detach the caption specifically because it is not part of the
        // iterator of Widgets that the {@link SimplePanel} super class returns.
        // This is similar to a {@link ComplexPanel}, but we do not want to expose
        // the caption widget, as its just an internal implementation.
        caption.onDetach();
    }

    /**
     * Called on mouse up in the caption area, ends dragging by ending event
     * capture.
     *
     * @param event the mouse up event that ended dragging
     *
     * @see DOM#releaseCapture
     * @see #beginDragging
     * @see #endDragging
     */
    protected void endDragging(MouseUpEvent event) {
        onMouseUp(caption, event.getX(), event.getY());
    }

    /**
     * <b>Affected Elements:</b>
     * <ul>
     * <li>-caption = text at the top of the {@link DialogBox}.</li>
     * <li>-content = the container around the content.</li>
     * </ul>
     *
     * @see UIObject#onEnsureDebugId(String)
     */
    @Override
    protected void onEnsureDebugId(String baseID) {
        super.onEnsureDebugId(baseID);
        caption.ensureDebugId(baseID + "-caption");
        ensureDebugId(getCellElement(1, 1), baseID, "content");
    }

    @Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        // We need to preventDefault() on mouseDown events (outside of the
        // DialogBox content) to keep text from being selected when it
        // is dragged.
        NativeEvent nativeEvent = event.getNativeEvent();

        if (!event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN) && isCaptionEvent(nativeEvent)) {
            nativeEvent.preventDefault();
        }

        if (isCloseOnEscEnabled() && event.getTypeInt() == Event.ONKEYDOWN && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
            hide();
        }
    }

    private boolean isCaptionEvent(NativeEvent event) {
        EventTarget target = event.getEventTarget();
        if (Element.is(target)) {
            return getCellElement(0, 1).getParentElement().isOrHasChild(Element.as(target));
        }
        return false;
    }

    public void showRelativeTo(com.google.gwt.dom.client.Element element) {
        showRelativeTo(new ElementWrapper(element));
    }

    private static class ElementWrapper extends UIObject {

        public ElementWrapper(com.google.gwt.dom.client.Element e) {
            setElement(e); // setElement() is protected, so we have to subclass and call here
        }
    }
}
