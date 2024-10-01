package com.example.databaseService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "questExchange";

    // Имена очередей
    public static final String QUEUE_DAILY_QUESTS = "questRequestQueue";
    public static final String QUEUE_QUEST_BY_ID = "questByIdQueue";
    public static final String QUEUE_RANDOM_QUEST = "questRandomQueue";

    // Ключи маршрутизации
    public static final String ROUTING_KEY_DAILY_QUESTS = "dailyQuests";
    public static final String ROUTING_KEY_QUEST_BY_ID = "questById";
    public static final String ROUTING_KEY_RANDOM_QUEST = "randomQuest";

    // Определение очередей
    @Bean
    public Queue dailyQuestsQueue() {
        return new Queue(QUEUE_DAILY_QUESTS);
    }

    @Bean
    public Queue questByIdQueue() {
        return new Queue(QUEUE_QUEST_BY_ID);
    }

    @Bean
    public Queue randomQuestQueue() {
        return new Queue(QUEUE_RANDOM_QUEST);
    }

    // Обменник
    @Bean
    public DirectExchange questExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Привязки с использованием ключей маршрутизации
    @Bean
    public Binding bindingDailyQuests() {
        return BindingBuilder.bind(dailyQuestsQueue()).to(questExchange()).with(ROUTING_KEY_DAILY_QUESTS);
    }

    @Bean
    public Binding bindingQuestById() {
        return BindingBuilder.bind(questByIdQueue()).to(questExchange()).with(ROUTING_KEY_QUEST_BY_ID);
    }

    @Bean
    public Binding bindingRandomQuest() {
        return BindingBuilder.bind(randomQuestQueue()).to(questExchange()).with(ROUTING_KEY_RANDOM_QUEST);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
