package request.requestBody;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DeletePlayerRequest extends BaseRequest {
    private Long playerId;

    public DeletePlayerRequest(Long playerId) {
        this.playerId = playerId;
    }
}