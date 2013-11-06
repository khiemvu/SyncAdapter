package com.qsoft.onlinedio.authenticate;

/**
 * User: khiemvx
 * Date: 10/29/13
 */
public interface ServerAuthenticate
{
    public String userSignUp(final String name, final String email, final String pass, String authType) throws Exception;

    public User userSignIn(final String user, final String pass, String authType) throws Exception;
}
