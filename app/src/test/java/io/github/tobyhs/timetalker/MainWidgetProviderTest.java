package io.github.tobyhs.timetalker;

import android.app.Application;
import android.content.ComponentName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainWidgetProviderTest {
    private MainWidgetProvider widgetProvider = new MainWidgetProvider();
    private Application application = RuntimeEnvironment.application;
    private ShadowApplication shadowApplication = shadowOf(application);
    private ComponentName screenServiceComponent = new ComponentName(application, ScreenService.class);

    @Test
    public void onEnabled() {
        widgetProvider.onEnabled(application);
        ComponentName nextComponent = shadowApplication.getNextStartedService().getComponent();
        assertThat(nextComponent, is(screenServiceComponent));
    }

    @Test
    public void onDisabled() {
        widgetProvider.onDisabled(application);
        ComponentName nextComponent = shadowApplication.getNextStoppedService().getComponent();
        assertThat(nextComponent, is(screenServiceComponent));
    }
}
