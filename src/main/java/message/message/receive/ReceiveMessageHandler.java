package message.message.receive;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.inbox.InboxHandler;
import message.session.StoreBas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 接收消息
 */
@Component
public class ReceiveMessageHandler {

    //netty服务器核心
    private SocketIOServer server;

    @Autowired
    public ReceiveMessageHandler(SocketIOServer server) {
        this.server = server;
    }


    //日志处理
    private static final Logger log = LoggerFactory.getLogger(ReceiveMessageHandler.class);

    /**
     * 接收消息事件
     *
     * @param
     */
    public void receiveMessage(ReceiveMessageDO receiveMessageDO) {
        //获取接收用户
        String receiveUser = receiveMessageDO.getReceiveUser();
        if (!StringUtil.isNullOrEmpty(receiveUser)) {
            ClientDO clientDo = StoreBas.CLIENTS.get(receiveUser);
            if (null == clientDo) {
                log.error("用户" + receiveUser + "获取不到用户存储的信息");
            }
            UUID session = clientDo.getSession();
            if (null == clientDo) {
                log.error("用户" + receiveUser + "获取不到连接信息");
            }
            SocketIOClient client = server.getClient(session);
            if (null != client) {
                client.sendEvent("receiveMessage", receiveMessageDO);
            } else {
                log.error("获取不到用户" + receiveUser + "连接实例");
            }

        }
    }
}


