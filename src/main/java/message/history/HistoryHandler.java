package message.history;
import com.alibaba.fastjson.JSONReader;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import message.message.receive.ReceiveMessageDO;
import message.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史消息
 */
@Component
public class HistoryHandler{
    //netty服务器核心
    private SocketIOServer server;

    //日志处理
    private static final Logger log = LoggerFactory.getLogger(HistoryHandler.class);

    public  HistoryHandler(SocketIOServer server){
        this.server = server;
    }
    /**
     * 用户收件箱
     *
     * @param
     */
    @OnEvent(value = "history")
    public void history(SocketIOClient client,UserDTO userDTO) throws IOException {
        //非空处理
        if (null == userDTO) {
            log.error(" 获取历史消息时，必须传递参数");
        }
        //用户ID
        String userId = userDTO.getUserId();
        if (StringUtil.isNullOrEmpty(userId)) {
            log.error(" 获取历史消息时，userId不能为空");
        }
        //聊天存储目录
        File dir = new File("history/message");
        if(!dir.exists()){
            log.info(" 聊天消息存储文件夹未创建 ");
            return;
        }
        //每一个用户存储的聊天内容文件，用户id作为文件名 文件格式为.json
        File file = new File(dir, userId + ".json");
        if(file.exists()){
            log.info("用户历史消息文件未创建");
            return;
        }
        //每个发送用户为一个单独的key,该发送用户与本人之间的聊天信息为value
        Map<String,List<ReceiveMessageDO>> outBean = new HashMap<>();
       //使用fastjson stream api
        FileReader fileReader = new FileReader(file);
        JSONReader reader = new JSONReader(fileReader);
        //数组读取
        reader.startArray();
        while(reader.hasNext()){
            ReceiveMessageDO receiveMessageDO = reader.readObject(ReceiveMessageDO.class);
            String fromUser = receiveMessageDO.getFromUser();
            //已经放置了该发送用户的聊天信息，只需要追加
            if(outBean.containsKey(fromUser)){
                List<ReceiveMessageDO> message = outBean.get(fromUser);
                message.add(receiveMessageDO);
                outBean.put(fromUser,message);
            }else{
                //未放置该发送用户的聊天消息，需要新增
                List<ReceiveMessageDO> message = new ArrayList<>();
                message.add(receiveMessageDO);
                outBean.put(fromUser,message);
            }
        }
        //关闭流
        reader.close();
        fileReader.close();
        //向客户端发送事件
        client.sendEvent("history",outBean);
    }


}
