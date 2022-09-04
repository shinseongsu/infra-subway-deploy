package nextstep.subway.utils;

import io.lettuce.core.RedisClient;
import java.util.Iterator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

@Service
@ActiveProfiles("test")
public class RedisCleanup {

    @Autowired
    private RedisTemplate redisTemplate;

    public void execute() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushAll();
            return null;
        });
    }

}
