package com.qsoft.onlinedio.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class HomeFeedSyncService extends Service
{
    private static final Object sSyncAdapterLock = new Object();
    private static HomeFeedSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new HomeFeedSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
