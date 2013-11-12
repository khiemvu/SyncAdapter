package com.qsoft.onlinedio.validate;

public enum Constant
{
    CONNECT_TITLE("Connect error"),
    CONNECT_MESSAGE("Please connect first"),
    EMAIL("qsoft@gmail.com"),
    PASSWORD("123456"),
    NOT_CONNECTED_TO_INTERNET("Not connected to Internet"),
    TITLE_MESSAGE("Error Signing In"),
    MESSAGE_CONNECTION_INTERNET("There is no connection to the internet"),
    EMAIL_INVALID("Invalid email address"),
    EMAIL_OR_PASSWORD_NOT_CORRECT("Email address or password is incorrect."),
    AUTHEN_TOKEN("authen_token"),
    ACCOUNT_CONNECTED("account_connected"),
    USER_ID("user_id"),
    ARG_ACCOUNT_TYPE("ACCOUNT_TYPE"),
    ARG_AUTH_TYPE("AUTH_TYPE"),
    ARG_ACCOUNT_NAME("ACCOUNT_NAME"),
    ARG_IS_ADDING_NEW_ACCOUNT("IS_ADDING_ACCOUNT"),
    PARAM_USER_PASS("USER_PASS"),
    KEY_ERROR_MESSAGE("ERR_MSG");

    private String value;

    public String getValue()
    {
        return value;
    }

    Constant(String value)
    {
        this.value = value;
    }
}