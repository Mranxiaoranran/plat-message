package message.event.listener;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.client.ClientDo;
import message.event.EventDTO;
import message.event.EventHandler;
import message.message.ReceiveMessageDO;
import message.session.StoreBas;
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
