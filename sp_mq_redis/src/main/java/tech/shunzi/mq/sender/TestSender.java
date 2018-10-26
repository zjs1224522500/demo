package tech.shunzi.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.shunzi.constants.IMessageQueueConstants;
import tech.shunzi.mq.dto.TestMQMsg;

import java.util.UUID;

@Component
public class TestSender {

    @Autowired
    private RabbitTemplate template;

    public void sendMsg() {
        TestMQMsg msg = new TestMQMsg();
        msg.setGuid(UUID.randomUUID().toString());
        msg.setName("name");
        template.convertAndSend(IMessageQueueConstants.DIRECT_EXCHANGE, IMessageQueueConstants.TEST_ROUTING_KEY, msg);
    }
}
