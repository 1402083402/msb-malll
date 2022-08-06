package com.msb.mall.third;

import com.msb.mall.third.utils.SmsComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jason
 * @date 2022/7/30
 * hello ashen one
 */
@SpringBootTest
public class tttttt {
    @Autowired
    private SmsComponent component;
    @Test
    public void testSendSMS2()
    {
        component.sendSmsCode("18523737141","9966");
    }
}
