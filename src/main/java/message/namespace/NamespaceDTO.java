package message.namespace;

/**
 * 房间传输对象
 *
 * @author anran
 */
public class NamespaceDTO {
    /**
     * 聊天内容
     */
    private String message;

    /**
     * 发送用户
     */
    private String sendUser;

    /**
     * 接受用户
     */
    private String toUser;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
