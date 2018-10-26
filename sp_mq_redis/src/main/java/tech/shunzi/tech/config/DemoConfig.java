package tech.shunzi.tech.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tech.shunzi.constants.IMessageQueueConstants;

@SpringBootConfiguration
public class DemoConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(IMessageQueueConstants.DIRECT_EXCHANGE);
    }

    @Bean
    public Queue testQueue() {
        return new Queue(IMessageQueueConstants.TEST_QUEUE_NAME, false);
    }

    @Bean
    public Binding testBinding(@Qualifier("testQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(IMessageQueueConstants.TEST_ROUTING_KEY);
    }


}
