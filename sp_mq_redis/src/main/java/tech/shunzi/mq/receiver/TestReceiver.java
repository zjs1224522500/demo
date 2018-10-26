package tech.shunzi.mq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.shunzi.constants.IMessageQueueConstants;
import tech.shunzi.mq.dto.TestMQMsg;

@Component
public class TestReceiver {

    @RabbitListener(containerFactory = IMessageQueueConstants.BEAN_NAME_BATCH_CONTAINER_FACTORY, queues = IMessageQueueConstants.TEST_QUEUE_NAME)
    public void onMessage(TestMQMsg msg) {

        System.out.println(msg.getGuid());
        System.out.println(msg.getName());
    }
}
