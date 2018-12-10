package message.namespace;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.UUID;

/**
 * namespace
 */
public class NamespaceHandler {
    /**
     * 新增namespace
     *
     * @return
     */
    public void addNamespace(final SocketIOServer server, String nameSpace){
        final SocketIONamespace chat1namespace = server.addNamespace("/" + nameSpace);
        chat1namespace.addEventListener("message", NamespaceDTO.class, new DataListener<NamespaceDTO>() {
            public void onData(SocketIOClient client, NamespaceDTO data, AckRequest ackRequest) {
                System.out.println("用户" + data.getSendUser() + "开始与用户" + data.getToUser() + "进行聊天了" + "   聊天内容为 " + data.getMessage());
                sendMessage(server, data);
            }
        });
    }
    /**
     * 发送消息给指定用户，假如该用户不在线，则无法发送消息
     *
     * @param data
     */
    public void sendMessage(SocketIOServer server, NamespaceDTO data) {
         SocketIONamespace chat1namespace = server.getNamespace("/" + data.getToUser());
        chat1namespace.getBroadcastOperations().sendEvent("message", data);
    }
}
