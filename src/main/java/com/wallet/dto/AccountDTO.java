package com.wallet.dto;

import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * This DTO is used for transferring account details as response.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Long accountId;
    private ResourceStatus accountStatus;
    private BigDecimal balance;
    private String currency;

}
