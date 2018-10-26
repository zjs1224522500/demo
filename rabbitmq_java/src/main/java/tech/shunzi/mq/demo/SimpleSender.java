package tech.shunzi.mq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static tech.shunzi.mq.demo.MQConstants.QUEUE_NAME;

public class SimpleSender {

    public static void main(String[] argv) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();

        // connect to a broker with its name or ip address
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // create a channel, which is where most of the API for getting things done resides.
        Channel channel = connection.createChannel();

        // Declaring a queue is idempotent - it will only be created if it doesn't exist already.
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello World!";
        // The message content is a byte array, so you can encode whatever you like there.
        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
