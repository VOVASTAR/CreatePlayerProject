package response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends BaseResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}