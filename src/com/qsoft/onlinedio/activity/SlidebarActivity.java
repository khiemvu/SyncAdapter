package com.qsoft.onlinedio.activity;

import android.accounts.Account;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.qsoft.onlinedio.adapter.Sidebar;
import com.qsoft.onlinedio.fragment.HomeFragment;
import com.qsoft.onlinedio.R;


/**
 * User: Dell 3360
 * Date: 10/20/13
 * Time: 8:39 AM
 */
public class SlidebarActivity extends FragmentActivity
{
    private String authen_token;
    private String user_id;
    private Account mConnectedAccount;

    final String[] listOptionName = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] item = {"HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment", "HomeFragment"};
    private static final int HOME = 0;
    private static final int FAVORITE = 1;
    private static final int FOLLOWING = 2;
    private static final int AUDIENCE = 3;
    private static final int GENRES = 4;
    private static final int SETTING = 5;
    private static final int HELP_CENTER = 6;
    private static final int SIGN_OUT = 7;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvOption;
    private ImageView ivProfile;
    private HomeFragment homeFragment;
    private RelativeLayout rlLeftDrawer;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        //get authen_token and account from first lauch
        Intent intent = getIntent();
        authen_token = intent.getStringExtra(FirstLaunchActivity.AUTHEN_TOKEN);
        mConnectedAccount = intent.getParcelableExtra(FirstLaunchActivity.ACCOUNT_CONNECTED);
        user_id = intent.getStringExtra(LoginActivity.USER_ID);
        setUpUI();
        setUpDataListOption(this);
        setUpListenerController();

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
                super.onDrawerSlide(drawerView, slideOffset);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };
        homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FirstLaunchActivity.AUTHEN_TOKEN, authen_token);
        bundle.putParcelable(FirstLaunchActivity.ACCOUNT_CONNECTED,mConnectedAccount);
        homeFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.slidebar_homeFragment, homeFragment);
        fragmentTransaction.commit();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpUI()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        lvOption = (ListView) findViewById(R.id.slidebar_listOption);
        ivProfile = (ImageView) findViewById(R.id.sidebar_ivProfile);
        rlLeftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
    }

    private void setUpListenerController()
    {
        lvOption.setOnItemClickListener(onItemClickListener);
        ivProfile.setOnClickListener(onClickListener);
    }

    private final ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l)
        {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (index)
            {
                case HOME:
                    ft.replace(R.id.slidebar_homeFragment, new HomeFragment(), "HomeFragment");
                    ft.addToBackStack("HomeFragment");
                    ft.commit();
                    break;
                case SIGN_OUT:
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    break;
            }
            setCloseListOption();
        }
    };

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.sidebar_ivProfile:
                    showProfile();
                    setCloseListOption();
                    break;
            }
        }
    };

    private void showProfile()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(FirstLaunchActivity.AUTHEN_TOKEN, authen_token);
        intent.putExtra(LoginActivity.USER_ID,user_id);
        intent.putExtra(FirstLaunchActivity.ACCOUNT_CONNECTED, mConnectedAccount);
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
