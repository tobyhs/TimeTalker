package io.github.tobyhs.timetalker;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ScreenActionReceiverTest {
    @Test
    public void onReceive_starts_DetectorService() {
        Application application = RuntimeEnvironment.application;
        ScreenActionReceiver receiver = new ScreenActionReceiver();
        receiver.onReceive(application, new Intent(Intent.ACTION_SCREEN_ON));

        Intent serviceIntent = shadowOf(application).getNextStartedService();
        ComponentName componentName = new ComponentName(application, DetectorService.class);
        assertThat(serviceIntent.getComponent(), is(componentName));
    }
}
