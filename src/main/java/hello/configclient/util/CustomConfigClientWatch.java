package hello.configclient.util;

import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CustomConfigClientWatch implements Closeable, EnvironmentAware {
    private static Log log = LogFactory.getLog(ConfigServicePropertySourceLocator.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ContextRefresher refresher;
    private Environment environment;
    private final ConfigServicePropertySourceLocator locator;
    private final AtomicReference<String> version = new AtomicReference<>();

    public CustomConfigClientWatch(ContextRefresher refresher, ConfigServicePropertySourceLocator locator) {
        this.refresher = refresher;
        this.locator = locator;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void start() {
        this.running.compareAndSet(false, true);
    }

    @Scheduled(
            initialDelayString = "${spring.cloud.config.watch.initialDelay:180000}",
            fixedDelayString = "${spring.cloud.config.watch.delay:500}"
    )
    public void watchConfigServer() {
        if (this.running.get()) {
            String newVersion = fetchNewVersion();
            if (newVersion == null) {
                return;
            }
            String oldVersion  = version.get();
            if (this.versionChanged(oldVersion, newVersion)) {
                version.set(newVersion);
                this.refresher.refresh();
            }
        }

    }

    private String fetchNewVersion() {
        try {
            CompositePropertySource propertySource = (CompositePropertySource) locator.locate(environment);
            return (String) propertySource.getProperty("config.client.version");
        } catch (NullPointerException e) {
            log.error("Cannot fetch from Cloud Config Server: ");
        }
        return null;
    }

    boolean versionChanged(String oldState, String newState) {
        return !StringUtils.hasText(oldState) && StringUtils.hasText(newState) || StringUtils.hasText(oldState) && !oldState.equals(newState);
    }

    public void close() {
        this.running.compareAndSet(true, false);
    }
}
