package lt.jmsys.spark.gwt.application.common.server.messaging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MessageQueueResourcesManager implements HttpSessionListener, ServletContextListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        /*        SessionInfo sessionInfo = SessionServiceImpl.getSessionInfo(event.getSession());
                if (null != sessionInfo) {            
                    MessageQueueManager.getInstance().removeQueue(sessionInfo.getUser_id(), sessionInfo.getNotificationId());
                }*/
    }

    // private Scheduler scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        /*        try {
                    scheduler.shutdown();
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }*/
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        /*        try {
                    scheduler = StdSchedulerFactory.getDefaultScheduler();
                    scheduler.start();
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }*/
    }

}
