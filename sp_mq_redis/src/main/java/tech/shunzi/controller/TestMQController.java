package tech.shunzi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shunzi.mq.sender.TestSender;

@RestController
@RequestMapping("/test")
public class TestMQController {

    @Autowired
    private TestSender sender;

    @GetMapping("/msg")
    public void send()
    {
        sender.sendMsg();
    }
}
