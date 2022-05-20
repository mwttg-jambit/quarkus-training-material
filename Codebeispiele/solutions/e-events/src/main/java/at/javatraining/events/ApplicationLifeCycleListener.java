package at.javatraining.events;

import io.quarkus.runtime.ShutdownContext;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

@ApplicationScoped
@Slf4j
public class ApplicationLifeCycleListener {
    public void onStartup(@Observes StartupEvent startupEvent){
        log.warn("Servcice started");
    }

    public void onShutdown(@Observes ShutdownEvent shutdownEvent){
        log.warn("Service shutting down");
    }


}
