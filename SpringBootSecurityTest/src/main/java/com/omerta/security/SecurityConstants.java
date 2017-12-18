package com.omerta.security;

public class SecurityConstants {
    public static final String SECRET = "alice";
    public static final long EXPIRATION_TIME = 360000000; //3600 saniye = 60 dakika 
    public static final long LONG_EXPIRATION_TIME = 360000000 * 24 * 365 * 5  ; //5 YIL
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String JWT_AUTH_CLAIM_STRING = "roles";
}