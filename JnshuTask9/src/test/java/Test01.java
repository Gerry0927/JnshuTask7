import com.gerry.jnshu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.transform.Templates;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test01 {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private UserCacheDao userCacheDao;

    @Test
    public void testStringSetKey(){
        System.out.println(redisTemplate.opsForValue().get("k1"));
    }

    @Test
    public void testStringSetKeyUserCache(){
//        UserCacheObject object = new UserCacheObject()
//                .setId(1).setName("test").setGender(1);
//        String key = String.format("user:%d",object.getId());
//        redisTemplate.opsForValue().set(key,object);
    }

    @Test
    public void testStringGetKeyUserCache(){
//        UserCacheObject object = new UserCacheObject()
//                .setId(1).setName("test").setGender(1);
        String key = String.format("user:%d",1);
        System.out.println(redisTemplate.opsForValue().get(key));
    }


    @Test
    public void testCacheDao(){
//        UserCacheObject object = new UserCacheObject()
//                .setId(1).setName("test").setGender(1);
//        userCacheDao.set(object.getId(),object);
        //System.out.println(userCacheDao.get(object.getId()));
    }

    /**
     * set(key,value)
     * get(key)
     */

    @Test
    public void testMultiSetAndGet(){
       Map valueMap = new HashMap<>();
       valueMap.put("key1","value1");
       valueMap.put("key2","value2");
       valueMap.put("key3","value3");
       valueMap.put("key4","value4");
       redisTemplate.opsForValue().multiSet(valueMap);

       redisTemplate.opsForValue().multiGet(Arrays.asList("ke1","key2","key3"));
    }

    @Test
    public void testList(){
//        redisTemplate.opsForList().leftPush("list","a");
//        redisTemplate.opsForList().leftPush("list","b");
//        redisTemplate.opsForList().leftPush("list","c");

//        redisTemplate.opsForList().leftPush("list","a","n");

//        redisTemplate.opsForList().leftPushIfPresent("list","o");

//        redisTemplate.opsForList().rightPush("list","b","r");

//        String popValue = (String) redisTemplate.opsForList()
//                .rightPopAndLeftPush("list","12");
//        System.out.println(popValue);

//        redisTemplate.opsForHash().put("hashValue","1","map1-1-1");
//        redisTemplate.opsForHash().put("hashValue","2","map2-2");
//        redisTemplate.opsForHash().increment("hashValue","map11",2);
//        redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
        //Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.NONE);
        Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.scanOptions().match("C").build());
        while (cursor.hasNext()){
            Object object = cursor.next();
            System.out.println("通过scan(K key, ScanOptions options)方法获取匹配的值:" + object);
        }

    }



}
