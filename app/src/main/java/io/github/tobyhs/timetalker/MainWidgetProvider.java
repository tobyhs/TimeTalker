package io.github.tobyhs.timetalker;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * A home screen widget that starts {@link ScreenService} when enabled.
 */
public class MainWidgetProvider extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context, ScreenService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context, ScreenService.class));
    }
}
