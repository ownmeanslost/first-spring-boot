package com.lsf.firstspringboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: RedisTempUtilTest
 * @Author: shaofan.li
 * @Description:
 * @Date: 2020/3/10 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTempUtilTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // KEYS数组对应 List<K> keys  （按照顺序获取）
// ARGV数组对应 Object... args （按照顺序获取）
//返回按照json格式返回
    /**
     * 单值扣减额度
     */
    public static String SINGLE_REDUCE_QUOTA =
          "\n" +
                  "local count = redis.call('incr',KEYS[1])\n" +
                  "if count == 1 then\n" +
                  "  redis.call('expire',KEYS[1],ARGV[1])\n" +
                  "end\n" +
                  " \n" +
                  "if count > tonumber(ARGV[2]) then\n" +
                  "  return 0\n" +
                  "end\n" +
                  " \n" +
                  "return 1\n";

    @Test
    public void test1(){
      Integer integer =  stringRedisTemplate.opsForValue().append("k1", "v1");
      System.out.print(integer);
    }

    @Test
    public void test2(){
        int socket =  Integer.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("socket")));
        if(socket > 0){
            int realStock = socket - 1;
            stringRedisTemplate.opsForValue().set("socket", String.valueOf(realStock));
            System.out.println("扣减成功，剩余库存："+ realStock + "");
        }else {
            System.out.print("扣减失败剩余库存不足！");
        }
    }

    @Test
    public void test3(){

    }
}
