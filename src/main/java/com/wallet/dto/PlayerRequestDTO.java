package com.wallet.dto;

import com.wallet.model.Account;
import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * This DTO is used as a player request.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PlayerRequestDTO {

    @Digits(integer = 15, fraction = 0, message = "Invalid Player ID")
    private Long playerId;
    @Length(min = 1, max = 30, message = "Lenth of first name should be in between 1,30")
    private String firstName;
    @Length(min = 1, max = 30, message = "Lenth of Last name should be in between 1,30")
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdDate = ZonedDateTime.now();
    @NotNull
    private ResourceStatus playerStatus;
    private Account account;

}
