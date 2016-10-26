package io.github.tobyhs.timetalker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This is a service that detects the appropriate event to trigger the phone to speak the time.
 */
public class DetectorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
