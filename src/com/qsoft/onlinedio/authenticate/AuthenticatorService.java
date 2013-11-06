package com.qsoft.onlinedio.authenticate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * User: khiemvx
 * Date: 10/29/13
 */
public class AuthenticatorService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }
}
