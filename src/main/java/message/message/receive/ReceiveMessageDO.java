package message.message.receive;


import message.message.send.SendMessageDO;

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

    /**
     * 发送消息用户的昵称
     */
    private String fromNickName;


    public ReceiveMessageDO() {
    }

    public ReceiveMessageDO(SendMessageDO sendMessageDO) {
        this.message = sendMessageDO.getMessage();
        this.fromUser = sendMessageDO.getFromUser();
        this.receiveUser = sendMessageDO.getReceiveUser();
        this.fromNickName = sendMessageDO.getFromNickName();
        this.receiveTime = new Date();
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


    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }
}
