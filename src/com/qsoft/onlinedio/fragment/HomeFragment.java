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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.activity.FirstLaunchActivity;
import com.qsoft.onlinedio.activity.SlidebarActivity;
import com.qsoft.onlinedio.adapter.HomeFeeds;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.syncadapter.HomeFeedSyncAdapter;
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
    private AccountManager mAccountManager;
    private Button btNavigate;
    private static ListView home_lvDetail;
    private static ProgressDialog mProgressDialog;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        mAccountManager = AccountManager.get(getActivity());
        getAuthenTokenAndAccount();

        View view = inflater.inflate(R.layout.home_layout, null);
        setUpUI(view);

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
        setUpListenerController();
        return view;
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
        auth_token = bundle.getString(FirstLaunchActivity.AUTHEN_TOKEN);
        mConnectedAccount = bundle.getParcelable(FirstLaunchActivity.ACCOUNT_CONNECTED);
    }

    private void setUpListenerController()
    {
        btNavigate.setOnClickListener(onClickListener);
        home_lvDetail.setOnItemClickListener(onItemClickListener);
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

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            doShowProgram();
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.btNavigate:
                    showMenu();
                    break;
            }
        }
    };

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doShowProgram()
    {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.slidebar_homeFragment, new ProgramFragment(), "ProgramFragment");
        ft.addToBackStack("ProgramFragment");
        ft.commit();
    }

    private void setUpUI(View view)
    {
        btNavigate = (Button) view.findViewById(R.id.btNavigate);
        home_lvDetail = (ListView) view.findViewById(R.id.home_lvDetail);
    }
}

