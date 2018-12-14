package message.event;

import java.io.Serializable;

public class EventDTO implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    public EventDTO(String userId) {

        this.userId = userId;
    }

    public EventDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
