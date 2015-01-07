package lt.jmsys.spark.gwt.application.common.shared.websocket;

public class NotificationSubscription {

    String action;
    String topic;

    public NotificationSubscription(String action, String topic) {
        this.action = action;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getAction() {
        return action;
    }

    public String toStringValue() {
        return action + "," + topic;
    }

    public static NotificationSubscription valueOf(String subscription) {
        String[] s = subscription.split(",");
        return new NotificationSubscription(s[0], s[1]);
    }

}