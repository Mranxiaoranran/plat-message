package message.message;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sun.nio.sctp.MessageInfo;
import message.client.ClientDo;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MessageHandler{

    //netty服务器核心
    private SocketIOServer server;

    @Autowired
    public MessageHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     * @param client
     * @param request
     * @param messageDO
     */
    @OnEvent(value = "message")
    public void onEvent(SocketIOClient client, AckRequest request, MessageDO messageDO){
        ClientDo clientDo = StoreBas.CLIENTS.get(messageDO.getToUser());
        if (clientDo != null && clientDo.isOnline()){
            UUID target = new UUID(clientDo.getMostSignificantBits(), clientDo.getLeastSignificantBits());
            System.out.println("目标会话UUID:"+target);
            // 向当前会话发送信息
            client.sendEvent("message", messageDO);
            // 向目标会话发送信息
            server.getClient(target).sendEvent("message", messageDO);
        }
    }




}
