package api.client;

import config.injection.InjectorData;

public abstract class ApiClient {
    protected RestAssuredClient apiClient;

    public ApiClient(String... token) {
        this.apiClient = this.getApiClient(token);
    }

    protected RestAssuredClient getApiClient(String... token) {
        return InjectorData.injector.getInstance(RestAssuredClient.class).setRequestConfiguration(token);
    }
}