package io.github.tobyhs.timetalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Click callback to start {@link ScreenService}
     *
     * @param v the view that was clicked
     */
    public void startService(View v) {
        startService(new Intent(this, ScreenService.class));
    }

    /**
     * Click callback to stop {@link ScreenService}
     *
     * @param v the view that was clicked
     */
    public void stopService(View v) {
        stopService(new Intent(this, ScreenService.class));
    }
}
