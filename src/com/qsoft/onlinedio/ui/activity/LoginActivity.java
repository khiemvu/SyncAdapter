package com.qsoft.onlinedio.ui.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.ui.controller.LoginController;
import com.qsoft.onlinedio.validate.Constant;

@EActivity(R.layout.login_layout)
public class LoginActivity extends AccountAuthenticatorActivity
{
    @ViewById(R.id.login_btLogin)
    protected Button btLogin;

    @ViewById(R.id.login_btBack)
    protected Button btBack;

    @ViewById(R.id.tvResetPass)
    protected TextView tvResetPas;

    @ViewById(R.id.etEmail)
    protected EditText etEmail;

    @ViewById(R.id.etPass)
    protected EditText etPass;

    @ViewById(R.id.ibtDelEmail)
    protected ImageButton ibtDelEmail;

    @ViewById(R.id.ibtDelPass)
    protected ImageButton ibtDelPass;

    @SystemService
    protected AccountManager mAccountManager;
    private String mAuthTokenType;

    @Bean
    LoginController loginController;


    @AfterViews
    protected void afterView()
    {
        loginController.prepareData();
        mAuthTokenType = getIntent().getStringExtra(Constant.ARG_AUTH_TYPE.getValue());
        if (mAuthTokenType == null)
        {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        loginController.getEmailAndPassword();
    }

    public EditText getEtEmail()
    {
        return etEmail;
    }

    public void setEtEmail(EditText etEmail)
    {
        this.etEmail = etEmail;
    }

    public EditText getEtPass()
    {
        return etPass;
    }

    public void setEtPass(EditText etPass)
    {
        this.etPass = etPass;
    }

    public Button getBtLogin()
    {
        return btLogin;
    }

    public void setBtLogin(Button btLogin)
    {
        this.btLogin = btLogin;
    }

    public ImageButton getIbtDelEmail()
    {
        return ibtDelEmail;
    }

    public void setIbtDelEmail(ImageButton ibtDelEmail)
    {
        this.ibtDelEmail = ibtDelEmail;
    }

    public ImageButton getIbtDelPass()
    {
        return ibtDelPass;
    }

    public void setIbtDelPass(ImageButton ibtDelPass)
    {
        this.ibtDelPass = ibtDelPass;
    }

    public String getmAuthTokenType()
    {
        return mAuthTokenType;
    }

    public void setmAuthTokenType(String mAuthTokenType)
    {
        this.mAuthTokenType = mAuthTokenType;
    }
}
