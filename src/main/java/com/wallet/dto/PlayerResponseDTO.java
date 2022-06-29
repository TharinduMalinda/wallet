package com.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

/**
 * This DTO is used to store player details, for player response.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PlayerResponseDTO {

    private Long playerId;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private ZonedDateTime createdDate;
    private ResourceStatus playerStatus;
    private Long accountId;

}
