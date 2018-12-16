package message.message.receive;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDo;
import message.message.SendMessageDO;
import message.session.StoreBas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
     * 接收消息方法
     *
     * @param
     */
    public void receiveMessage(ReceiveMessageDO receiveMessageDO) {
        String receiveUser = receiveMessageDO.getReceiveUser();
        if (!StringUtil.isNullOrEmpty(receiveUser)) {
            ClientDo clientDo = StoreBas.CLIENTS.get(receiveUser);
            //在线时发送即时消息
            if (clientDo.isOnline()) {
                UUID session = clientDo.getSession();
                SocketIOClient client = server.getClient(session);
            } else {
                //不在线时放入收件箱
                Date date = new Date();
                
            }
        }



    }


}
