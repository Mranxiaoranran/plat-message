package message.message.send;

import java.util.Date;

/**
 * 发送消息事件
 */
public class SendMessageDO{
    /**
     * 聊天内容
     */
    private String message;

    /**
     *  接收用户
     */
    private String   receiveUser;


    /**
     *  发送用户
     */
    private String  fromUser;


    /**
     *  发送时间
     */
    private Date sendTime;


    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
