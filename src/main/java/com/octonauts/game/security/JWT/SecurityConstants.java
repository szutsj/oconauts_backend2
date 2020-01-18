package com.octonauts.game.security.JWT;

public final class SecurityConstants {

    public static final String SECRET = "OctonautsIsAGoodGame";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 1 * 24 * 60 * 60 * 1000; // 1 day in millisec
}
