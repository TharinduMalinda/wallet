package com.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

/**
 * This DTO is used as a authorization request body.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
