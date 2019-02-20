package message.group;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import message.history.HistoryHandler;
import message.io.json.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * 群组事件
 */
public class GroupHandler{

    //netty服务器核心
    private SocketIOServer server;

    //日志处理
    private static final Logger log = LoggerFactory.getLogger(GroupHandler.class);

    public GroupHandler(SocketIOServer server){
        this.server = server;
    }

    /**
     * 创建一个群组
     */
    @OnEvent("addGroup")
    public void addGroup(GroupDTO groupDTO) throws IOException {
        if(null ==groupDTO){
            log.error(" 创建群组时，必须传递参数 ");
            return;
        }
        //群组名称
        String groupName = groupDTO.getGroupName();
        //年月日
        String date = new DateFormatter().print(new Date(), Locale.SIMPLIFIED_CHINESE);
        //随机数
        String  uuid = UUID.randomUUID().toString();
        //目录
        File dir  = new File("group");
        if(!dir.exists()){
            dir.mkdir();
        }
        String fileName = new StringBuffer().append(groupName)
                                .append("-").append(date)
                                .append("-").append(uuid)
                                .toString();
        File file = new File(dir,fileName);
        JsonWriter<GroupDTO> writer = new JsonWriter();
        writer.write(file,groupDTO);
    }




}
