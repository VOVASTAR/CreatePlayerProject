package request.helper;

import entity.Gender;
import entity.Role;
import request.queryParam.CreatePlayerQueryParam;
import response.GetCreatePlayerResponse;
import service.PlayerApi;
import utils.AppConstants;

import java.util.Map;
import java.util.UUID;

public class CreatePlayerRequestHelper {

    public GetCreatePlayerResponse getCreatedPlayerAdmin() {
        Map<String, String> queryParam = new CreatePlayerRequestHelper().getValidAllRequiredParams()
                .role(Role.ADMIN.name().toLowerCase()).asMap();
        return new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, queryParam)
                .as(GetCreatePlayerResponse.class);
    }

    public GetCreatePlayerResponse getCreatedPlayerUser() {
        Map<String, String> queryParam = new CreatePlayerRequestHelper().getValidAllRequiredParams()
                .role(Role.USER.name().toLowerCase()).asMap();
        return new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, queryParam)
                .as(GetCreatePlayerResponse.class);
    }

    public CreatePlayerQueryParam getValidAllRequiredParams() {
        String uuid = UUID.randomUUID().toString();

        return new CreatePlayerQueryParam()
                .age("32")
                .gender(Gender.FEMALE.name().toLowerCase())
                .login("login_" + uuid)
                .role(Role.USER.name().toLowerCase())
                .screenName("screenName_" + uuid);
    }
}
