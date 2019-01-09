package message.inbox;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.connector.Connector;
import message.message.receive.ReceiveMessageDO;
import message.message.receive.ReceiveMessageHandler;
import message.redis.RedisClient;
import message.session.StoreBas;
import message.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;
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

    private ReceiveMessageHandler receiveMessageHandler;

    //日志处理
    private static final Logger log = LoggerFactory.getLogger(InboxHandler.class);


    public InboxHandler(SocketIOServer server, RedisClient redisClient, ReceiveMessageHandler receiveMessageHandler) {
        this.server = server;
        this.redisClient = redisClient;
        this.receiveMessageHandler = receiveMessageHandler;
    }

    /**
     * 用户收件箱
     *
     * @param
     */
    @OnEvent(value = "inbox")
    public void inbox(UserDTO userDTO) {
        //非空处理
        if (null == userDTO) {
            log.error(" 拉取用户收件箱时，必须传递参数");
        }
        String userId = userDTO.getUserId();
        if (StringUtil.isNullOrEmpty(userId)) {
            log.error(" 拉取用户收件箱时，userId不能为空");
        }
        //用户对应收件箱key
        String key = new StringBuffer("INBOX:").append(userId).toString();
        //用户收件箱
        Set<Object> index = redisClient.zRange(key);
        if(index.isEmpty()){
            log.info("用户"+userId +"收件箱无内容");
            return;
        }else{
            //循环向用户发送消息
            for (Object obj : index) {
                String json = (String) obj;
                ReceiveMessageDO receiveMessageDO = JSONObject.parseObject(json, ReceiveMessageDO.class);
                receiveMessageHandler.receiveMessage(receiveMessageDO);
            }
            log.info("用户" + userId + " 拉取收件箱成功");
            //清空用户收件箱
            redisClient.zRem(key);
            log.info("用户" + userId + " 收件箱清空");
        }



    }


}
