package io.github.tobyhs.timetalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver that is triggered when the screen turns on and starts {@link DetectorService}
 */
public class ScreenActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, DetectorService.class));
    }
}
