package com.wallet.dto;

import com.wallet.model.Account;
import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(staticName = "build")

public class PlayerDTO {

    private Long playerId;
    private String firstName;
    private String lastName;
    private ZonedDateTime createdDate;
    private ResourceStatus playerStatus;
    private Long accountId;

}
