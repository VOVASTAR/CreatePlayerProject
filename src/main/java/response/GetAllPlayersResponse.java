package response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class GetAllPlayersResponse extends BaseResponse {
    private List<Player> players;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Player {
        private long id;
        private String screenName;
        private String gender;
        private int age;
        private String role;
    }
}
