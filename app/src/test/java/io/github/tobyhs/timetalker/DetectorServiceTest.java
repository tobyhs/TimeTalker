package io.github.tobyhs.timetalker;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowTextToSpeech;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetectorServiceTest {
    @Test
    public void onBind_ReturnsNull() {
        DetectorService service = new DetectorService();
        assertThat(service.onBind(new Intent()), is(nullValue()));
    }

    @Test
    public void ttsInitError_showsToast() {
        TextToSpeech.OnInitListener listener = setupAndGetTtsOnInitListener();
        listener.onInit(TextToSpeech.ERROR);
        String latestToastText = ShadowToast.getTextOfLatestToast();
        assertThat(latestToastText, is("Time Talker: Failed to initialize TextToSpeech"));
    }

    @Test
    public void ttsInitSuccess_doesntShowToast() {
        TextToSpeech.OnInitListener listener = setupAndGetTtsOnInitListener();
        listener.onInit(TextToSpeech.SUCCESS);
        assertThat(ShadowToast.getLatestToast(), is(nullValue()));
    }

    private TextToSpeech.OnInitListener setupAndGetTtsOnInitListener() {
        DetectorService service = Robolectric.setupService(DetectorService.class);
        ShadowToast.reset();
        return shadowOf(service.textToSpeech).getOnInitListener();
    }

    public void hearShake_SpeaksTime() {
        ServiceController<DetectorService> controller = Robolectric.buildService(DetectorService.class);
        controller.create();
        DetectorService service = controller.get();

        TimeFormatter timeFormatter = mock(TimeFormatter.class);
        String formattedTime = "9 45 AM";
        when(timeFormatter.format()).thenReturn(formattedTime);
        service.timeFormatter = timeFormatter;

        service.hearShake();

        ShadowTextToSpeech shadowTts = shadowOf(service.textToSpeech);
        String spokenText = shadowTts.getLastSpokenText();
        assertThat(spokenText, is(formattedTime));

        controller.destroy();
        assertThat(shadowTts.isShutdown(), is(true));
    }

    @Test
    public void hearShake_WithRingerOff_DoesNotSpeak() {
        DetectorService service = Robolectric.setupService(DetectorService.class);
        AudioManager audioManager = (AudioManager) service.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        service.hearShake();

        ShadowTextToSpeech ttsShadow = shadowOf(service.textToSpeech);
        assertThat(ttsShadow.getLastSpokenText(), is(nullValue()));
    }
}
