package com.example.demo;

import com.example.demo.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatServiceApplicationTests {
    @Resource
    private MessageService messageService;

    @Test
    public void contextLoads() {
        String a=messageService.find1();
        String b=messageService.find2();

        Assert.assertNotEquals(b,a);
    }

}

