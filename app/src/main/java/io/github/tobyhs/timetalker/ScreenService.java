package io.github.tobyhs.timetalker;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Service that registers {@link ScreenActionReceiver}
 */
public class ScreenService extends Service {
    ScreenActionReceiver screenActionReceiver = new ScreenActionReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenActionReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenActionReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
