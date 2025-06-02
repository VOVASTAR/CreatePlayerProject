package api.player;

import entity.Role;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import request.helper.CreatePlayerRequestHelper;
import request.queryParam.CreatePlayerQueryParam;
import request.requestBody.DeletePlayerRequest;
import response.GetCreatePlayerResponse;
import runner.BaseTest;
import runner.TestGroupsConstants;
import service.PlayerApi;
import utils.AppConstants;

import java.util.Map;

@Feature("Route: DELETE /player/delete/{editor}")
public class DeletePlayerByIdTest extends BaseTest {

    @Test(
            description = "Test delete player by supervisor",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "createUserForSupervisor"
    )
    public void testDeletePlayerBySupervisor(String description,
                                             CreatePlayerQueryParam validParams) {
        Map<String, String> queryParam = validParams.asMap();

        GetCreatePlayerResponse createdPlayer = new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, queryParam)
                .as(GetCreatePlayerResponse.class);

        new PlayerApi()
                .deletePlayer(AppConstants.SUPERVISOR_LOGIN, new DeletePlayerRequest(createdPlayer.getId()), 204, 300L);
    }

    @Test(
            description = "Test delete player with role supervisor",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testDeleteSupervisorPlayer() {
        new PlayerApi()
                .deletePlayer(AppConstants.SUPERVISOR_LOGIN, new DeletePlayerRequest(1L), 403, 300L);
    }

    @Test(
            description = "Test delete player with role supervisor",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "createUserForSupervisor"
    )
    public void testDeleteSupervisorPlayerByOtherRole(String description,
                                                      CreatePlayerQueryParam validParams) {
        GetCreatePlayerResponse createdPlayer = new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, validParams.asMap())
                .as(GetCreatePlayerResponse.class);

        new PlayerApi()
                .deletePlayer(createdPlayer.getLogin(), new DeletePlayerRequest(1L), 403, 300L);
    }

    @Test(
            description = "Test delete player by admin",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "createUserForAdmin"
    )
    public void testDeletePlayerByAdmin(String description, CreatePlayerQueryParam validParams, int statusCode) {
        GetCreatePlayerResponse createdAdminPlayer = new CreatePlayerRequestHelper().getCreatedPlayerAdmin();

        String adminLogin = createdAdminPlayer.getLogin();
        GetCreatePlayerResponse createdPlayer = new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, validParams.asMap())
                .as(GetCreatePlayerResponse.class);

        new PlayerApi()
                .deletePlayer(adminLogin, new DeletePlayerRequest(createdPlayer.getId()), statusCode, 300L);
    }

    @Test(
            description = "Test delete player by admin by himself",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testDeletePlayerByAdminByHimself() {
        GetCreatePlayerResponse createdAdminPlayer = new CreatePlayerRequestHelper().getCreatedPlayerAdmin();

        String adminLogin = createdAdminPlayer.getLogin();
        Long adminId = createdAdminPlayer.getId();

        new PlayerApi()
                .deletePlayer(adminLogin, new DeletePlayerRequest(adminId), 204, 300L);
    }

    @Test(
            description = "Test delete player with role user by himself",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testDeletePlayerByUserByHimself() {
        GetCreatePlayerResponse createdUserPlayer = new CreatePlayerRequestHelper().getCreatedPlayerUser();

        String userLogin = createdUserPlayer.getLogin();
        Long userId = createdUserPlayer.getId();

        new PlayerApi()
                .deletePlayer(userLogin, new DeletePlayerRequest(userId), 204, 300L);
    }

    @Test(
            description = "Test delete player by user",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "createUserForUser"
    )
    public void testDeletePlayerByUser(String description, CreatePlayerQueryParam validParams, int statusCode) {

        GetCreatePlayerResponse createdUserPlayer = new CreatePlayerRequestHelper().getCreatedPlayerUser();
        String userLogin = createdUserPlayer.getLogin();

        GetCreatePlayerResponse createdPlayer = new PlayerApi()
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, validParams.asMap())
                .as(GetCreatePlayerResponse.class);

        new PlayerApi()
                .deletePlayer(userLogin, new DeletePlayerRequest(createdPlayer.getId()), statusCode, 300L);
    }


    @DataProvider
    public Object[][] createUserForSupervisor() {
        CreatePlayerRequestHelper createPlayerHelper = new CreatePlayerRequestHelper();
        return new Object[][]{
                {"Case with role admin", createPlayerHelper.getValidAllRequiredParams().role(Role.ADMIN.name())},
                {"Case with role user", createPlayerHelper.getValidAllRequiredParams().role(Role.USER.name())},
        };
    }

    @DataProvider
    public Object[][] createUserForAdmin() {
        CreatePlayerRequestHelper createPlayerHelper = new CreatePlayerRequestHelper();
        return new Object[][]{
//                {"Case delete another admin", createPlayerHelper.getValidAllRequiredParams().role(Role.ADMIN.name()), 403},  // fixme bug admin can delete another admin
                {"Case delete role user", createPlayerHelper.getValidAllRequiredParams().role(Role.USER.name()), 204},
        };
    }

    @DataProvider
    public Object[][] createUserForUser() {
        CreatePlayerRequestHelper createPlayerHelper = new CreatePlayerRequestHelper();
        return new Object[][]{
                // fixme bug user can delete another user and admin
//                {"Case delete admin", createPlayerHelper.getValidAllRequiredParams().role(Role.ADMIN.name()), 403},
//                {"Case delete another user", createPlayerHelper.getValidAllRequiredParams().role(Role.USER.name()), 403},
        };
    }
}
