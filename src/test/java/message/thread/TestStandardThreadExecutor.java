package message.thread;

import message.message.receive.ReceiveMessageDO;
import org.junit.Test;

import java.util.Date;

public class TestStandardThreadExecutor {
    @Test
    public void testExecute(){
        StandardThreadExecutor standardThreadExecutor = new StandardThreadExecutor();
        String userId = "a";
        ReceiveMessageDO receiveMessageDO = new ReceiveMessageDO();
        receiveMessageDO.setMessage("a");
        receiveMessageDO.setFromUser("a");
        receiveMessageDO.setReceiveTime(new Date());
        receiveMessageDO.setReceiveUser("a");
        receiveMessageDO.setFromNickName("a");
        HistoryMessageThread historyMessageThread = new HistoryMessageThread(userId, receiveMessageDO);
        standardThreadExecutor.execute(historyMessageThread);
    }



}
