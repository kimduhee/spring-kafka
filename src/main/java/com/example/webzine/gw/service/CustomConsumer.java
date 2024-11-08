package com.example.webzine.gw.service;

import com.example.webzine.gw.common.KafkaConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomConsumer {

    private final KafkaConfig config;
    private KafkaConsumer<String, String> consumer = null;

    @PostConstruct
    public void build() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, config.getConsumer().getGroupId());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getConsumer().getKeyDeserializer());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getConsumer().getValueDeserializer());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getConsumer().getAutoOffsetReset());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, config.getConsumer().getMaxPollRecords());
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.getConsumer().getEnableAutoCommit());
        consumer = new KafkaConsumer<>(properties);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "#{'${spring.kafka.template.topic}'.split(', ')}")
    public void consumer1(ConsumerRecord<String, String> data){
        log.debug("[consumer1] => topic: {}, partition: {}, offset: {}, value: {} ", data.topic(), data.partition(), data.offset(), data.value());
        //acknowledgment.acknowledge();
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "#{'${spring.kafka.template.topic}'.split(', ')}")
    public void consumer2(ConsumerRecord<String, String> data){
        log.debug("[consumer2] => topic: {}, partition: {}, offset: {}, value: {} ", data.topic(), data.partition(), data.offset(), data.value());
        //acknowledgment.acknowledge();
    }

//    @KafkaListener(topics = "#{'${spring.kafka.template.topic}'.split(', ')}")
//    public void consume2(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String payload) {
//        log.debug("consumer topic name : {}", topic);
//        log.debug("consumer payload : {}", payload);
//    }
}
