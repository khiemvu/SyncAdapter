package com.qsoft.onlinedio.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.authenticate.User;
import com.qsoft.onlinedio.ui.activity.FirstLaunchActivity;
import com.qsoft.onlinedio.ui.activity.LoginActivity;
import com.qsoft.onlinedio.ui.activity.SlidebarActivity_;
import com.qsoft.onlinedio.validate.Constant;
import com.qsoft.onlinedio.validate.EmailFormatValidator;
import com.qsoft.onlinedio.validate.NetworkUtil;

import static com.qsoft.onlinedio.authenticate.AccountGeneral.sServerAuthenticate;

/**
 * User: khiemvx
 * Date: 11/12/13
 */
@EBean
public class LoginController
{
    private final String TAG = this.getClass().getSimpleName();
    EmailFormatValidator emailFormatValidator = new EmailFormatValidator();
    private String email;
    private String pass;

    @RootContext
    LoginActivity context;

    @SystemService
    AccountManager mAccountManager;

    public void prepareData()
    {
        context.getEtEmail().setText("qsoft@gmail.com");
        context.getEtPass().setText("123456");
    }

    @Click({R.id.login_btBack, R.id.login_btLogin, R.id.tvResetPass, R.id.ibtDelEmail, R.id.ibtDelPass})
    protected void setUpOnClickListener(View view)
    {
        switch (view.getId())
        {
            case R.id.login_btBack:
                Intent intentBack = new Intent(context, FirstLaunchActivity.class);
                context.startActivity(intentBack);
                break;
            case R.id.login_btLogin:
                if (!checkNetwork())
                {
                    showMessageError();
                    break;
                }
                else
                {
                    checkLogin();
                }
                break;
            case R.id.tvResetPass:
                resetPassWord();
                break;
            case R.id.ibtDelEmail:
                context.getEtEmail().setText("");
                break;
            case R.id.ibtDelPass:
                context.getEtPass().setText("");
                break;
        }
    };

    @TextChange({R.id.etEmail, R.id.etPass})
    protected void onTextChangesOnSomeTextViews(CharSequence s, int start, int before,
                                                int count)
    {
        if (!context.getEtEmail().getText().toString().isEmpty())
        {
            context.getIbtDelEmail().setVisibility(View.VISIBLE);
            context.getIbtDelPass().setVisibility(View.INVISIBLE);
        }
        if (!context.getEtPass().getText().toString().isEmpty())
        {
            context.getIbtDelPass().setVisibility(View.VISIBLE);
            context.getIbtDelEmail().setVisibility(View.INVISIBLE);
        }

        if (!context.getEtEmail().getText().toString().isEmpty() && !context.getEtPass().getText().toString().isEmpty())
        {
            context.getBtLogin().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.login_bt_active));
        }
        else
        {
            context.getBtLogin().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.login_bt_normal));
        }
    }

    @AfterTextChange({R.id.etEmail, R.id.etPass})
    protected void afterTextChangedOnSomeTextViews(Editable s)
    {
        context.getEtEmail().setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !context.getEtEmail().getText().toString().isEmpty())
                {
                    context.getIbtDelEmail().setVisibility(View.VISIBLE);
                }
                else
                {
                    context.getIbtDelEmail().setVisibility(View.INVISIBLE);
                }
            }
        });
        context.getEtPass().setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus && !context.getEtPass().getText().toString().isEmpty())
                {
                    context.getIbtDelPass().setVisibility(View.VISIBLE);
                }
                else
                {
                    context.getIbtDelPass().setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void getEmailAndPassword()
    {
        email = context.getEtEmail().getText().toString();
        pass = context.getEtPass().getText().toString();
        if (!email.equals(" ") && !pass.equals(" "))
        {
            context.getBtLogin().setEnabled(true);
            context.getBtLogin().setClickable(true);
        }
    }

    private boolean checkNetwork()
    {
        boolean result = true;
        String status = NetworkUtil.getConnectivityStatusString(context);
        if (status.equals(Constant.NOT_CONNECTED_TO_INTERNET.getValue()))
        {
            result = false;
        }
        return result;
    }

    @UiThread
    protected void showMessageError()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Constant.TITLE_MESSAGE.getValue());
        builder.setMessage(Constant.MESSAGE_CONNECTION_INTERNET.getValue());
        builder.setNegativeButton(R.string.OK, new OkOnClickListener());
        builder.show();
    }

    @UiThread
    protected void resetPassWord()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Forgot Password");
        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.login_reset_password_layout, null))
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //TODO sent password again
                    }
                });
        builder.setView(inflater.inflate(R.layout.login_reset_password_layout, null))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
        builder.show();
    }

    @Background
    protected void getInfoUser(String email, String pass, String accountType)
    {
        Log.d("onlinedio", TAG + "> Started authenticating");

        String authtoken = null;
        Bundle data = new Bundle();
        try
        {
            if ((emailFormatValidator.validate(email)) == false)
            {
                data.putString(Constant.KEY_ERROR_MESSAGE.getValue(), Constant.EMAIL_INVALID.getValue());
            }
            else
            {
                User user = sServerAuthenticate.userSignIn(email, pass, context.getmAuthTokenType());
                if (user.getUser_id() != null)
                {
                    authtoken = user.getAccess_token();
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, email);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(AccountManager.KEY_CALLER_UID, user.getUser_id());
                    data.putString(Constant.PARAM_USER_PASS.getValue(), pass);
                }
                else
                {
                    data.putString(Constant.KEY_ERROR_MESSAGE.getValue(), Constant.EMAIL_OR_PASSWORD_NOT_CORRECT.getValue());
                }
            }
        }
        catch (Exception e)
        {
            data.putString(Constant.KEY_ERROR_MESSAGE.getValue(), e.getMessage());
        }
        final Intent res = new Intent();
        res.putExtras(data);
        resultLogin(res);
    }

    @UiThread
    protected void resultLogin(Intent intent)
    {
        if (intent.hasExtra(Constant.KEY_ERROR_MESSAGE.getValue()))
        {
            Toast.makeText(context.getBaseContext(), intent.getStringExtra(Constant.KEY_ERROR_MESSAGE.getValue()), Toast.LENGTH_SHORT).show();
        }
        else
        {
            finishLogin(intent);
        }
    }

    private void checkLogin()
    {
        email = context.getEtEmail().getText().toString();
        pass = context.getEtPass().getText().toString();
        final String accountType = context.getIntent().getStringExtra(Constant.ARG_ACCOUNT_TYPE.getValue());
        getInfoUser(email, pass, accountType);
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener
    {
        public void onClick(DialogInterface dialog, int which)
        {
            context.getEtEmail().setText(Constant.EMAIL.getValue());
            context.getEtPass().setText(Constant.PASSWORD.getValue());
        }
    }

    private void finishLogin(Intent intent)
    {
        Log.d("onlinedio", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(Constant.PARAM_USER_PASS.getValue());
        String user_id = intent.getStringExtra(AccountManager.KEY_CALLER_UID);
        final Account account = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
        String authtoken = null;

        if (context.getIntent().getBooleanExtra(Constant.ARG_IS_ADDING_NEW_ACCOUNT.getValue(), false) || mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE).length == 0)
        {
            Log.d("onlinedio", TAG + "> finishLogin > addAccountExplicitly");
            authtoken = createAccountOnDevice(intent, accountPassword, user_id, account);
        }
        else
        {
            Log.d("onlinedio", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        callSlideBarActivity(user_id, account, authtoken);
        context.setResult(context.RESULT_OK, intent);
        context.finish();
    }

    private String createAccountOnDevice(Intent intent, String accountPassword, String user_id, Account account)
    {
        String authtoken;
        authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String authtokenType = context.getmAuthTokenType();

        // Creating the account on the device and setting the auth token we got
        // (Not setting the auth token will cause another call to the server to authenticate the user)
        mAccountManager.addAccountExplicitly(account, accountPassword, null);
        mAccountManager.setAuthToken(account, authtokenType, authtoken);
        mAccountManager.setUserData(account, Constant.USER_ID.getValue(), user_id);
        return authtoken;
    }

    private void callSlideBarActivity(String user_id, Account account, String authtoken)
    {
        Intent i = new Intent(context, SlidebarActivity_.class);
        i.putExtra(Constant.AUTHEN_TOKEN.getValue(), authtoken);
        i.putExtra(Constant.USER_ID.getValue(), user_id);
        i.putExtra(Constant.ACCOUNT_CONNECTED.getValue(), account);
        context.startActivity(i);
    }
}
