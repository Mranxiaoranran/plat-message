package message.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Configuration
public class RedisListener {
    @Autowired
    private RedisEventHandler redisEventHandler ;


    /**
     * Redis消息监听器容器
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter(redisEventHandler), new PatternTopic("event"));
        return container;
    }


    /**
     * 配置消息接收处理类
     *
     * @param
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisEventHandler eventHandler) {
        return new MessageListenerAdapter(eventHandler, "receiveMessage");
    }


}
