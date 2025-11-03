package org.ozbeman.ebento.utils;

public final class RegexPatternUtils {
    private RegexPatternUtils() {}
    public static final String USER_NAME = "^[a-zA-Z0-9_]{5,25}$";
    public static final String VARIFICATION_CODE = "^[0-9]{4}$";
    public static final String PHONE_NUMBER = "^[0-9]{10,15}$";
    public static final String CHANNEL_TITLE = "^[a-zA-Z0-9 ]{0,50}$";
}
