package com.example.webzine.gw.service;

import com.example.webzine.gw.common.KafkaConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomProducer {

    private final KafkaConfig config;
    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void build() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getProducer().getKeySerializer());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getProducer().getValueSerializer());
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, config.getProducer().getPartitionerClass());
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, config.getProducer().getBatchSize());
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, config.getProducer().getLingerMs());
        producer = new KafkaProducer<>(properties);
    }

    public void send1(String message) {
        //ProducerRecord<String, String> record = new ProducerRecord<>(config.getTemplate().getDefaultTopic(), message);
        ProducerRecord<String, String> record = new ProducerRecord<>("dev-topic-test1", message);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                log.debug("pub timestamp : {}", LocalDateTime.ofInstant(Instant.ofEpochMilli(metadata.timestamp()), TimeZone.getDefault().toZoneId()));
                log.debug("pub partition : {}", metadata.partition());
                log.debug("pub message : {}", message);
                if(exception != null) {
                    log.debug(exception.getMessage());
                }
            }
        });
    }

    public void send2(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>("dev-topic-test2", message);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                log.debug("pub timestamp : {}", LocalDateTime.ofInstant(Instant.ofEpochMilli(metadata.timestamp()), TimeZone.getDefault().toZoneId()));
                log.debug("pub partition : {}", metadata.partition());
                log.debug("pub message : {}", message);
                if(exception != null) {
                    log.debug(exception.getMessage());
                }
            }
        });
    }
}
