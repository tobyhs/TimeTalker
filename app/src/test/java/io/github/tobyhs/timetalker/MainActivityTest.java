package io.github.tobyhs.timetalker;

import android.content.ComponentName;
import android.widget.Switch;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Test
    public void toggleEnabledWhenServiceRunning() {
        ServiceController<ScreenService> controller = Robolectric.buildService(ScreenService.class);
        controller.create();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Switch enabledSwitch = activity.findViewById(R.id.enabledSwitch);
        assertThat(enabledSwitch.isChecked(), is(true));

        controller.destroy();
    }

    @Test
    public void togglingStartsAndStopsService() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        ComponentName serviceComponent = new ComponentName(activity, ScreenService.class);
        Switch enabledSwitch = activity.findViewById(R.id.enabledSwitch);

        enabledSwitch.toggle();
        assertThat(shadowActivity.getNextStartedService().getComponent(), is(serviceComponent));

        enabledSwitch.toggle();
        assertThat(shadowActivity.getNextStoppedService().getComponent(), is(serviceComponent));
    }
}
