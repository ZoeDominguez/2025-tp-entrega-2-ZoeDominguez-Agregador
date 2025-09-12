package ar.edu.utn.dds.k3003.metrics;

import io.micrometer.core.instrument.step.StepMeterRegistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public StepMeterRegistry datadogRegistry() {
        DDMetricsUtils metricsUtils = new DDMetricsUtils("agregador");
        return metricsUtils.getRegistry();
    }
}