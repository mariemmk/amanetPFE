package com.example.amanetpfe.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class OTPUtils {

    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator(
            new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder().build()
    );

    public static String generateSecret() {
        return gAuth.createCredentials().getKey();
    }

    public static int getTOTPCode(String secret) {
        return gAuth.getTotpPassword(secret);
    }

    public static boolean validateTOTP(int code, String secret) {
        return gAuth.authorize(secret, code);
    }
}
