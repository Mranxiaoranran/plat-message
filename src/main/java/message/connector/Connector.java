package message.connector;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import io.netty.util.internal.StringUtil;
import message.client.ClientDo;
import message.session.StoreBas;
import message.user.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Connector {
    //netty服务器核心
    private SocketIOServer server;


    //在线人数
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Autowired
    public Connector(SocketIOServer server) {
        this.server = server;
    }

    /**
     * connect事件处理，当客户端发起连接时将调用
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientId");
        UUID session = client.getSessionId();
        ClientDo clientDo = StoreBas.CLIENTS.get(clientId);
        // 如果没有连接信息、则新建会话信息
        if (clientDo == null) {
            clientDo = new ClientDo();
            //设置在线
            clientDo.setOnline(true);
            //在线数加1
            System.out.println("socket 建立新连接、sessionId:" + session + "、clientId:" + clientId + "、当前连接数：" + onlineCount.incrementAndGet());
        } else {
            System.out.println(" socket连接回复: " + clientId);
        }
        // 更新设置客户端连接信息
        clientDo.setSession(session);
        clientDo.setLastConnectedTime(new Date());
        //将会话信息更新保存至集合中
        StoreBas.CLIENTS.put(clientId, clientDo);
        StoreBas.CONNECTIONS.put(session, clientId);
        //设置客户信息
        UserDO userDO = new UserDO();
        userDO.setUserId(clientId);
        StoreBas.USERS.add(userDO);
    }


    /**
     * disconnect事件处理，当客户端断开连接时将调用
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
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
                System.out.println("socket 断开连接、sessionId:" + client.getSessionId() + "、clientId:" + clientId + "、当前连接数：" + onlineCount.decrementAndGet());
            }
        } else {
            //通过业务方法断开连接
            StoreBas.CLIENTS.remove(clientId);
            //在线数减1
            System.out.println("socket 断开连接、sessionId:" + client.getSessionId() + "、clientId:" + clientId + "、当前连接数：" + onlineCount.decrementAndGet());
        }
    }


}
