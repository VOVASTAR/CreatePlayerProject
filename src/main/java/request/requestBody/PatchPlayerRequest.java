package request.requestBody;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PatchPlayerRequest extends BaseRequest {
    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    private PatchPlayerRequest(Builder builder) {
        this.age = builder.age;
        this.gender = builder.gender;
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
        this.screenName = builder.screenName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer age;
        private String gender;
        private String login;
        private String password;
        private String role;
        private String screenName;

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder screenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        public PatchPlayerRequest build() {
            return new PatchPlayerRequest(this);
        }
    }
}
