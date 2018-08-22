import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.function.Consumer;

public class RedisService {
    private static String host = "127.0.0.1";
    private static int port = 6379;
    private static int maxIdle = 10;
    private static int maxTotal = 25;
    private static JedisPool jedisPool;

    static{
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        jedisPool = new JedisPool(poolConfig, host, port);
    }

    public static void withRedis(Consumer<Jedis> consumer){
        try(Jedis jedis = jedisPool.getResource()){
            consumer.accept(jedis);
        }
    }
}
