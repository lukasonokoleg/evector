package lt.jmsys.spark.gwt.application.common.server.messaging;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.server.service.ServiceGetter;
import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;


public interface EnvelopedMessage extends Message{
    Message open(ServiceGetter serviceGetter) throws SparkBusinessException;
}
