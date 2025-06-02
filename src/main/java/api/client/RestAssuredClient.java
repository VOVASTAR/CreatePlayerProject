package api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import request.requestBody.BaseRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static org.hamcrest.Matchers.lessThan;

@Getter
@Setter
public class RestAssuredClient {
    private String baseUrl;
    private String authorization;
    private RequestSpecification requestSpecification;

    protected static final RestAssuredConfig config;

    static {
        config = RestAssuredConfig.config()
                .objectMapperConfig(objectMapperConfig().defaultObjectMapperType(ObjectMapperType.JACKSON_2));
    }

    public RestAssuredClient setRequestConfiguration(String... token) {
        var specBuilder = setBuilder("application/json;charset=utf-8", token);
        this.requestSpecification = specBuilder
                .setBaseUri(baseUrl)
                .build();

        return this;
    }

    private RequestSpecBuilder setBuilder(@NonNull String contentType, String... token) {
        this.baseUrl = System.getProperty("HTTP.URL.BASE");

        return new RequestSpecBuilder()
                .setConfig(config)
                .setContentType(contentType)
                .setUrlEncodingEnabled(false)
                .addFilter(new AllureRestAssured());
    }

    public RestAssuredClient addParams(@NonNull Map<String, String> params) {
        var requestSpec = (RequestSpecificationImpl) this.requestSpecification;
        for (Map.Entry<String, String> param : params.entrySet()) {
            requestSpec.queryParam(param.getKey(), param.getValue());
        }
        return this;
    }

    public Response get(@NonNull String url, int statusCode, long responseTime) {
        ExtractableResponse<Response> extract = given()
                .log().all()
                .spec(requestSpecification)
                .when().get(url)
                .then()
                .log().all()
                .time(lessThan(responseTime))
                .statusCode(statusCode)
                .extract();
        return extract.response();
    }

    public <T extends BaseRequest> Response post(@NonNull String url, T body, int statusCode, long responseTime) {
        return given()
                .log().all()
                .spec(requestSpecification)
                .body(body == null ? "{}" : body)
                .when().post(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .time(lessThan(responseTime))
                .extract()
                .response();
    }

    public <T extends BaseRequest> Response patch(@NonNull String url, T body, int statusCode, long responseTime) {
        return given()
                .log().all()
                .spec(requestSpecification)
                .body(body == null ? "{}" : body)
                .when().patch(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .time(lessThan(responseTime))
                .extract()
                .response();
    }

    public <T extends BaseRequest> Response delete(@NonNull String url, T body, int statusCode, long responseTime) {
        return given()
                .log().all()
                .spec(requestSpecification)
                .body(body == null ? "{}" : body)
                .when().delete(url)
                .then()
                .log().all()
                .statusCode(statusCode)
                .time(lessThan(responseTime))
                .extract()
                .response();
    }
}
