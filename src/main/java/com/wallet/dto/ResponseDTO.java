package com.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This DTO is used as common response DTO .
 *
 * @author Malinda
 *
 */


@Data
@AllArgsConstructor
public class ResponseDTO {

    private String status;
    private Object description;
}
