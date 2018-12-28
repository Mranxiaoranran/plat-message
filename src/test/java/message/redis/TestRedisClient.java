package message.redis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisClient {

    @Autowired
    private RedisClient redisClient;


    @Test
    public void testZAdd() {
        String key = "1";
        String value = "123456";
        Double a = 2.0;
        redisClient.zAdd(key, value, a);
    }
    @Test
    public void testZRange(){
        String key = "1";
        Set<Object> objects = redisClient.zRange("1");
        System.out.println(objects.size());
    }

    @Test
    public void testZRem(){
        String key = "1";
        redisClient.zRem(key);
    }


}
