package io.github.tobyhs.timetalker;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ScreenActionReceiverTest {
    private Application application = RuntimeEnvironment.application;
    private AudioManager audioManager;
    private ScreenActionReceiver receiver = new ScreenActionReceiver();

    @Before
    public void setup() {
        audioManager = (AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
    }

    @Test
    public void onReceive_withRingerOn_starts_DetectorService() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        receiver.onReceive(application, new Intent(Intent.ACTION_SCREEN_ON));

        Intent serviceIntent = shadowOf(application).getNextStartedService();
        ComponentName componentName = new ComponentName(application, DetectorService.class);
        assertThat(serviceIntent.getComponent(), is(componentName));
    }

    @Test
    public void onReceive_withRingerOff_doesNotStart_service() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        receiver.onReceive(application, new Intent(Intent.ACTION_SCREEN_ON));

        Intent serviceIntent = shadowOf(application).getNextStartedService();
        assertThat(serviceIntent, is(nullValue()));
    }
}
