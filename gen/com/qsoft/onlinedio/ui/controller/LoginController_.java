//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.ui.controller;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.qsoft.onlinedio.ui.activity.LoginActivity;

public final class LoginController_
    extends LoginController
{

    private Context context_;
    private Handler handler_ = new Handler();

    private LoginController_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.login_btBack);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginController_.this.setUpOnClickListener(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.login_btLogin);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginController_.this.setUpOnClickListener(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.tvResetPass);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginController_.this.setUpOnClickListener(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.ibtDelEmail);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginController_.this.setUpOnClickListener(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.ibtDelPass);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginController_.this.setUpOnClickListener(view);
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) findViewById(com.qsoft.onlinedio.R.id.etEmail));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void afterTextChanged(Editable s) {
                        LoginController_.this.afterTextChangedOnSomeTextViews(s);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        LoginController_.this.onTextChangesOnSomeTextViews(s, start, before, count);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) findViewById(com.qsoft.onlinedio.R.id.etPass));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void afterTextChanged(Editable s) {
                        LoginController_.this.afterTextChangedOnSomeTextViews(s);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        LoginController_.this.onTextChangesOnSomeTextViews(s, start, before, count);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                }
                );
            }
        }
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        mAccountManager = ((AccountManager) context_.getSystemService(Context.ACCOUNT_SERVICE));
        if (context_ instanceof LoginActivity) {
            context = ((LoginActivity) context_);
        }
    }

    public static LoginController_ getInstance_(Context context) {
        return new LoginController_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

    @Override
    public void resetPassWord() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginController_.super.resetPassWord();
                } catch (RuntimeException e) {
                    Log.e("LoginController_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void resultLogin(final Intent intent) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginController_.super.resultLogin(intent);
                } catch (RuntimeException e) {
                    Log.e("LoginController_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void showMessageError() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginController_.super.showMessageError();
                } catch (RuntimeException e) {
                    Log.e("LoginController_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void getInfoUser(final String email, final String pass, final String accountType) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginController_.super.getInfoUser(email, pass, accountType);
                } catch (RuntimeException e) {
                    Log.e("LoginController_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

}
