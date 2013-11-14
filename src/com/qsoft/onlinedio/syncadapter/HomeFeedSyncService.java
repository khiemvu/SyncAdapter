package com.qsoft.onlinedio.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EService;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
@EService
public class HomeFeedSyncService extends Service
{
    public static final Object sSyncAdapterLock = new Object();
    @Bean
    public static HomeFeedSyncAdapter sSyncAdapter;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
//                sSyncAdapter = new HomeFeedSyncAdapter(getApplicationContext(),true);
                sSyncAdapter = new HomeFeedSyncAdapter(getApplicationContext());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
