package io.github.tobyhs.timetalker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code TimeFormatter} formats dates so that a text-to-speech engine can say the time.
 */
class TimeFormatter {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("h mm a");

    /**
     * @param date the date representing the time to format
     * @return the given date formatted
     */
    String format(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * @return the current date formatted
     */
    String format() {
        return format(now());
    }

    /**
     * @return the current date
     */
    Date now() {
        return new Date();
    }
}
