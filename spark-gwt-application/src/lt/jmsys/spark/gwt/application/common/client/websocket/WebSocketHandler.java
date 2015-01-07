package lt.jmsys.spark.gwt.application.common.client.websocket;

public interface WebSocketHandler {

    void onMessage(String message);
    void onOpen();
    void onClose();
}