package com.example.demo.service.impl;

import com.example.demo.mapper.source1.MessageMapper1;
import com.example.demo.mapper.source2.MessageMapper2;
import com.example.demo.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pc on 2018/12/17 15:37
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper1 messageMapper1;

    @Resource
    private MessageMapper2 messageMapper2;


    @Override
    public String find1() {
        return messageMapper1.find();
    }

    @Override
    public String find2() {
        return messageMapper2.find();
    }
}
