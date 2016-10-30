package io.github.tobyhs.timetalker;

import android.content.ComponentName;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {
    @Test
    public void clickingStartAndStopButtons() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity activityShadow = shadowOf(activity);
        ComponentName componentName = new ComponentName(activity, ScreenService.class);

        activity.findViewById(R.id.startButton).performClick();
        assertThat(activityShadow.getNextStartedService().getComponent(), is(componentName));

        activity.findViewById(R.id.stopButton).performClick();
        assertThat(activityShadow.getNextStoppedService().getComponent(), is(componentName));
    }
}
