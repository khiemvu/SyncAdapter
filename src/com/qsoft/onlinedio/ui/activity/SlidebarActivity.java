package com.qsoft.onlinedio.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.adapter.Sidebar;
import com.qsoft.onlinedio.fragment.HomeFragment_;
import com.qsoft.onlinedio.validate.Constant;

/**
 * User: Dell 3360
 * Date: 10/20/13
 * Time: 8:39 AM
 */
@EActivity (R.layout.slidebar)
public class SlidebarActivity extends FragmentActivity
{
    private String authen_token;
    private String user_id;
    private Account mConnectedAccount;

    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    private static final int HOME = 0;
    private static final int FAVORITE = 1;
    private static final int FOLLOWING = 2;
    private static final int AUDIENCE = 3;
    private static final int GENRES = 4;
    private static final int SETTING = 5;
    private static final int HELP_CENTER = 6;
    private static final int SIGN_OUT = 7;

    @ViewById(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @ViewById(R.id.slidebar_listOption)
    protected ListView lvOption;

    @ViewById(R.id.sidebar_ivProfile)
    protected ImageView ivProfile;

    @ViewById(R.id.left_drawer)
    protected RelativeLayout rlLeftDrawer;

    private HomeFragment_ homeFragment;
    private ActionBarDrawerToggle mDrawerToggle;

    public static Context context;
    @SystemService
    protected AccountManager mAccountManager;

    @AfterViews
    protected void afterViews(){
        context = this.getApplicationContext();
        getAuthTokenAndAccount();
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        setUpDataListOption(this);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };
        moveHomeFragment();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void moveHomeFragment()
    {
        homeFragment = new HomeFragment_();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.AUTHEN_TOKEN.getValue(), authen_token);
        bundle.putParcelable(Constant.ACCOUNT_CONNECTED.getValue(), mConnectedAccount);
        homeFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.slidebar_homeFragment, homeFragment);
        fragmentTransaction.commit();
    }

    private void getAuthTokenAndAccount()
    {
        Intent intent = getIntent();
        authen_token = intent.getStringExtra(Constant.AUTHEN_TOKEN.getValue());
        mConnectedAccount = intent.getParcelableExtra(Constant.ACCOUNT_CONNECTED.getValue());
        user_id = intent.getStringExtra(Constant.USER_ID.getValue());
    }

    @ItemClick(R.id.slidebar_listOption)
    protected void slideBarItemClicked(int position)
    {
        switch (position)
        {
            case HOME:
                moveHomeFragment();
                finish();
                break;
            case SIGN_OUT:
                mAccountManager.removeAccount(mConnectedAccount, null, null);
                startActivity(new Intent(getApplicationContext(),LoginActivity_.class));
                finish();
                break;
        }
        setCloseListOption();
    }

    @Click(R.id.sidebar_ivProfile)
    protected void ivProfileCick(){
        showProfile();
        setCloseListOption();
        finish();
    }

    private void showProfile()
    {
        Intent intent = new Intent(this, ProfileActivity_.class);
        intent.putExtra(Constant.AUTHEN_TOKEN.getValue(), authen_token);
        intent.putExtra(Constant.USER_ID.getValue(), user_id);
        intent.putExtra(Constant.ACCOUNT_CONNECTED.getValue(), mConnectedAccount);
        startActivity(intent);
    }

    private void setUpDataListOption(Context context)
    {
        lvOption.setAdapter(new Sidebar(context, R.layout.slidebar_listoptions, listOptionName));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.slidebar_profileFragment);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setOpenListOption()
    {
        mDrawerLayout.openDrawer(rlLeftDrawer);
    }

    public void setCloseListOption()
    {
        mDrawerLayout.closeDrawer(rlLeftDrawer);
    }
}
