package com.msb.mall.ware;

/**
 * @author Jason
 * @date 2022/6/23
 * hello ashen one
 */
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.msb.mall.ware.dao")
@EnableDiscoveryClient
public class MallWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWareApplication.class, args);
    }
}
