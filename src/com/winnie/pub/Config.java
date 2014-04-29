package com.winnie.pub;


import com.winnie.pub.utils.PropertyUtil;
import com.winnie.quartz.BBSJob;
import org.apache.log4j.Logger;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.quartz.*;

import java.io.File;
import java.io.IOException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-11
 * Time: ÉÏÎç2:07
 * To change this template use File | Settings | File Templates.
 */
public class Config implements Setup {
    Logger logger = Logger.getLogger(Config.class);
    public static PropertyUtil pu = new PropertyUtil("chrome");
    private static ChromeDriverService chromeDriverService;
    private static InternetExplorerDriverService ieDriverService;

    public static ChromeDriverService getChromeDriverService() {
        //System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, );
        if (chromeDriverService != null) return chromeDriverService;
        try {
            chromeDriverService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(pu.getValue("chromedriver")))
                    .usingAnyFreePort()
                    .build();
            chromeDriverService.start();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return chromeDriverService;
    }

    public static InternetExplorerDriverService getIEDriverService() {
        if (ieDriverService != null) return ieDriverService;
        try {

            ieDriverService = new InternetExplorerDriverService.Builder()
                    .usingDriverExecutable(new File(pu.getValue("iedriver")))
                    .usingAnyFreePort()
                    .build();

            ieDriverService.start();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ieDriverService;
    }

    public void init(NutConfig config) {
        logger.debug("ioc init");
        IocFactory.setIoc(config.getIoc());

        startTBKJob();
    }

    public void startTBKJob() {
        //job start
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched = null;
        try {
            sched = schedFact.getScheduler();


            sched.start();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(BBSJob.class)
                    .withIdentity("myJob", "group1")
                    .build();

            // Trigger the job to run now, and then every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void destroy(NutConfig config) {
        chromeDriverService.stop();
    }
}