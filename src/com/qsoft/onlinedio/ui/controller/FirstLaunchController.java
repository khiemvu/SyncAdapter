package com.qsoft.onlinedio.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.ui.activity.SlidebarActivity_;
import com.qsoft.onlinedio.validate.Constant;

/**
 * User: khiemvx
 * Date: 11/12/13
 */
@EBean
public class FirstLaunchController
{
    private String TAG = this.getClass().getSimpleName();

    private Account mConnectedAccount;

    @SystemService
    AccountManager mAccountManager;

    @RootContext
    Activity context;

    @Click(R.id.launch_btLogin)
    protected void btLoginClicked()
    {
        getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        Log.i(TAG, "Get token if it existed");
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, context, null, null,
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
                                String user_id = mAccountManager.getUserData(mConnectedAccount, Constant.USER_ID.getValue());
                                Intent intent = new Intent(context, SlidebarActivity_.class);
                                intent.putExtra(Constant.AUTHEN_TOKEN.getValue(), authtoken);
                                intent.putExtra(Constant.USER_ID.getValue(), user_id);
                                intent.putExtra(Constant.ACCOUNT_CONNECTED.getValue(), mConnectedAccount);
                                context.startActivity(intent);
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

    @UiThread
    protected void showMessage(final String msg)
    {
        if (TextUtils.isEmpty(msg))
        {
            return;
        }
        Toast.makeText(context.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
