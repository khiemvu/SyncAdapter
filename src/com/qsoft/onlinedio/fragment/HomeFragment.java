package com.qsoft.onlinedio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.adapter.HomeFeeds;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.syncadapter.HomeFeedSyncAdapter;
import com.qsoft.onlinedio.ui.activity.SlidebarActivity;
import com.qsoft.onlinedio.util.DateTime;
import com.qsoft.onlinedio.validate.Constant;
import com.qsoft.onlinedio.validate.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/14/13
 */
@EFragment (R.layout.home_layout)
public class HomeFragment extends Fragment
{
    private static HomeFeeds adapter;
    public static ArrayList<HashMap<String, String>> arraylist;
    public static final String COMMENT = "comment";
    public static final String LIKE = "like";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String TIME = "time";
    public static final String AVATAR = "avatar";
    public static DateTime dateTime = new DateTime();
    private String auth_token;
    private String TAG = this.getClass().getSimpleName();
    private Account mConnectedAccount;
    @SystemService
    protected AccountManager mAccountManager;
    private static ProgressDialog mProgressDialog;

    @ViewById(R.id.btNavigate)
    protected Button btNavigate;

    @ViewById(R.id.home_lvDetail)
    protected static ListView home_lvDetail;


    public static Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            String aResponse = msg.getData().getString(HomeFeedSyncAdapter.MESSAGE_KEY);
            if (aResponse.equals(HomeFeedSyncAdapter.DONE))
            {
                setUpDataToHomeListView();
                mProgressDialog.dismiss();
            }
        }
    };

    @AfterViews
    protected void afterViews()
    {
        getAuthenTokenAndAccount();

        Thread background = new Thread(new Runnable()
        {
            public void run()
            {
                performSyncData();
            }
        });
        if (checkNetwork())
        {
            background.start();
        }
        else
        {
            setUpDataToHomeListView();
        }
    }

    private boolean checkNetwork()
    {
        Log.i(TAG, "Check network available");
        boolean result = true;
        String status = NetworkUtil.getConnectivityStatusString(SlidebarActivity.context);
        if (status.equals(Constant.NOT_CONNECTED_TO_INTERNET.getValue()))
        {
            result = false;
        }
        return result;
    }


    private void performSyncData()
    {
        Log.i(TAG, "Perform sync");
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mProgressDialog = new ProgressDialog(getActivity());
                // Set progressdialog message
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
        });
        refreshAuthToken();
        requestSyncData();
    }

    private void requestSyncData()
    {
        String authority = Contract.AUTHORITY;
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mConnectedAccount, authority, bundle);
    }

    private void refreshAuthToken()
    {
        Log.i(TAG, "Refresh auth token");
        mAccountManager.invalidateAuthToken(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, auth_token);
        try
        {
            auth_token = mAccountManager.getAuthToken(mConnectedAccount,
                    AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true, null, null).getResult().getString(AccountManager.KEY_AUTHTOKEN);
        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (AuthenticatorException e)
        {
            e.printStackTrace();
        }
    }

    private void getAuthenTokenAndAccount()
    {
        Log.i(TAG, "Get auth token");
        Bundle bundle = this.getArguments();
        auth_token = bundle.getString(Constant.AUTHEN_TOKEN.getValue());
        mConnectedAccount = bundle.getParcelable(Constant.ACCOUNT_CONNECTED.getValue());
    }
    @Click(R.id.btNavigate)
    protected void btNavigateClick()
    {
        showMenu();
    }
    @ItemClick(R.id.home_lvDetail)
    protected void itemClickListener(){
        doShowProgram();
    }

    private static void setUpDataToHomeListView()
    {
        arraylist = new ArrayList<HashMap<String, String>>();
        Cursor cur = SlidebarActivity.context.getContentResolver().query(Contract.CONTENT_URI, null, null, null, null);
        HomeModel temp = new HomeModel();
        if (cur != null)
        {
            while (cur.moveToNext())
            {
                HashMap<String, String> map = new HashMap<String, String>();
                temp = HomeModel.fromCursor(cur);
                long updated_at = dateTime.getTimeBefore(temp.getUpdated_at());
                temp.setUpdated_at(Long.toString(updated_at));
                map.put(HomeFragment.NAME, temp.getDisplay_name());
                map.put(HomeFragment.TITLE, temp.getTitle());
                map.put(HomeFragment.LIKE, String.valueOf(temp.getLikes()));
                map.put(HomeFragment.COMMENT, String.valueOf(temp.getComments()));
                map.put(HomeFragment.TIME, temp.getUpdated_at());
                map.put(HomeFragment.AVATAR, temp.getAvatar());
                arraylist.add(map);
            }
            cur.close();
        }
        adapter = new HomeFeeds(SlidebarActivity.context, cur, arraylist);
        home_lvDetail.setAdapter(adapter);
    }

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doShowProgram()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.slidebar_homeFragment, new ProgramFragment_(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }
}

