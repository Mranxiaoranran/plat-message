package message.inbox;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.connector.Connector;
import message.message.receive.ReceiveMessageDO;
import message.redis.RedisClient;
import message.session.StoreBas;
import message.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 收件箱
 */
@Component
public class InboxHandler {
    //netty服务器核心
    private SocketIOServer server;
    //redis客户端
    private RedisClient redisClient;
    //日志处理
    private static final Logger log = LoggerFactory.getLogger(InboxHandler.class);


    public InboxHandler(SocketIOServer server,RedisClient redisClient){
        this.server = server;
        this.redisClient = redisClient;
    }

    /**
     * 用户收件箱
     *
     * @param
     */
    @OnEvent(value = "inbox")
    public void inbox(UserDTO  userDTO) {
        //非空处理
        if(null == userDTO){
            log.error("    ");
        }
    }



}
