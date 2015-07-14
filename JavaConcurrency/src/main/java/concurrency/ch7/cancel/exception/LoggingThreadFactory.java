package concurrency.ch7.cancel.exception;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingThreadFactory implements ThreadFactory
{
    
    @Override
    public Thread newThread(Runnable r)
    {
        Thread t = new Thread(r);

        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
            	
               Logger logger =  LoggerFactory.getLogger(t.getName());
               logger.error("Exception Handled by UncaughtExceptionHandler!");
               logger.error(e.getMessage(), e);
            }
        });

        return t;
    }
}