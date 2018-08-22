import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;


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

    public static String getString(String key){
        String value;
        try(Jedis jedis = jedisPool.getResource()){
            value = jedis.get(key);
        }
        return value;
    }


    public static void setString(String key, String value){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.set(key, value);
        }
    }

    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList();
        for(int i=0;i<10;i++){
            list.add("id"+i);
        }
        String key = "flow-1::action-1";
        int index = 0;
        String val = RedisService.getString(key);
        if(val != null){
            index = Integer.parseInt(val);
        }
        RedisService.setString(key, String.valueOf((index + 1)));

        System.out.println("index=" + index);
    }
}
