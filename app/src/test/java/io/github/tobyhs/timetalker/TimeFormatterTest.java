package io.github.tobyhs.timetalker;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TimeFormatterTest {
    private TimeFormatter formatter = new TimeFormatter();

    @Test
    public void format_withDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 30, 21, 5);
        assertThat(formatter.format(calendar.getTime()), is("9 05 PM"));
    }

    @Test
    public void format_noArgs() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 30, 9, 59);
        TimeFormatter spy = spy(formatter);
        when(spy.now()).thenReturn(calendar.getTime());
        assertThat(spy.format(), is("9 59 AM"));
    }

    @Test
    public void now() {
        double result = formatter.now().getTime();
        double now = new Date().getTime();
        assertThat(result, closeTo(now, 2000));
    }
}
