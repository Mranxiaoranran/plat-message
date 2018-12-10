package message.client;

import java.util.Date;

/**
 * 客户端存储信息
 */
public class ClientDo{

    private String clientId;

    private boolean isOnline;

    private long mostSignificantBits;

    private long leastSignificantBits;

    private Date lastConnectedTime;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getMostSignificantBits() {
        return mostSignificantBits;
    }

    public void setMostSignificantBits(long mostSignificantBits) {
        this.mostSignificantBits = mostSignificantBits;
    }

    public long getLeastSignificantBits() {
        return leastSignificantBits;
    }

    public void setLeastSignificantBits(long leastSignificantBits) {
        this.leastSignificantBits = leastSignificantBits;
    }

    public Date getLastConnectedTime() {
        return lastConnectedTime;
    }

    public void setLastConnectedTime(Date lastConnectedTime) {
        this.lastConnectedTime = lastConnectedTime;
    }
}
