package message.connector;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.session.StoreBas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Connector {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    /**
     * netty服务器核心
     */
    private SocketIOServer server;

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
        //获取连接的唯一标识,sid
        UUID session = client.getSessionId();
        if (null == session) {
            log.error(" sid 不能为空，本次连接关闭 ");
            client.disconnect();
            return;
        }
        //业务用户标识
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        if (StringUtil.isNullOrEmpty(userId)) {
            log.error("userId不能为空,本次连接关闭");
            client.disconnect();
            return;
        }
        //用户未在消息服务器登录过，即第一次登录
        if (!StoreBas.CLIENTS.containsKey(userId)) {
            ClientDO clientDO = new ClientDO();
            //设置登录状态
            clientDO.setOnline(true);
            //设置连接sid
            clientDO.setSession(session);
            //设置最新登录时间
            clientDO.setLastConnectedTime(new Date());
            //将会话信息更新保存至集合中
            StoreBas.CLIENTS.put(userId, clientDO);
            log.info("用户未在消息服务器登录过，即第一次登录 userId为" + userId);
        } else {
            ClientDO clientDo = StoreBas.CLIENTS.get(userId);
            //特殊情况处理，虽然登录过，但是用户信息存在问题
            if (null == clientDo) {
                clientDo = new ClientDO();
                //设置登录状态
                clientDo.setOnline(true);
                //设置连接sid
                clientDo.setSession(session);
                //设置最新登录时间
                clientDo.setLastConnectedTime(new Date());
                //将会话信息更新保存至集合中
                StoreBas.CLIENTS.put(userId, clientDo);
            } else {
                //正常情况下,用户曾经连接过消息服务器,但是再一次登录，也分为两种情况
                //同一个浏览器在不同个窗口打开
                UUID lastSession = clientDo.getSession();
                if (session.equals(lastSession)) {
                    //设置登录状态
                    clientDo.setOnline(true);
                    //设置最新登录时间
                    clientDo.setLastConnectedTime(new Date());
                    //将会话信息更新保存至集合中
                    StoreBas.CLIENTS.put(userId, clientDo);
                    log.info("同一个浏览器在不同个窗口打开 userId为" + userId);
                } else {
                    // 情况二  同一个用户是在不同的浏览器登录
                    log.info("同一个用户是在不同的浏览器登录 用户userId是" + userId);
                    //清除存储的连接，并断开连接，实现单点登录
                    if (null != lastSession) {
                        SocketIOClient lastClient = server.getClient(lastSession);
                        if(null != lastClient){
                            lastClient.disconnect();
                        }
                        log.info(" 清除存储的连接，并断开连接，实现单点登录  ");
                    }
                    //重新为这次浏览器客户端设置登录状态
                    clientDo.setOnline(true);
                    //设置连接sid
                    clientDo.setSession(session);
                    //设置最新登录时间
                    clientDo.setLastConnectedTime(new Date());
                    //将会话信息更新保存至集合中
                    StoreBas.CLIENTS.put(userId, clientDo);
                }
            }
        }
        log.info("用户" + userId + "登录成功当前在线人数为" + StoreBas.CLIENTS.size());
    }


    /**
     * disconnect事件处理，当客户端断开连接时将调用
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        /**
         * 只处理通过业务方式连接消息服务器的请求
         */
        if (!StringUtil.isNullOrEmpty(userId)){
            log.info("用户" + userId + "退出登录" + "当前在线人数为" + StoreBas.CLIENTS.size());
        }
    }

}