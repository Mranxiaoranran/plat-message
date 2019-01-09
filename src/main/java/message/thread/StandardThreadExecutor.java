package message.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class StandardThreadExecutor implements Executor, ResizableExecutor{


    /**
     * Default thread priority
     */
    protected int threadPriority = Thread.NORM_PRIORITY;

    /**
     * Run threads in daemon or non-daemon state
     */
    protected boolean daemon = true;


    /**
     * max number of threads
     */
    protected int maxThreads = 200;

    /**
     * min number of threads
     */
    protected int minSpareThreads = 25;

    /**
     * idle time in milliseconds
     */
    protected int maxIdleTime = 60000;

    /**
     * The executor we use for this component
     */
    protected ThreadPoolExecutor executor = null;

    /**
     * the name of this thread pool
     */
    protected String name;

    /**
     * prestart threads?
     */
    protected boolean prestartminSpareThreads = false;

    /**
     * The maximum number of elements that can queue up before we reject them
     */
    protected int maxQueueSize = Integer.MAX_VALUE;



    private TaskQueue taskqueue = null;


    @Override
    public int getPoolSize() {
        return 0;
    }

    @Override
    public int getMaxThreads() {
        return 0;
    }

    @Override
    public int getActiveCount() {
        return 0;
    }

    @Override
    public boolean resizePool(int corePoolSize, int maximumPoolSize) {
        return false;
    }

    @Override
    public boolean resizeQueue(int capacity) {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
