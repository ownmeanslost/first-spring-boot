package com.lsf.firstspringboot.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisController
 * @Author: shaofan.li
 * @Description:
 * @Date: 2020/3/10 17:05
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/test2")
    public String test2(){
        String uuid = UUID.randomUUID().toString();
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent("test2", uuid, 2, TimeUnit.SECONDS);
        if(!result){
            return "error";
        }
        try {
            int socket = 0;
            socket = Integer.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("socket")));
            if(socket > 0){
                int realStock = socket - 1;
                stringRedisTemplate.opsForValue().set("socket", String.valueOf(realStock));
                System.out.println("扣减成功，剩余库存："+ realStock + "");
            }else {
                System.out.println("扣减失败剩余库存不足！");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if(uuid.equals(stringRedisTemplate.opsForValue().get("test2"))){
                //释放锁
                stringRedisTemplate.delete("test2");
            }
        }

        return "end";
    }



}
