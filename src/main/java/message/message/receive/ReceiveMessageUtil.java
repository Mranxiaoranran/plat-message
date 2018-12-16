package message.message.receive;

public class ReceiveMessageUtil {
    /**
     * 收件箱
     */
    private final String INDEX_BOX = "INDEX_BOX";

    /**
     * 获取KEY值
     *
     * @param receiveUser
     * @return
     */
    public String getKey(String receiveUser) {
        return new StringBuffer().append(INDEX_BOX).append(":").append(receiveUser).toString();
    }


}
