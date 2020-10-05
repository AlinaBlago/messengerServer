package com.finalproject.server.security.token;

import java.util.Random;

public class CustomToken {
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";

    public static String getToken() {
        StringBuilder token = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }
}
