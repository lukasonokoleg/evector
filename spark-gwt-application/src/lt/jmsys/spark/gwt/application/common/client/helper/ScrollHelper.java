package lt.jmsys.spark.gwt.application.common.client.helper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;

public class ScrollHelper {

    public static Sticker stickVertically(Element element, Element toElement) {
        return new VerticalSticker(element, toElement);
    }

    public interface Sticker extends HandlerRegistration {

        void updatePosition();
    }

    private static class VerticalSticker implements Sticker {

        private int counter;

        private JavaScriptObject handler;
        private Element element;
        private Element toElement;

        public VerticalSticker(Element element, Element toElement) {
            this.element = element;
            this.toElement = toElement;
            addScrollHandler();
        }

        private native void addScrollHandler()/*-{
			var _this = this;
			var handler = function() {
				_this.@lt.jmsys.spark.gwt.application.common.client.helper.ScrollHelper$VerticalSticker::scheduledUpdatePosition()();
			};
			_this.@lt.jmsys.spark.gwt.application.common.client.helper.ScrollHelper$VerticalSticker::handler = handler;
			//$wnd.addEventListener("scroll", handler, false);            
			if ($wnd.addEventListener) // W3C DOM
				$wnd.addEventListener("scroll", handler, false);
			else if ($wnd.attachEvent) { // IE DOM
				$wnd.attachEvent("onscroll", handler);
			} else { // No much to do
			}

        }-*/;

        @Override
        public native void removeHandler()/*-{
			var handler = this.@lt.jmsys.spark.gwt.application.common.client.helper.ScrollHelper$VerticalSticker::handler;
			//$wnd.removeEventListener("scroll", handler, false);
			if ($wnd.removeEventListener) // W3C DOM
				$wnd.removeEventListener("scroll", handler, false);
			else if ($wnd.detachEvent) { // IE DOM
				$wnd.detachEvent("onscroll", handler);
			} else { // No much to do
			}
        }-*/;

        @Override
        public void updatePosition() {
            scheduledUpdatePosition(++counter);
        }
        
        private void scheduledUpdatePosition() {
            final int c = ++counter;
            Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

                @Override
                public boolean execute() {
                    scheduledUpdatePosition(c);
                    return false;
                }
            }, 200);
        }

        private void scheduledUpdatePosition(int c) {
            if (c == counter) {
                int top = Window.getScrollTop();
                int height = element.getOffsetHeight();
                int toHeight = toElement.getOffsetHeight();
                if (top > toElement.getAbsoluteTop() && height < toHeight - 50) {
                    top = Math.min(top, toElement.getAbsoluteBottom() - height);
                    element.getStyle().setPosition(Position.ABSOLUTE);
                    element.getStyle().setTop(top, Unit.PX);
                } else {
                    element.getStyle().setPosition(Position.RELATIVE);
                    element.getStyle().setTop(0, Unit.PX);
                }
            }
        }
    }

}
