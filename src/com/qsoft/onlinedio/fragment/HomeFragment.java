package com.qsoft.onlinedio.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.util.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/14/13
 */

public class HomeFragment extends Fragment
{
    HomeFeeds adapter;
    ArrayList<HashMap<String, String>> arraylist;
    public static final String COMMENT = "comment";
    public static final String LIKE = "like";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String TIME = "time";
    public static final String AVATAR = "avatar";
    DateTime dateTime = new DateTime();
    private String auth_token;
    private String TAG = this.getClass().getSimpleName();
    private Account mConnectedAccount;

    private Button btNavigate;
    private ListView home_lvDetail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        getAuthenTokenAndAccount();

        performSyncData();
//        ContentResolver.setIsSyncable(mConnectedAccount, authority, 1);
//        ContentResolver.setSyncAutomatically(mConnectedAccount,authority, true);

//        try
//        {
//            listHomeFeed =  parseComServerAccessor.getShows(auth_token);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        View view = inflater.inflate(R.layout.home_layout, null);
        setUpUI(view);
        setUpDataToHomeListView();
        setUpListenerController();
        return view;
    }

    private void performSyncData()
    {
        Log.i(TAG, "Perform sync");
        String authority = Contract.AUTHORITY;
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mConnectedAccount, authority, bundle);
    }


//    private ArrayList<HomeModel> getDataFromLocal()
//    {
//        Cursor cur = getActivity().getContentResolver().query(Contract.CONTENT_URI, null, null, null, null);
//        HashMap<String, String> map = new HashMap<String, String>();
////        ArrayList<HomeModel> feedList = new ArrayList<HomeModel>();
//        HomeModel temp = new HomeModel();
//        if (cur != null) {
//            while (cur.moveToNext()){
//                temp = HomeModel.fromCursor(cur);
//                long updated_at = dateTime.getTimeBefore(temp.updated_at);
//                temp.updated_at = Long.toString(updated_at);
//                map.put(HomeFragment.NAME, temp.username);
//                map.put(HomeFragment.TITLE, temp.title);
//                map.put(HomeFragment.LIKE, String.valueOf(temp.likes));
//                map.put(HomeFragment.COMMENT, String.valueOf(temp.comments));
//                map.put(HomeFragment.TITLE, temp.updated_at);
//                map.put(HomeFragment.AVATAR, temp.avatar);
//                arraylist.add(map);
//            }
//            cur.close();
//        }
//        return arraylist;
//    }

    private void getAuthenTokenAndAccount()
    {
        Bundle bundle = this.getArguments();
        auth_token = bundle.getString(FirstLaunchActivity.AUTHEN_TOKEN);
        mConnectedAccount = bundle.getParcelable(FirstLaunchActivity.ACCOUNT_CONNECTED);
    }

    private void setUpListenerController()
    {
        btNavigate.setOnClickListener(onClickListener);
        home_lvDetail.setOnItemClickListener(onItemClickListener);
    }

    private void setUpDataToHomeListView()
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                // Create an array
                arraylist = new ArrayList<HashMap<String, String>>();
                Cursor cur = getActivity().getContentResolver().query(Contract.CONTENT_URI, null, null, null, null);
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
                return null;
            }

            @Override
            protected void onPostExecute(Void args)
            {

                adapter = new HomeFeeds(getActivity(), arraylist);
                home_lvDetail.setAdapter(adapter);
            }
        }.execute();
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

