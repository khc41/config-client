package hello.configclient.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class ErrorCodeManagerTest {
    @Autowired
    ErrorCodeManager errorCodeManager;

    @Test
    void test_errorCode() {
        Map<String, ErrorCode> common = errorCodeManager.common;
        for (String key : common.keySet()) {
            System.out.println("key = " + key + ", value = " + common.get(key));
        }
        Map<String, ErrorCode> shop = errorCodeManager.shop;
        for (String key : shop.keySet()) {
            System.out.println("key = " + key + ", value = " + shop.get(key));
        }
    }
}
