package hello.configclient.config;

import hello.configclient.util.CustomConfigClientWatch;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ConfigDataContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class CustomClientWatchConfig {
    @Bean
    @ConditionalOnMissingBean(ConfigServicePropertySourceLocator.class)
    @ConditionalOnProperty(name = ConfigClientProperties.PREFIX + ".enabled", matchIfMissing = true)
    public ConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties properties) {
        return new ConfigServicePropertySourceLocator(properties);
    }

    @Bean
    @ConditionalOnProperty(name = ConfigClientProperties.PREFIX+ ".enabled", matchIfMissing = true)
    public CustomConfigClientWatch configClientWatch(ConfigDataContextRefresher refresher, ConfigServicePropertySourceLocator locator){
        return new CustomConfigClientWatch(refresher, locator);
    }
}
