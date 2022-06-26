package com.msb.mall.coupon;

/**
 * @author Jason
 * @date 2022/6/23
 * hello ashen one
 */
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.msb.mall.coupon.dao")
@EnableDiscoveryClient
public class MallCouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }
}
