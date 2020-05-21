package com.akulkarni.ppmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/users/**";
    public static final String SECRET = "SecretKeyToGenJWT";
    public static final String TOKEN_PREFIX = "Bearer "; //space is intentional
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 3000_00000; // 30 sec

}