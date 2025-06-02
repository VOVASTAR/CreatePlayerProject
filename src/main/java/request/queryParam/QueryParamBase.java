package request.queryParam;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class QueryParamBase {
    protected final Map<String, String> params = new ConcurrentHashMap<>();

    public QueryParamBase add(String key, Object value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public Map<String, String> asMap() {
        return params;
    }
}
