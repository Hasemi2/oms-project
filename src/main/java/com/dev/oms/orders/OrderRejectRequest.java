package com.dev.oms.orders;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderRejectRequest {

    @NotNull
    @NotBlank(message = "rejectMessage must be provided")
    private String message;

    public String getMessage() {
        return message;
    }
}
