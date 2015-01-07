package lt.jmsys.spark.gwt.application.server.quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.server.messaging.EnvelopedMessage;
import lt.jmsys.spark.gwt.application.common.server.service.ServiceGetter;
import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;
import lt.jmsys.spark.gwt.application.shared.messaging.MessageCountMessage;

public class MessageCountChecker implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO:
        /*        try {
                    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

                    JobDetail job = JobBuilder.newJob(CheckerJob.class).withIdentity("MessageCountCheckerJob", "ALL001").build();

                    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("MessageCountCheckerTrigger", "ALL001").startNow()
                            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(300).repeatForever()).build();

                    scheduler.scheduleJob(job, trigger);

                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }*/

    }

    /*    public static class CheckerJob implements Job {

            @Override
            public void execute(JobExecutionContext arg0) throws JobExecutionException {
                MessageQueueManager.getInstance().getQueue(null, null).add(new MessageCountEnvelope());
            }
        }*/

    public static class MessageCountEnvelope extends MessageCountMessage implements EnvelopedMessage {

        private static final long serialVersionUID = 1L;

        @Override
        public Message open(ServiceGetter serviceGetter) throws SparkBusinessException {
            /*            Als_msg service = serviceGetter.getServiceImplAuth(Als_msg.class);
                        Spr_paging_ot paging = new Spr_paging_ot(1000d, 0d, 1000d, null);
                        InOut<Spr_paging_ot> p_paging = new InOut<Spr_paging_ot>(paging);
                        List<Find_messages> messages = service.find_messages(p_paging, null, null, null, Als_YesNo.YES);
                        int c;
                        if (null != messages){
                            c = messages.size();
                        }else{
                            c = 0;
                        }*/
            // TODO
            int c = 1;
            return new MessageCountMessage(c);
        }

        @Override
        public String getMessageTag() {
            return MessageCountEnvelope.class.getName();
        }
    }
}
