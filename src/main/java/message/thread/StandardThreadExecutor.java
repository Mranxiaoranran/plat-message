package message.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class StandardThreadExecutor implements Executor{


    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
