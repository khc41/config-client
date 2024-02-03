package hello.configclient.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "error.codemap")
public class ErrorCodeManager {
    public Map<String, ErrorCode> common = new HashMap<>();
    public Map<String, ErrorCode> shop = new HashMap<>();
}
