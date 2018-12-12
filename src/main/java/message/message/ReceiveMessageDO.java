package message.message;

import java.io.Serializable;

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

    public ReceiveMessageDO() {

    }

    public ReceiveMessageDO(SendMessageDO sendMessageDO) {
        this.message = sendMessageDO.getMessage();
        this.fromUser = sendMessageDO.getSendUser();
        this.receiveUser = sendMessageDO.getToUser();
    }


}
