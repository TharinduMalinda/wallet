package com.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(staticName = "build")

public class PlayerDTO {

    private Long playerId;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private ZonedDateTime createdDate;
    private ResourceStatus playerStatus;
    private Long accountId;

}
