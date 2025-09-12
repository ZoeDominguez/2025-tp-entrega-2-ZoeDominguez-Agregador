package ar.edu.utn.dds.k3003.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    public MetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementCounter(String metricName, String... tags) {
        Counter counter = Counter.builder(metricName)
                .tags(tags)
                .register(meterRegistry);
        counter.increment();
    }

    public Timer.Sample startTimer() {
        return Timer.start(meterRegistry);
    }

    public void stopTimer(Timer.Sample sample, String metricName, String... tags) {
        sample.stop(Timer.builder(metricName)
                .tags(tags)
                .register(meterRegistry));
    }

}