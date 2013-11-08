package com.qsoft.onlinedio.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.qsoft.onlinedio.authenticate.AccountGeneral;
import com.qsoft.onlinedio.database.Contract;
import com.qsoft.onlinedio.database.DbHelper;
import com.qsoft.onlinedio.database.entity.HomeModel;
import com.qsoft.onlinedio.fragment.HomeFragment;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class HomeFeedSyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "HomeFeedSyncAdapter";
    public static final String DONE = "Done";

    private final AccountManager mAccountManager;
    private final ContentResolver mContentResolver;
    public static final String MESSAGE_KEY = "key";

    public HomeFeedSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
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
            HomeFeedParseToServer parseComService = new HomeFeedParseToServer();
            ArrayList<HomeModel> remoteData = parseComService.getShows(authToken);

            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            HashMap<Long, HomeModel> map = new HashMap<Long, HomeModel>();
            for (HomeModel homeModel : remoteData)
            {
                map.put(homeModel.getId(), homeModel);
            }

            Log.i(TAG, "Get data on local");
            Cursor cur = provider.query(Contract.CONTENT_URI, null, null, null, null);
            assert cur != null;
            while (cur.moveToNext())
            {
                HomeModel temp = HomeModel.fromCursor(cur);
                HomeModel checkUpdate = map.get(temp.getId());

                if (checkUpdate != null)
                {
                    //remove record map
                    map.remove(temp.getId());
                    //Check update able
                    Uri existedUri = Contract.CONTENT_URI.buildUpon()
                            .appendPath(Long.toString(temp.getId())).build();
                    if ((checkUpdate.getUpdated_at() != null) &&
                            checkUpdate.getUpdated_at().equals(temp.getUpdated_at())
                            || checkUpdate.getLikes() != temp.getLikes()
                            || checkUpdate.getComments() != temp.getComments())
                    {
                        Log.i(TAG, "Data start update");
                        batch.add(ContentProviderOperation.newUpdate(existedUri)
                                .withValue(DbHelper.HOMEFEED_COL_ID, checkUpdate.getId())
                                .withValue(DbHelper.HOMEFEED_COL_USER_ID, checkUpdate.getUser_id())
                                .withValue(DbHelper.HOMEFEED_COL_TITLE, checkUpdate.getTitle())
                                .withValue(DbHelper.HOMEFEED_COL_THUMBNAIL, checkUpdate.getThumbnail())
                                .withValue(DbHelper.HOMEFEED_COL_DESCRIPTION, checkUpdate.getDesccription())
                                .withValue(DbHelper.HOMEFEED_COL_SOUND_PATH, checkUpdate.getSound_path())
                                .withValue(DbHelper.HOMEFEED_COL_DURATION, checkUpdate.getDuration())
                                .withValue(DbHelper.HOMEFEED_COL_PLAYED, checkUpdate.isPlayed())
                                .withValue(DbHelper.HOMEFEED_COL_CREATED_AT, checkUpdate.getCreated_at())
                                .withValue(DbHelper.HOMEFEED_COL_UPDATED_AT, checkUpdate.getUpdated_at())
                                .withValue(DbHelper.HOMEFEED_COL_LIKES, checkUpdate.getLikes())
                                .withValue(DbHelper.HOMEFEED_COL_VIEWED, checkUpdate.getViewed())
                                .withValue(DbHelper.HOMEFEED_COL_COMMENTS, checkUpdate.getComments())
                                .withValue(DbHelper.HOMEFEED_COL_USERNAME, checkUpdate.getUsername())
                                .withValue(DbHelper.HOMEFEED_COL_DISPLAY_NAME, checkUpdate.getDisplay_name())
                                .withValue(DbHelper.HOMEFEED_COL_AVATAR, checkUpdate.getAvatar()).build());

                        syncResult.stats.numUpdates++;
                    }

                }
                // record not exist. Need remove it from db
                else
                {
                    Uri deleteUri = Contract.CONTENT_URI.buildUpon().appendPath(Long.toString(temp.getId())).build();
                    Log.i(TAG, "Start delete" + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            cur.close();
            // add new record
            for (HomeModel homeModel : map.values())
            {
                Log.i(TAG, "Start insert: record_id = " + homeModel.getId());
                batch.add(ContentProviderOperation
                        .newInsert(Contract.CONTENT_URI)
                        .withValue(DbHelper.HOMEFEED_COL_ID, homeModel.getId())
                        .withValue(DbHelper.HOMEFEED_COL_USER_ID, homeModel.getUser_id())
                        .withValue(DbHelper.HOMEFEED_COL_TITLE, homeModel.getTitle())
                        .withValue(DbHelper.HOMEFEED_COL_THUMBNAIL, homeModel.getThumbnail())
                        .withValue(DbHelper.HOMEFEED_COL_DESCRIPTION, homeModel.getDesccription())
                        .withValue(DbHelper.HOMEFEED_COL_SOUND_PATH, homeModel.getSound_path())
                        .withValue(DbHelper.HOMEFEED_COL_DURATION, homeModel.getDuration())
                        .withValue(DbHelper.HOMEFEED_COL_PLAYED, homeModel.isPlayed())
                        .withValue(DbHelper.HOMEFEED_COL_CREATED_AT, homeModel.getCreated_at())
                        .withValue(DbHelper.HOMEFEED_COL_UPDATED_AT, homeModel.getUpdated_at())
                        .withValue(DbHelper.HOMEFEED_COL_LIKES, homeModel.getLikes())
                        .withValue(DbHelper.HOMEFEED_COL_VIEWED, homeModel.getViewed())
                        .withValue(DbHelper.HOMEFEED_COL_COMMENTS, homeModel.getComments())
                        .withValue(DbHelper.HOMEFEED_COL_USERNAME, homeModel.getUsername())
                        .withValue(DbHelper.HOMEFEED_COL_DISPLAY_NAME, homeModel.getDisplay_name())
                        .withValue(DbHelper.HOMEFEED_COL_AVATAR, homeModel.getAvatar()).build());
                syncResult.stats.numInserts++;
            }
            mContentResolver.applyBatch(Contract.AUTHORITY, batch);
            mContentResolver.notifyChange(Contract.CONTENT_URI, // URI
                    null, // No local observer
                    false); // IMPORTANT: Do not sync to network

            Log.d(TAG, "Finished sync data");
            Message msgObj = HomeFragment.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString(MESSAGE_KEY, DONE);
            msgObj.setData(b);
            HomeFragment.handler.sendMessage(msgObj);
        }

        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (OperationApplicationException e)
        {
            e.printStackTrace();
        }
        catch (OperationCanceledException e)
        {
            e.printStackTrace();
        }
        catch (AuthenticatorException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
