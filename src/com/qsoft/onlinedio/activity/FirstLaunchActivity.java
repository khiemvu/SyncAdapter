package com.qsoft.onlinedio.activity;

import android.accounts.*;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.authenticate.AccountGeneral;

/**
 * Created with IntelliJ IDEA.
 * User: Dell 3360
 * Date: 10/14/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class FirstLaunchActivity extends AccountAuthenticatorActivity
{
    private String TAG = this.getClass().getSimpleName();
    public final static String AUTHEN_TOKEN = "authen_token";
    public final static String ACCOUNT_CONNECTED = "account_connected";
    private Button launch_btLogin;
    private AccountManager mAccountManager;
    private Account mConnectedAccount;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_launch_layout);
        mAccountManager = AccountManager.get(this);

        launch_btLogin = (Button) findViewById(R.id.launch_btLogin);
        launch_btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });

    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        Log.i(TAG, "Get token if it existed");
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        Bundle bnd = null;
                        try
                        {
                            bnd = future.getResult();
                            final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            showMessage(((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL"));
                            if (authtoken != null)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
                                String user_id = mAccountManager.getUserData(mConnectedAccount,LoginActivity.USER_ID);
                                Intent intent = new Intent(FirstLaunchActivity.this, SlidebarActivity.class);
                                intent.putExtra(AUTHEN_TOKEN, authtoken);
                                intent.putExtra(LoginActivity.USER_ID,user_id);
                                intent.putExtra(ACCOUNT_CONNECTED, mConnectedAccount);
                                startActivity(intent);
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    }

    private void showMessage(final String msg)
    {
        if (TextUtils.isEmpty(msg))
        {
            return;
        }

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}