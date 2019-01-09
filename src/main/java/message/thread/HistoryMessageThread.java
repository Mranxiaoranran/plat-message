package message.thread;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.util.IOUtils;
import message.inbox.InboxHandler;
import message.message.receive.ReceiveMessageDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史消息存储线程
 */
public class HistoryMessageThread implements Runnable {


    //日志处理
    private static final Logger log = LoggerFactory.getLogger(HistoryMessageThread.class);

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 接收用户消息
     */
    private ReceiveMessageDO receiveMessageDO;

    /**
     * 根据用户ID，消息创建线程实例
     *
     * @param userId
     * @param receiveMessageDO
     */
    public HistoryMessageThread(String userId, ReceiveMessageDO receiveMessageDO) {
        this.userId = userId;
        this.receiveMessageDO = receiveMessageDO;
    }

    /**
     * 线程执行方法
     */
    @Override
    public void run() {
        //历史消息存储目录
        File dir = new File("history/message");
        //如果文件不存在，创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //用户历史消息存储文件
        File file = new File(dir, userId + ".json");
        //当该用户对应的历史文件不存在，则直接创建文件，将消息写入
        if (!file.exists()) {
            try {
                //创建文件
                file.createNewFile();
                //writer文件流
                Writer writer = new FileWriter(file, false);
                //fastjson stream api
                JSONWriter jsonWriter = new JSONWriter(writer);
                //写入的为数租
                jsonWriter.startArray();
                jsonWriter.writeValue(receiveMessageDO);
                jsonWriter.endArray();
                //关闭流
                jsonWriter.close();
                writer.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            //如果用户对应历史消息文件存在，则先将历史消息文件读取，追加新的聊天记录再次写入
            try {
                //文件中历史消息
                List<ReceiveMessageDO> messages = new ArrayList<>();
                //fastjson stream api
                FileReader fileReader = new FileReader(file);
                JSONReader reader = new JSONReader(fileReader);
                reader.startArray();
                //循环读取
                while (reader.hasNext()) {
                    messages.add(reader.readObject(ReceiveMessageDO.class));
                }
                //关闭流文件
                reader.close();
                fileReader.close();
                //追加内容
                messages.add(receiveMessageDO);
                //删除源文件
                file.delete();
                //创建新文件
                file.createNewFile();
                //fastjson stream api 再次写入
                Writer writer = new FileWriter(file, false);
                JSONWriter jsonWriter = new JSONWriter(writer);
                jsonWriter.startArray();
                for (ReceiveMessageDO messageDO : messages) {
                    jsonWriter.writeValue(messageDO);
                }
                jsonWriter.endArray();
                //关闭流
                jsonWriter.close();
                writer.close();
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }


    }
}
