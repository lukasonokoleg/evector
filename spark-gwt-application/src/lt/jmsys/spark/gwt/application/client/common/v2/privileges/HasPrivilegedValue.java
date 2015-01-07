package lt.jmsys.spark.gwt.application.client.common.v2.privileges;

public interface HasPrivilegedValue<T> {

    T getValue();

    Void getPrivileges();
}
