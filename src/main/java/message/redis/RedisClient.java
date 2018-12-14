package message.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisClient {

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加ZSET缓存
     */
    public void zAdd(String key, Object obj, double score) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, obj, score);
    }

}
