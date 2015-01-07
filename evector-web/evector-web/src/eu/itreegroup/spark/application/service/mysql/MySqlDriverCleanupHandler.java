package eu.itreegroup.spark.application.service.mysql;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class MySqlDriverCleanupHandler implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            Logger.getLogger(MySqlDriverCleanupHandler.class).error("Failed to shutdown MySQL abandoned connection cleanup thread", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
    }

}
