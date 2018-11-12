package message;

/**
 * 消息业务传输对象
 *
 * @author anran
 */
public class MessageDTO {
    private String userName;

    private String message;

    public MessageDTO() {
    }

    public MessageDTO(String userName, String message) {
        super();
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
