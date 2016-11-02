package io.github.tobyhs.timetalker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

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
    Handler deathHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        shakeDetector = new ShakeDetector(this);
        shakeDetector.start((SensorManager) getSystemService(Context.SENSOR_SERVICE));

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        deathHandler = new Handler();
        deathHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        }, LIFETIME_MILLIS);
    }

    @Override
    public void onDestroy() {
        shakeDetector.stop();

        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void hearShake() {
        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            initTextToSpeech();
            textToSpeech.speak(timeFormatter.format(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private synchronized void initTextToSpeech() {
        if (textToSpeech == null) {
            Context context = getApplicationContext();
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    // TODO: handle error status
                }
            });
        }
    }
}
