package message.message.send;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.client.ClientDO;
import message.connector.Connector;
import message.message.receive.ReceiveMessageDO;
import message.message.receive.ReceiveMessageHandler;
import message.redis.RedisClient;
import message.session.StoreBas;
import message.thread.HistoryMessageThread;
import message.thread.StandardThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 发送消息
 * <p>
 * 用户向消息服务器的 发送消息事件发起事件 ===》 用户是否在线
 */
@Component
public class SendMessageHandler {

    //netty服务器核心
    private SocketIOServer server;


    private ReceiveMessageHandler receiveMessageHandler;


    private RedisClient redisClient;

    private StandardThreadExecutor standardThreadExecutor;

    private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

    @Autowired
    public SendMessageHandler(SocketIOServer server, ReceiveMessageHandler receiveMessageHandler, RedisClient redisClient,StandardThreadExecutor standardThreadExecutor) {
        this.server = server;
        this.receiveMessageHandler = receiveMessageHandler;
        this.redisClient = redisClient;
        this.standardThreadExecutor = standardThreadExecutor;
    }

    @OnEvent("sendMessage")
    public void sendMessage(SendMessageDO sendMessageDO) {
        String receiveUser = sendMessageDO.getReceiveUser();
        //接收用户不能为空或者为空字符串
        if (!StringUtil.isNullOrEmpty(receiveUser)) {
            //历史消息线程
            HistoryMessageThread historyMessageThread = new HistoryMessageThread(receiveUser,new ReceiveMessageDO(sendMessageDO));
            standardThreadExecutor.execute(historyMessageThread);
            ClientDO clientDo = StoreBas.CLIENTS.get(receiveUser);
            //此时会有此人未登录过消息服务器的情况，初始化数据
            if (null == clientDo) {
                clientDo = new ClientDO();
                //设置在线
                clientDo.setOnline(false);
                //放置消息服务器中
                StoreBas.CLIENTS.put(receiveUser, clientDo);
            }
            ReceiveMessageDO receiveMessageDO = new ReceiveMessageDO(sendMessageDO);
            UUID session = clientDo.getSession();
            //如果在线，直接发送消息
            if (clientDo.isOnline() && null != session && null != server.getClient(session)) {
                receiveMessageHandler.receiveMessage(receiveMessageDO);
                log.info("用户" + receiveMessageDO.getFromUser() + "向用户" + receiveMessageDO.getReceiveUser() + "发送消息" + receiveMessageDO.getMessage());
            } else {
                //不在线，放入用户收件箱
                String key = new StringBuffer("INBOX:").append(receiveMessageDO.getReceiveUser()).toString();
                //消息内容
                String value = JSONObject.toJSONString(receiveMessageDO);
                //score
                Long score = new Date().getTime();
                //放入收件箱
                redisClient.zAdd(key, value, score);
                //日志
                log.info("用户" + receiveMessageDO.getReceiveUser() + "不在线，收件箱新增一条消息");
            }
        }
    }

}

