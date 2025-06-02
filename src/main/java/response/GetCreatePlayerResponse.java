package response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class GetCreatePlayerResponse extends BaseResponse {
    private Long id;
    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
