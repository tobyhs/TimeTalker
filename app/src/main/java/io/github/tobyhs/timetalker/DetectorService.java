package io.github.tobyhs.timetalker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

/**
 * This is a service that detects the appropriate event to trigger the phone to speak the time.
 */
public class DetectorService extends Service implements ShakeDetector.Listener {
    public static final long LIFETIME_MILLIS = 10000;

    TimeFormatter timeFormatter = new TimeFormatter();
    TextToSpeech textToSpeech;
    ShakeDetector shakeDetector;
    AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    String ttsInitFailureMsg = getString(
                            R.string.ttsInitFailure, getString(R.string.app_name)
                    );

                    Toast.makeText(
                            DetectorService.this, ttsInitFailureMsg, Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        shakeDetector = new ShakeDetector(this);
        shakeDetector.start((SensorManager) getSystemService(Context.SENSOR_SERVICE));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        }, LIFETIME_MILLIS);
    }

    @Override
    public void onDestroy() {
        shakeDetector.stop();
        textToSpeech.shutdown();

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void hearShake() {
        final boolean shouldSpeak = audioManager.getMode() == AudioManager.MODE_NORMAL &&
                audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
        if (shouldSpeak) {
            textToSpeech.speak(timeFormatter.format(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
