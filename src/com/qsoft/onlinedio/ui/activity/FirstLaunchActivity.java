package com.qsoft.onlinedio.ui.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.ui.controller.FirstLaunchController;

/**
 * Created with IntelliJ IDEA.
 * User: Dell 3360
 * Date: 10/14/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
@EActivity(R.layout.first_launch_layout)
public class FirstLaunchActivity extends AccountAuthenticatorActivity
{
    @ViewById(R.id.launch_btLogin)
    protected Button launch_btLogin;

    @Bean
    FirstLaunchController firstLaunchController;
}