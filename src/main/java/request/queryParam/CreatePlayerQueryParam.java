package request.queryParam;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class CreatePlayerQueryParam extends QueryParamBase {

    public CreatePlayerQueryParam age(String value) {
        super.add("age", value);
        return this;
    }

    public CreatePlayerQueryParam gender(String value) {
        super.add("gender", value);
        return this;
    }

    public CreatePlayerQueryParam role(String value) {
        super.add("role", value);
        return this;
    }

    public CreatePlayerQueryParam login(String value) {
        super.add("login", value);
        return this;
    }

    public CreatePlayerQueryParam screenName(String value) {
        super.add("screenName", value);
        return this;
    }

    public CreatePlayerQueryParam password(String value) {
        super.add("password", value);
        return this;
    }
}
