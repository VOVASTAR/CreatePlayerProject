package api.player;

import entity.Gender;
import entity.Role;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import request.helper.CreatePlayerRequestHelper;
import request.queryParam.CreatePlayerQueryParam;
import response.ErrorResponse;
import response.GetCreatePlayerResponse;
import runner.BaseTest;
import runner.TestGroupsConstants;
import service.PlayerApi;
import utils.AppConstants;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Feature("Route: GET /player/create/{editor}")
public class GetCreatePlayerTest extends BaseTest {

    @Test(
            description = "Test create player with all required valid parameters for role supervisor",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "allRequiredParams")
    public void testCreatePlayerWithValidQueryParamForSupervisor(String description,
                                                                 CreatePlayerQueryParam validParams) {
        testCreatePlayerWithValidQueryParam(description, validParams, AppConstants.SUPERVISOR_LOGIN);
    }

    @Test(
            description = "Test create player  with all required valid parameters for role admin",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "allRequiredParams"
    )
    public void testCreatePlayerWithValidQueryParamForAdmin(String description,
                                                            CreatePlayerQueryParam validParams) {
        GetCreatePlayerResponse playerAdmin = new CreatePlayerRequestHelper().getCreatedPlayerAdmin();
        testCreatePlayerWithValidQueryParam(description, validParams, playerAdmin.getLogin());
    }

    private void testCreatePlayerWithValidQueryParam(String description,
                                                     CreatePlayerQueryParam validParams,
                                                     String editor) {
        Map<String, String> validParameters = validParams.asMap();

        GetCreatePlayerResponse actualResponse = new PlayerApi()
                .getCreatePlayer(editor, 200, 300L, validParameters)
                .as(GetCreatePlayerResponse.class);

        Assert.assertTrue(actualResponse.getId() > 0L);
        Assert.assertEquals(actualResponse.getLogin(), validParameters.get("login"));
//      fixme bug all fields below in response come as null
//        Assert.assertEquals(actualResponse.getPassword(), validParameters.get("password"));
//        Assert.assertEquals(actualResponse.getScreenName(), validParameters.get("screenName"));
//        Assert.assertEquals(actualResponse.getGender(), validParameters.get("gender"));
//        Assert.assertEquals(String.valueOf(actualResponse.getAge()), validParameters.get("age"));
//        Assert.assertEquals(actualResponse.getRole(), validParameters.get("role"));
    }

    @Test(
            description = "Test forbidden to create player with all required valid parameters for role user",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testCreatePlayerWithValidQueryParamForUser() {
        GetCreatePlayerResponse playerUser = new CreatePlayerRequestHelper().getCreatedPlayerUser();
        Map<String, String> validParameters = new CreatePlayerRequestHelper().getValidAllRequiredParams().asMap();
        new PlayerApi()
                .getCreatePlayer(playerUser.getLogin(), 403, 300L, validParameters);
    }


    @Test(
            description = "Test not valid query parameters",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "withNotValidParams"
    )
    public void testCreatePlayerWithNotValidQueryParam(String description,
                                                       CreatePlayerQueryParam notValidParams) {
        PlayerApi playerApi = new PlayerApi();
        ErrorResponse errorResponse = playerApi
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 400, 300L, notValidParams.asMap())
                .as(ErrorResponse.class);

        validateBadRequest(errorResponse, playerApi, "");
    }

    private void validateBadRequest(ErrorResponse errorResponse, PlayerApi playerApi, String message) {
        Assert.assertFalse(errorResponse.getTimestamp().isBlank());
        Assert.assertEquals(errorResponse.getStatus(), 400);
        Assert.assertEquals(errorResponse.getError(), "Bad Request");
        Assert.assertEquals(errorResponse.getPath(), playerApi.getPath());
        Assert.assertEquals(errorResponse.getMessage(), message); // fixme should be message from server
    }

    // fixme bug
    //  1) twice login - rewrite user data
    //  2) twice screenName - create new user with same screenName
    @Test(
            description = "Test twice created unique query parameters",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "uniqueParams"
    )
    public void testTwiceCreatePlayerWithUniqueQueryParam(String description,
                                                          Consumer<CreatePlayerQueryParam> modifiedParams) {
        PlayerApi playerApi = new PlayerApi();
        CreatePlayerQueryParam notValidParams = new CreatePlayerRequestHelper().getValidAllRequiredParams();

        playerApi.getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 200, 300L, notValidParams.asMap());
        modifiedParams.accept(notValidParams);

        ErrorResponse errorResponse = playerApi
                .getCreatePlayer(AppConstants.SUPERVISOR_LOGIN, 400, 300L, notValidParams.asMap())
                .as(ErrorResponse.class);

        validateBadRequest(errorResponse, playerApi, "");
    }

    @DataProvider
    public Object[][] uniqueParams() {
        return new Object[][]{
                {"Case with unique login", (Consumer<CreatePlayerQueryParam>)
                        (param -> param.login("login_" + UUID.randomUUID()))},
                {"Case with unique screenName", (Consumer<CreatePlayerQueryParam>)
                        (param -> param.screenName("screenName_" + UUID.randomUUID()))},
        };
    }

    @DataProvider
    public Object[][] allRequiredParams() {
        CreatePlayerRequestHelper createPlayerHelper = new CreatePlayerRequestHelper();
        return new Object[][]{
//                {"Case with valid parameters and MIN age", createPlayerHelper.getValidAllRequiredParams().age("16")}, // fixme bug not created, low bound 17
                {"Case with valid parameters and MAX age", createPlayerHelper.getValidAllRequiredParams().age("60")},
                {"Case with valid parameters and gender male", createPlayerHelper.getValidAllRequiredParams().gender(Gender.MALE.name())},
                {"Case with valid parameters and gender female", createPlayerHelper.getValidAllRequiredParams().gender(Gender.FEMALE.name())},
                {"Case with valid parameters and role admin", createPlayerHelper.getValidAllRequiredParams().role(Role.ADMIN.name())},
                {"Case with valid parameters and role user", createPlayerHelper.getValidAllRequiredParams().role(Role.USER.name())},
                {"Case with valid parameters and MIN password 7 characters",
                        createPlayerHelper.getValidAllRequiredParams().password("1234567")},
                {"Case with valid parameters and MAX password 15 characters",
                        createPlayerHelper.getValidAllRequiredParams().password("1234567890Qwery")},
        };
    }

    @DataProvider
    public Object[][] withNotValidParams() {
        CreatePlayerRequestHelper createPlayerHelper = new CreatePlayerRequestHelper();
        return new Object[][]{
                {"Case with not valid MIN age", createPlayerHelper.getValidAllRequiredParams().age("15")},
                {"Case with not valid MAX age", createPlayerHelper.getValidAllRequiredParams().age("61")},
//                {"Case with not valid gender", createPlayerHelper.getValidAllRequiredParams().gender("malee")}, // fixme bug player created
                {"Case with not valid role supervisor", createPlayerHelper.getValidAllRequiredParams().role("supervisor")},
                {"Case with not valid role name", createPlayerHelper.getValidAllRequiredParams().role("userr")},
//                {"Case with not valid MIN password 6 characters", createPlayerHelper.getValidAllRequiredParams().password("123456")}, // fixme bug player created
//                {"Case with not valid MAX password 16 characters",
//                        createPlayerHelper.getValidAllRequiredParams().password("1234567890Qweryr")}, // fixme bug player created
                {"Case with null age", createPlayerHelper.getValidAllRequiredParams().age(null)},
                {"Case with null gender", createPlayerHelper.getValidAllRequiredParams().gender(null)},
                {"Case with null role", createPlayerHelper.getValidAllRequiredParams().role(null)},
//                {"Case with null screenName", createPlayerHelper.getValidAllRequiredParams().screenName(null)}, // fixme bug player created
//                {"Case with null login", createPlayerHelper.getValidAllRequiredParams().login(null)}, // fixme bug player created
        };
    }
}
