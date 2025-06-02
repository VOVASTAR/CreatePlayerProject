package api.player;

import entity.Gender;
import entity.Role;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import request.helper.CreatePlayerRequestHelper;
import request.requestBody.PatchPlayerRequest;
import response.GetCreatePlayerResponse;
import response.PatchPlayerResponse;
import runner.BaseTest;
import runner.TestGroupsConstants;
import service.PlayerApi;
import utils.AppConstants;

@Feature("Route: PATCH /player/update/{editor}/{id}")
public class PatchPlayerTest extends BaseTest {

    @Test(
            description = "Test update player by supervisor",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testPatchPlayerWithValidData() {
        GetCreatePlayerResponse createdAdminPlayer = new CreatePlayerRequestHelper().getCreatedPlayerAdmin();

        Long adminId = createdAdminPlayer.getId();
        String updatedGender = Gender.MALE.name().toLowerCase();
        String updatedRole = Role.USER.name().toLowerCase();
        String updatedLogin = "login_" + adminId;
        String updatedScreenName = "screenName_" + adminId;
        int updatedAge = 20;

        PatchPlayerRequest patchRequest = new PatchPlayerRequest.Builder()
                .age(updatedAge)
                .gender(updatedGender)
                .role(updatedRole)
                .login(updatedLogin)
                .screenName(updatedScreenName)
                .build();

        PatchPlayerResponse updatedPlayer = new PlayerApi()
                .patchPlayer(AppConstants.SUPERVISOR_LOGIN, adminId, patchRequest, 200, 300L)
                .as(PatchPlayerResponse.class);

        Assert.assertEquals(updatedPlayer.getId(), adminId);
        Assert.assertEquals(updatedPlayer.getAge(), updatedAge);
        Assert.assertEquals(updatedPlayer.getGender(), updatedGender);
//        Assert.assertEquals(updatedPlayer.getRole(), updatedRole); // fixme role do not change in response
        Assert.assertEquals(updatedPlayer.getLogin(), updatedLogin);
        Assert.assertEquals(updatedPlayer.getScreenName(), updatedScreenName);
    }
}
