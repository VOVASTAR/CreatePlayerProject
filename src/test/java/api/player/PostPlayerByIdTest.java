package api.player;

import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import request.requestBody.PostPlayerIdRequest;
import response.PostPlayerResponse;
import runner.BaseTest;
import runner.TestGroupsConstants;
import service.PlayerApi;

@Feature("Route: POST /player/get")
public class PostPlayerByIdTest extends BaseTest {

    @Test(
            description = "Test post player by id",
            groups = {TestGroupsConstants.DEFAULT})
    public void testPostPlayerById() {
        PostPlayerResponse actualResponse = new PlayerApi()
                .postPlayerById(new PostPlayerIdRequest(1L), 200, 10500L) // fixme bug sometimes response time over 10 seconds
                .as(PostPlayerResponse.class);

        Assert.assertEquals(actualResponse.getId(), 1L);
        Assert.assertEquals(actualResponse.getLogin(), "supervisorLogin");
        Assert.assertEquals(actualResponse.getScreenName(), "supervisorScreenName");
        Assert.assertEquals(actualResponse.getGender(), "male");
        Assert.assertEquals(actualResponse.getPassword(), "123AbC098");
        Assert.assertEquals(actualResponse.getAge(), 33);
        Assert.assertEquals(actualResponse.getRole(), "supervisor");
    }

    @Test(
            description = "Test post player by not existing id",
            groups = {TestGroupsConstants.DEFAULT},
            dataProvider = "notExistingId"
    )
    public void testPostPlayerByNotExistingId(String description, Long id) {
        new PlayerApi()
                .postPlayerById(new PostPlayerIdRequest(id), 200, 500L); // fixme possible 404 return better
    }

    @DataProvider
    public Object[][] notExistingId() {
        return new Object[][]{
                {"Case with id = 0", 0L},
                {"Case with id = -1", -1L},
                {"Case with id = MAX_LONG", Long.MAX_VALUE}
        };
    }
}