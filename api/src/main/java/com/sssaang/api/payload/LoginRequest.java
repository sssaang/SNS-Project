package com.sssaang.api.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    public String usernameOrEmail;

    @NotBlank
    public String password;
}
