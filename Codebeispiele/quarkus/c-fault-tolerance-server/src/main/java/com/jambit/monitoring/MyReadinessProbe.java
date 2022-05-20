package com.jambit.monitoring;

import lombok.SneakyThrows;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
@Readiness
public class MyReadinessProbe implements HealthCheck {
    @SneakyThrows
    @Override
    public HealthCheckResponse call() {
        FileStore fileStore = Files.getFileStore(Paths.get("C:/"));
        long free = fileStore.getUsableSpace() / 1_000_000_000L;
        long total = fileStore.getTotalSpace() / 1_000_000_000;
        long memory = Runtime.getRuntime().freeMemory() / 1_000_000;

        HealthCheckResponseBuilder readiness = HealthCheckResponse.builder().name("available diskspace");
        return readiness.up().withData("total disk space (GB)", total)
                .withData("free disk space (GB)", free)
                .withData("free ram (MB)", memory)
                .up().build();
    }
}
