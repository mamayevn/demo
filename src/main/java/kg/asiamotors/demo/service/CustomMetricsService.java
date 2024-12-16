package kg.asiamotors.demo.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMetricsService {

    private final MeterRegistry meterRegistry;

    public CustomMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void recordRequest(String requestType) {
        meterRegistry.counter("custom.requests", "type", requestType).increment();
    }

    public void recordError(String requestType) {
        meterRegistry.counter("custom.errors", "type", requestType).increment();
    }
}
