package com.msb.mall.product;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jason
 * @date 2022/7/26
 * hello ashen one
 */
@SpringBootTest
public class mallTest {
    @Autowired
    RedissonClient redissonClient;
    @Test
    public void testRedissonClient(){
        System.out.println("redisClient"+redissonClient);
    }
}
