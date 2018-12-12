package message.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户事件传输参数
 */
public class UserDO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        UserDO compareUserDo = (UserDO) obj;
        return this.userId.equals(compareUserDo.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }


}
