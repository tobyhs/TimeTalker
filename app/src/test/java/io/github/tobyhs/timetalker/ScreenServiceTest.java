package io.github.tobyhs.timetalker;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ServiceController;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ScreenServiceTest {
    @Test
    public void isRunning() {
        ServiceController<ScreenService> controller = Robolectric.buildService(ScreenService.class);
        controller.create();
        assertThat(ScreenService.isRunning(), is(true));

        controller.destroy();
        assertThat(ScreenService.isRunning(), is(false));
    }

    @Test
    public void registersReceiverForScreenOn() {
        Intent intent = new Intent(Intent.ACTION_SCREEN_ON);
        ShadowApplication shadowApp = ShadowApplication.getInstance();

        ServiceController serviceController = Robolectric.buildService(ScreenService.class);
        serviceController.create();
        assertThat(shadowApp.hasReceiverForIntent(intent), is(true));
        serviceController.destroy();
        assertThat(shadowApp.hasReceiverForIntent(intent), is(false));
    }

    @Test
    public void onBind_returnsNull() {
        ScreenService service = new ScreenService();
        assertThat(service.onBind(new Intent()), is(nullValue()));
    }
}
