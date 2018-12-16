package message.event.listener;

import message.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedisEventHandler {

    //netty服务器核心
    @Autowired
    private EventHandler eventHandler;


    public void receiveMessage(String json) throws IOException {
        eventHandler.sendEvent(json);
    }


}
