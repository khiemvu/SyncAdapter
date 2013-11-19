package com.qsoft.onlinedio.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.database.dto.HomeFeedDTO;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.database.entity.HomeModelContract;
import com.qsoft.onlinedio.restfullservice.RestClientHomeFeed;
import com.qsoft.onlinedio.restfullservice.container.HomeFeedContainer;
import com.qsoft.onlinedio.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
@EBean
public class HomeFeedSyncAdapter extends AbstractThreadedSyncAdapter
{
    @RestService
    RestClientHomeFeed restClient;

    private static final String TAG = "HomeFeedSyncAdapter";
    public static final String DONE = "Done";

    private final AccountManager mAccountManager;
    private final ContentResolver mContentResolver;
    public static final String MESSAGE_KEY = "key";

    public HomeFeedSyncAdapter(Context context)
    {
        super(context, true, true);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult)
    {
        try
        {
            Log.i(TAG, "Perform sync data");

            //get auth token
            String authToken = mAccountManager.peekAuthToken(account,
                    AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);

            Log.i(TAG, "Get data from server");
//            HomeFeedParseToServer parseComService = new HomeFeedParseToServer();
//            ArrayList<HomeModel> remoteData = parseComService.getShows(authToken);
            HomeFeedContainer homeFeedContainer = restClient.getHomeFeeds(authToken);
            ArrayList<HomeFeedDTO> remoteData = homeFeedContainer.getData();

            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            HashMap<Long, HomeFeedDTO> map = new HashMap<Long, HomeFeedDTO>();
            for (HomeFeedDTO homeModel : remoteData)
            {
                map.put(homeModel.getId(), homeModel);
            }

            Log.i(TAG, "Get data on local");
            Cursor cur = provider.query(HomeModelContract.CONTENT_URI, null, null, null, null);
            assert cur != null;
            while (cur.moveToNext())
            {
                HomeModel temp = HomeModel.fromCursor(cur);
                HomeFeedDTO checkUpdate = map.get(temp.getId());

                if (checkUpdate != null)
                {
                    //remove record map
                    map.remove(temp.getId());
                    //Check update able
                    Uri existedUri = HomeModelContract.CONTENT_URI.buildUpon()
                            .appendPath(Long.toString(temp.getId())).build();
                    if ((checkUpdate.getUpdated_at() != null) &&
                            checkUpdate.getUpdated_at().equals(temp.getUpdated_at())
                            || checkUpdate.getLikes() != temp.getLikes()
                            || checkUpdate.getComments() != temp.getComments())
                    {
                        Log.i(TAG, "Data start update");
                        batch.add(ContentProviderOperation.newUpdate(existedUri)
                                .withValue(HomeModelContract.ID, checkUpdate.getId())
                                .withValue(HomeModelContract.USER_ID, checkUpdate.getUser_id())
                                .withValue(HomeModelContract.TITLE, checkUpdate.getTitle())
                                .withValue(HomeModelContract.THUMBNAIL, checkUpdate.getThumbnail())
                                .withValue(HomeModelContract.DESCRIPTION, checkUpdate.getDescription())
                                .withValue(HomeModelContract.SOUND_PATH, checkUpdate.getSound_path())
                                .withValue(HomeModelContract.DURATION, checkUpdate.getDuration())
                                .withValue(HomeModelContract.PLAYED, checkUpdate.getPlayed())
                                .withValue(HomeModelContract.CREATED_AT, checkUpdate.getCreated_at())
                                .withValue(HomeModelContract.UPDATED_AT, checkUpdate.getUpdated_at())
                                .withValue(HomeModelContract.LIKES, checkUpdate.getLikes())
                                .withValue(HomeModelContract.VIEWED, checkUpdate.getViewed())
                                .withValue(HomeModelContract.COMMENTS, checkUpdate.getComments())
                                .withValue(HomeModelContract.USERNAME, checkUpdate.getUsername())
                                .withValue(HomeModelContract.DISPLAY_NAME, checkUpdate.getDisplay_name())
                                .withValue(HomeModelContract.AVATAR, checkUpdate.getAvatar()).build());

                        syncResult.stats.numUpdates++;
                    }

                }
                // record not exist. Need remove it from db
                else
                {
                    Uri deleteUri = HomeModelContract.CONTENT_URI.buildUpon().appendPath(Long.toString(temp.getId())).build();
                    Log.i(TAG, "Start delete " + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            cur.close();
            // add new record
            for (HomeFeedDTO homeModel : map.values())
            {
                Log.i(TAG, "Start insert: record_id = " + homeModel.getId() + " " + HomeModelContract.CONTENT_URI);
                batch.add(ContentProviderOperation
                        .newInsert(HomeModelContract.CONTENT_URI)
                        .withValue(HomeModelContract.ID, homeModel.getId())
                        .withValue(HomeModelContract.USER_ID, homeModel.getUser_id())
                        .withValue(HomeModelContract.TITLE, homeModel.getTitle())
                        .withValue(HomeModelContract.THUMBNAIL, homeModel.getThumbnail())
                        .withValue(HomeModelContract.DESCRIPTION, homeModel.getDescription())
                        .withValue(HomeModelContract.SOUND_PATH, homeModel.getSound_path())
                        .withValue(HomeModelContract.DURATION, homeModel.getDuration())
                        .withValue(HomeModelContract.PLAYED, homeModel.getPlayed())
                        .withValue(HomeModelContract.CREATED_AT, homeModel.getCreated_at())
                        .withValue(HomeModelContract.UPDATED_AT, homeModel.getUpdated_at())
                        .withValue(HomeModelContract.LIKES, homeModel.getLikes())
                        .withValue(HomeModelContract.VIEWED, homeModel.getViewed())
                        .withValue(HomeModelContract.COMMENTS, homeModel.getComments())
                        .withValue(HomeModelContract.USERNAME, homeModel.getUsername())
                        .withValue(HomeModelContract.DISPLAY_NAME, homeModel.getDisplay_name())
                        .withValue(HomeModelContract.AVATAR, homeModel.getAvatar()).build());
                syncResult.stats.numInserts++;
            }
            mContentResolver.applyBatch(HomeModelContract.AUTHORITY, batch);
            mContentResolver.notifyChange(HomeModelContract.CONTENT_URI, // URI
                    null, // No local observer
                    false); // IMPORTANT: Do not sync to network

            Log.d(TAG, "Finished sync data");
            Message msgObj = HomeFragment.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString(MESSAGE_KEY, DONE);
            msgObj.setData(b);
            HomeFragment.handler.sendMessage(msgObj);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
