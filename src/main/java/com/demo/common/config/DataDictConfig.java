package com.demo.common.config;


import com.demo.common.output.ConsoleReporter;
import com.demo.common.output.ReporterInterface;
import com.demo.common.starage.MemoryStorage;
import com.demo.common.starage.StorageInterface;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableConfigurationProperties(value = DataInfo.class)
public class DataDictConfig {

    @Bean
    @ConditionalOnProperty(prefix = "metrics", name = "reporter", havingValue = "console")
    public ReporterInterface console() {
        return new ConsoleReporter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "metrics", name = "storage", havingValue = "memory")
    public StorageInterface memory() {
        return new MemoryStorage();
    }


    @Bean
    public PushConfiguration startRepeatedReport(DataInfo dataInfo, ReporterInterface metricsReporter, StorageInterface metricsStorage) {
        PushConfiguration instance = PushConfiguration.getInstance(metricsStorage, metricsReporter);
        instance.startRepeatedReport(dataInfo.getPeriodInSeconds(), dataInfo.getDurationInSeconds());
        return instance;
    }
}
