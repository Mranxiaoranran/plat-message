package message.user;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import message.client.ClientDo;
import message.message.MessageDO;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserHandler {

    //netty服务器核心
    private SocketIOServer server;

    @Autowired
    public UserHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     *
     * @param client
     * @param request
     * @param userDO
     */
    @OnEvent(value = "user")
    public void onEvent(SocketIOClient client, AckRequest request, UserDO userDO) {
        ClientDo clientDo = StoreBas.CLIENTS.get(userDO.getUserId());
        if (clientDo != null && clientDo.isOnline()) {
            UUID target = client.getSessionId();
            System.out.println("目标会话UUID:" + target);
            // 向当前会话发送信息
            client.sendEvent("message", StoreBas.CLIENTS);
        }
    }


}
