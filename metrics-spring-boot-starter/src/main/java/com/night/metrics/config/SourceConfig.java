package com.night.metrics.config;


import com.night.metrics.aspect.ApiMonitorAspect;
import com.night.metrics.output.ConsoleReporter;
import com.night.metrics.output.ReporterInterface;
import com.night.metrics.starage.MemoryStorage;
import com.night.metrics.starage.StorageInterface;
import com.night.metrics.util.SpringContextUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableConfigurationProperties(value = DateInfo.class)
public class SourceConfig {

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
    public PushConfiguration startRepeatedReport(DateInfo dataInfo, ReporterInterface metricsReporter, StorageInterface metricsStorage) {
        PushConfiguration instance = PushConfiguration.getInstance(metricsStorage, metricsReporter);
        instance.startRepeatedReport(dataInfo.getPeriodInSeconds(), dataInfo.getDurationInSeconds());
        return instance;
    }

}
