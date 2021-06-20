package com.churilovich.bortnik.darya.shop.on.sofa.service.constants;

public final class RegexValueConstants {
    public static final String EMAIL_REGEX_VALUE =
            "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public static final String FIRST_NAME_REGEX_VALUE = "^[a-zA-Z]{0,20}$";
    public static final String MIDDLE_NAME_REGEX_VALUE = "^[a-zA-Z]{0,40}$";
    public static final String LAST_NAME_REGEX_VALUE = "^[a-zA-Z]{0,40}$";

    private RegexValueConstants() {
    }
}
