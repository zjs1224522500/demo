package tech.shunzi.tech.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tech.shunzi.constants.IMessageQueueConstants;

@Configuration
public class RabbitMQConfigContainerFactory {

    @Bean(IMessageQueueConstants.BEAN_NAME_GENERAL_CONTAINER_FACTORY)
    @Primary
    public SimpleRabbitListenerContainerFactory generalContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler());
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Bean(IMessageQueueConstants.BEAN_NAME_BATCH_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory batchContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setConcurrentConsumers(50);
        factory.setMaxConcurrentConsumers(100);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler());
        return factory;
    }

}
