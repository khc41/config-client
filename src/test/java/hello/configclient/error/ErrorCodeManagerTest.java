package hello.configclient.error;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest
public class ErrorCodeManagerTest {
    @Autowired
    ErrorCodeManager errorCodeManager;

    @Test
    void test_errorCode() {
        Map<String, ErrorCode> common = errorCodeManager.common;
        for (String key : common.keySet()) {
            log.info("key = {}, value = {}", key, common.get(key));
        }
        Map<String, ErrorCode> shop = errorCodeManager.shop;
        for (String key : shop.keySet()) {
            log.info("key = {}, value = {}", key, shop.get(key));
        }
    }
}
