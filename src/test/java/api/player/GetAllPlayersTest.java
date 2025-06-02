package api.player;

import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import response.GetAllPlayersResponse;
import runner.BaseTest;
import runner.TestGroupsConstants;
import service.PlayerApi;

import java.util.List;

@Feature("Route: GET /player/get/all")
public class GetAllPlayersTest extends BaseTest {

    @Test(
            description = "Test get all players",
            groups = {TestGroupsConstants.DEFAULT}
    )
    public void testGetAllPlayers() {
        GetAllPlayersResponse getAllPlayersResponse = new PlayerApi()
                .getAllPlayers(200, 300L)
                .as(GetAllPlayersResponse.class);

        SoftAssert softAssert = new SoftAssert();
        List<GetAllPlayersResponse.Player> players = getAllPlayersResponse.getPlayers();
//        softAssert.assertEquals(players.size(), 1); // todo should take quantity from BD
        // todo should take player from BD
        GetAllPlayersResponse.Player firstPlayer = players.stream()
                .filter(player -> player.getId() == 1L)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Player with ID 1 not found"));

        softAssert.assertEquals(firstPlayer.getId(), 1L);
        softAssert.assertEquals(firstPlayer.getScreenName(), "supervisorScreenName");
        softAssert.assertEquals(firstPlayer.getGender(), "male");
        softAssert.assertEquals(firstPlayer.getAge(), 33);
//        softAssert.assertEquals(firstPlayer.getRole(), "supervisor"); // fixme bug field is not returned in response
        softAssert.assertAll();
    }
}
