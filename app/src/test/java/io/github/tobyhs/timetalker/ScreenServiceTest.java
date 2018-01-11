package io.github.tobyhs.timetalker;

import android.content.BroadcastReceiver;
import android.content.Intent;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ScreenServiceTest {
    @Test
    public void registersReceiverForScreenOn() {
        Intent intent = new Intent(Intent.ACTION_SCREEN_ON);
        ShadowApplication shadowApp = ShadowApplication.getInstance();

        ServiceController serviceController = Robolectric.buildService(ScreenService.class);
        serviceController.create();
        List<BroadcastReceiver> receivers = shadowApp.getReceiversForIntent(intent);
        assertThat(receivers, hasItem(isA(ScreenActionReceiver.class)));

        serviceController.destroy();
        assertThat(shadowApp.hasReceiverForIntent(intent), is(false));
    }

    @Test
    public void onBind_returnsNull() {
        ScreenService service = new ScreenService();
        assertThat(service.onBind(new Intent()), is(nullValue()));
    }
}
