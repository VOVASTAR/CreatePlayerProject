package service;

import api.client.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import request.requestBody.DeletePlayerRequest;
import request.requestBody.PatchPlayerRequest;
import request.requestBody.PostPlayerIdRequest;

import java.util.Map;

@Getter
public class PlayerApi extends ApiClient {

    private final String commonPath = "/player";
    private String path;

    public PlayerApi(String... token) {
        super(token);
    }


    @Step("GET /player/create/{editor}")
    public Response getCreatePlayer(String editor, int statusCode, long responseTime, Map<String, String> params) {
        this.path = String.format(commonPath + "/create/%s", editor);
        return apiClient
                .addParams(params)
                .get(path, statusCode, responseTime);
    }

    @Step("GET /player/get/all")
    public Response getAllPlayers(int statusCode, long responseTime) {
        this.path = commonPath + "/get/all";
        return apiClient.get(path, statusCode, responseTime);
    }

    @Step("POST /player/get")
    public Response postPlayerById(PostPlayerIdRequest request, int statusCode, long responseTime) {
        this.path = commonPath + "/get";
        return apiClient.post(path, request, statusCode, responseTime);
    }

    @Step("DELETE /player/delete/{editor}")
    public Response deletePlayer(String editor, DeletePlayerRequest request, int statusCode, long responseTime) {
        this.path = String.format(commonPath + "/delete/%s", editor);
        return apiClient
                .delete(path, request, statusCode, responseTime);
    }

    @Step("PATCH /player/update/{editor}/{id}")
    public Response patchPlayer(String editor, long id, PatchPlayerRequest request, int statusCode, long responseTime) {
        this.path = String.format(commonPath + "/update/%s/%d", editor, id);
        return apiClient
                .patch(path, request, statusCode, responseTime);
    }
}
