package message.context.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettySocketIoConfig {

    @Value("${message.host}")
    private String hostName;

    @Value("${message.port}")
    private Integer port;


    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostName);
        config.setPort(port);
        return new SocketIOServer(config);
    }
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }


}
