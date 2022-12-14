package com.example.orchestration.config;

import com.example.common.OrderRequest;
import com.example.common.OrderResponse;
import com.example.orchestration.util.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, OrderRequest> consumerFactory() {
        Map<String, Object> props = new HashMap();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.HOST);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(OrderRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderRequest> factory = new ConcurrentKafkaListenerContainerFactory<String, OrderRequest>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    @Bean
    public ProducerFactory<String, OrderResponse> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.HOST);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(configProps);
    }
    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, OrderResponse> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
