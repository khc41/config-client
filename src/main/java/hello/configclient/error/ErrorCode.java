package hello.configclient.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorCode {
    private String code;
    private String value;
}
