package io.github.tobyhs.timetalker;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowTextToSpeech;
import org.robolectric.util.ServiceController;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class DetectorServiceTest {
    @Test
    public void onBind_ReturnsNull() {
        DetectorService service = new DetectorService();
        assertThat(service.onBind(new Intent()), is(nullValue()));
    }

    @Test
    public void hearShake_SpeaksTime() {
        ServiceController<DetectorService> serviceController = Robolectric.buildService(DetectorService.class);
        serviceController.create();
        DetectorService service = serviceController.get();

        TimeFormatter timeFormatter = mock(TimeFormatter.class);
        String formattedTime = "9 45 AM";
        when(timeFormatter.format()).thenReturn(formattedTime);
        service.timeFormatter = timeFormatter;

        service.hearShake();

        ShadowTextToSpeech ttsShadow = shadowOf(service.textToSpeech);
        String spokenText = ttsShadow.getLastSpokenText();
        assertThat(spokenText, is(formattedTime));

        serviceController.destroy();
        assertThat(ttsShadow.isShutdown(), is(true));
    }
}
