package com.sssaang.api.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    public String name;

    @NotBlank
    @Size(min = 3, max = 15)
    public String username;

    @NotBlank
    @Size(max = 40)
    @Email
    public String email;

    @NotBlank
    @Size(min = 6, max = 20)
    public String password;
}
