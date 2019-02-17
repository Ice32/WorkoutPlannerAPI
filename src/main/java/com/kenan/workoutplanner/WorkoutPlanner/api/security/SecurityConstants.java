package com.kenan.workoutplanner.WorkoutPlanner.api.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String HEADER_STRING_REFRESH_TOKEN = "RefreshToken";
    public static final String SIGN_UP_URL = "/users/registration";
    public static final String REFRESH_URL = "/users/refresh";
    public static final String INFO_PAGE_URL = "/";
}
