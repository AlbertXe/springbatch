package hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloSheduler  {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1").build();
        Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);

    }
}

