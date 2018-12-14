package message.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisClient {

    @Autowired
    private RedisClient redisClient;


    @Test
    public void testZAdd() {
        String key = "INDEX";
        String value = "123";
        Double a = 2.0;
        redisClient.zAdd(key, value, a);
    }


}
