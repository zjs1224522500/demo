package tech.shunzi.mq.demo.publish;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Actively declare a non-auto-delete, non-durable exchange with no extra arguments
        // Declare an FANOUT exchange with name "logs"
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // Create a non-durable, exclusive, auto-delete queue with a generated name
        String queueName = channel.queueDeclare().getQueue();
        // Bind the queues to the exchange, maintain the binding relationship between queues and exchange.
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
