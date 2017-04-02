package io.github.tobyhs.timetalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Broadcast receiver that is triggered when the screen turns on and starts {@link DetectorService}
 */
public class ScreenActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            context.startService(new Intent(context, DetectorService.class));
        }
    }
}
