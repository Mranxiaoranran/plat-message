package message.thread;

import java.util.concurrent.Executor;

public interface ResizableExecutor extends Executor {


    public int getPoolSize();

    public int getMaxThreads();

    public int getActiveCount();

    public boolean resizePool(int corePoolSize, int maximumPoolSize);

    public boolean resizeQueue(int capacity);


}
