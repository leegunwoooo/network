package appjem.network.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAlarmRequest {

    private Long id;
    private String name;
    private String description;
    private Boolean active;

}
