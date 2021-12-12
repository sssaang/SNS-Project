package com.sssaang.api.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
