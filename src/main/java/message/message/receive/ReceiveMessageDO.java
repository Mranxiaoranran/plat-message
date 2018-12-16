package message.message.receive;

import message.message.SendMessageDO;

import java.io.Serializable;
import java.util.Date;

/**
 * 接收消息
 */
public class ReceiveMessageDO implements Serializable {

    /**
     * 聊天内容
     */
    private String message;

    /**
     * 消息发送用户信息
     */
    private String fromUser;

    /**
     * 接收用户信息
     */
    private String receiveUser;

    /**
     * 接收时间
     */
    private Date receiveTime;


    public ReceiveMessageDO() {

    }

    public ReceiveMessageDO(SendMessageDO sendMessageDO) {
        this.message = sendMessageDO.getMessage();
        this.fromUser = sendMessageDO.getSendUser();
        this.receiveUser = sendMessageDO.getToUser();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
