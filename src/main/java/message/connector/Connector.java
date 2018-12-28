package message.connector;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.message.receive.ReceiveMessageDO;
import message.message.receive.ReceiveMessageHandler;
import message.redis.RedisClient;
import message.session.StoreBas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Connector {

    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    //netty服务器核心
    private SocketIOServer server;

    private RedisClient redisClient;

    private ReceiveMessageHandler receiveMessageHandler;

    //在线人数
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Autowired
    public Connector(SocketIOServer server, RedisClient redisClient, ReceiveMessageHandler receiveMessageHandler) {
        this.server = server;
        this.redisClient = redisClient;
        this.receiveMessageHandler = receiveMessageHandler;
    }

    /**
     * connect事件处理，当客户端发起连接时将调用
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        //业务上连接需要处理的问题
        connect(client);
        //拉取收件箱信息
        inbox(client);
    }

    /**
     * disconnect事件处理，当客户端断开连接时将调用
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("userId");
        UUID session = client.getSessionId();
        //通过非业务方法断开连接
        if (StringUtil.isNullOrEmpty(clientId)) {
            //通过连接与客户信息获取用户信息
            clientId = StoreBas.CONNECTIONS.get(session);
            if (!StringUtil.isNullOrEmpty(clientId)) {
                //连接数与客户关系移除
                StoreBas.CONNECTIONS.remove(session);
                //通过业务方法断开连接
                StoreBas.CLIENTS.remove(clientId);
                //在线数减1
                log.info("socket 断开连接、sessionId:" + client.getSessionId() + "、userId:" + clientId + "、当前连接数：" + onlineCount.decrementAndGet());
            }
        } else {
            //通过业务方法断开连接
            StoreBas.CLIENTS.remove(clientId);
            //在线数减1
            log.info("socket 断开连接、sessionId:" + client.getSessionId() + "、userId:" + clientId + "、当前连接数：" + onlineCount.decrementAndGet());
        }
    }

    /**
     * 业务相关连接处理
     *
     * @param client
     */
    protected void connect(SocketIOClient client) {
        //获取连接的唯一标识
        UUID session = client.getSessionId();
        //业务用户标识
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        //用户信息
        ClientDO clientDo = StoreBas.CLIENTS.get(userId);
        // 如果没有连接信息、则新建会话信息
        if (clientDo == null) {
            clientDo = new ClientDO();
            //设置在线
            clientDo.setOnline(true);
            //在线数加1
            log.info("socket 建立新连接、sessionId:" + session + "、userId:" + userId + "、当前连接数：" + onlineCount.incrementAndGet());
        } else {
            log.info(" socket连接回复: " + userId);
        }
        // 更新设置客户端连接信息
        clientDo.setSession(session);
        clientDo.setLastConnectedTime(new Date());
        //将会话信息更新保存至集合中
        StoreBas.CLIENTS.put(userId, clientDo);
        StoreBas.CONNECTIONS.put(session, userId);
    }

    protected void inbox(SocketIOClient client) {
        //业务用户标识
        String clientId = client.getHandshakeData().getSingleUrlParam("userId");
        //拉取收件箱信息
        Set<Object> inbox = redisClient.zRange(clientId);
        if (!inbox.isEmpty()) {
            for (Object obj : inbox) {
                if (null == obj) {
                    ReceiveMessageDO receiveMessageDO = (ReceiveMessageDO) obj;
                    receiveMessageHandler.receiveMessage(receiveMessageDO);
                }
            }
            //将收件箱清空
            redisClient.zRem(clientId);
        }
    }
}