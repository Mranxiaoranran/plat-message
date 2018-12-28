package message.message.receive;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReceiveMessageHandler {

    //netty服务器核心
    private SocketIOServer server;

    @Autowired
    public ReceiveMessageHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 接收消息事件
     *
     * @param
     */
    @OnEvent(value = "receiveMessage")
    public void receiveMessage(ReceiveMessageDO receiveMessageDO) {
        String receiveUser = receiveMessageDO.getReceiveUser();
        if (!StringUtil.isNullOrEmpty(receiveUser)){
            ClientDO clientDo = StoreBas.CLIENTS.get(receiveUser);
            UUID session = clientDo.getSession();
            SocketIOClient client = server.getClient(session);
            client.sendEvent("receiveMessage", receiveMessageDO);
        }
    }
}


