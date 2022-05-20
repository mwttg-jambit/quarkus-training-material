package at.javatraining.events;

import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@Startup
// by default in Quarkus all beans are lazily initalized.
// use @Startup to enforce creation at startup
@Slf4j
@ApplicationScoped
public class StartupClass {
    public StartupClass() {
        log.info("Application is starting");
    }

    @PostConstruct
    void postConstruct(){
        log.info("Startup bean initialized");
    }
}
