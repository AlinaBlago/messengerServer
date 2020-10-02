package com.finalproject.server.security2.properties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class JWTProperties {
    @NotBlank
    private String secret;

    @Min(value = 60_000)
    @Max(value = 3_600_000)
    private long expireIn;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }
}


