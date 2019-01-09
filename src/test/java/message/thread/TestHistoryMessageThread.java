package message.thread;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import message.message.receive.ReceiveMessageDO;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

public class TestHistoryMessageThread {

    @Test
    public void testRun(){
        String userId = "a";

        ReceiveMessageDO receiveMessageDO = new ReceiveMessageDO();

        receiveMessageDO.setMessage("a");

        receiveMessageDO.setFromUser("a");

        receiveMessageDO.setReceiveTime(new Date());

        receiveMessageDO.setReceiveUser("a");

        receiveMessageDO.setFromNickName("a");


        HistoryMessageThread historyMessageThread = new HistoryMessageThread(userId, receiveMessageDO);

        historyMessageThread.run();

    }

    @Test
    public void testRead() throws FileNotFoundException {
        File dir  = new File("history/message");
        File file = new File(dir,"a.json");
        JSONReader reader = new JSONReader(new FileReader(file));
        reader.startArray();
        while (reader.hasNext()) {
            ReceiveMessageDO vo = reader.readObject(ReceiveMessageDO.class);
            System.out.println(vo);
        }
        reader.endArray();
        reader.close();
    }


}
