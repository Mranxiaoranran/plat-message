package message.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import message.client.ClientDO;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        ClientDO clientDo = StoreBas.CLIENTS.get(userId);
        SocketIOClient client = server.getClient(clientDo.getSession());
        client.sendEvent("event", userId);
    }
}


