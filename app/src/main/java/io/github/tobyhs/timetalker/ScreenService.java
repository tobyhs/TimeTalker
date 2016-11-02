package io.github.tobyhs.timetalker;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Service that registers {@link ScreenActionReceiver}
 */
public class ScreenService extends Service {
    private static boolean sRunning = false;

    ScreenActionReceiver screenActionReceiver = new ScreenActionReceiver();

    /**
     * Determines whether this service is running.
     *
     * @return whether this service is running
     */
    public static boolean isRunning() {
        return sRunning;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sRunning = true;

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenActionReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenActionReceiver);

        sRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
