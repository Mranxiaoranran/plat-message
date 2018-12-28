package message.client;

import java.util.Date;
import java.util.UUID;

/**
 * 客户端存储信息
 */
public class ClientDO {
    /**
     * 业务系统用户标识
     */
    private String userId;
    /**
     * 是否在线
     */
    private boolean isOnline;

    /**
     * 最后登录日期
     */
    private Date lastConnectedTime;

    /**
     * 长连接唯一标识
     */
    private UUID session;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Date getLastConnectedTime() {
        return lastConnectedTime;
    }

    public void setLastConnectedTime(Date lastConnectedTime) {
        this.lastConnectedTime = lastConnectedTime;
    }

    public UUID getSession() {
        return session;
    }

    public void setSession(UUID session) {
        this.session = session;
    }
}
