package com.sssaang.api.payload;

public class JwtAuthenticationResponse {
    public String accessToken;
    public String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
