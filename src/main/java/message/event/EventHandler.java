package message.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import message.client.ClientDo;
import message.message.ReceiveMessageDO;
import message.message.SendMessageDO;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventHandler {
    //netty服务器核心
    private SocketIOServer server;

    @Autowired
    public EventHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 发送事件
     */
    public void sendEvent(String userId) {
        ClientDo clientDo = StoreBas.CLIENTS.get(userId);
        SocketIOClient client = server.getClient(clientDo.getSession());
        client.sendEvent("event", userId);
    }
}


