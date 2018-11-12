package message;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * 基于 NamespaceChatLauncher 应用程序启动
 *
 * @author anran
 */
public class StartApplication {

    public static void main(String[] args) throws InterruptedException {
        //配置服务器的IP地址和端口号
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        //开启SocketIOServer服务器
        final SocketIOServer server = new SocketIOServer(config);
        //绑定namespace
        final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
        //配置namespace监听
        chat1namespace.addEventListener("message", MessageDTO.class, new DataListener<MessageDTO>() {
            public void onData(SocketIOClient client, MessageDTO data, AckRequest ackRequest) {
                System.out.print(data.getMessage());
                chat1namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }


}
