package lt.jmsys.spark.gwt.application.common.shared.websocket;

public interface WebSocketCommand {

    String PING = "ping";
    String PONG = "pong";

    String TOPIC = "topic:";    
    String STATS = "stats:";
}
