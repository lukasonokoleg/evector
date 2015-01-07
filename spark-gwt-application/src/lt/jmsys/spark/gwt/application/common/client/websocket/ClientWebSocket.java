package lt.jmsys.spark.gwt.application.common.client.websocket;

import com.google.gwt.core.client.JavaScriptObject;

public class ClientWebSocket {

    private WebSocketHandler messageHandler;
    private JavaScriptObject webSocket;

    public ClientWebSocket(String url, WebSocketHandler messageHandler) {
        this.messageHandler = messageHandler;
        if (!isSupported()) {
            throw new RuntimeException("WebSockets are not supported by this client");
        }
        init(url);
    }

    private native void init(String url)/*-{
		var webSocket = null;
		if ($wnd.WebSocket) {
			webSocket = new $wnd.WebSocket(url, "chat");
		} else if ($wnd.MozWebSocket) {
			webSocket = new $wnd.MozWebSocket(url, "chat");
		}		
		var _this = this;        
		webSocket.onopen = function() {
			_this.@lt.jmsys.spark.gwt.application.common.client.websocket.ClientWebSocket::messageHandler.@lt.jmsys.spark.gwt.application.common.client.websocket.WebSocketHandler::onOpen()();
		}
		webSocket.onmessage = function(message) {
			_this.@lt.jmsys.spark.gwt.application.common.client.websocket.ClientWebSocket::messageHandler.@lt.jmsys.spark.gwt.application.common.client.websocket.WebSocketHandler::onMessage(Ljava/lang/String;)(message.data);
		}
		webSocket.onclose = function(message) {
			_this.@lt.jmsys.spark.gwt.application.common.client.websocket.ClientWebSocket::messageHandler.@lt.jmsys.spark.gwt.application.common.client.websocket.WebSocketHandler::onClose()();
		}
		this.@lt.jmsys.spark.gwt.application.common.client.websocket.ClientWebSocket::webSocket = webSocket;
    }-*/;

    public native void send(String message)/*-{
		this.@lt.jmsys.spark.gwt.application.common.client.websocket.ClientWebSocket::webSocket
				.send(message);
    }-*/;

    public static native boolean isSupported()/*-{
		if ($wnd.WebSocket || $wnd.MozWebSocket) {
			return true;
		} else {
			return false;
		}
    }-*/;

}
