package com.example.webzine.gw.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties("spring.kafka")
@Getter
@Setter
public class KafkaConfig {
    private String bootstrapServers;
    private Producer producer;
    private Consumer consumer;
    private Template template;

    @Getter
    @Setter
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private String batchSize;
        private String partitionerClass;
        private String lingerMs;
    }

    @Getter
    @Setter
    public static class Template {
        private List<String> topic;
    }

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
        private String enableAutoCommit;
        private String autoOffsetReset;
        private String keyDeserializer;
        private String valueDeserializer;
        private String maxPollRecords;
    }
}
