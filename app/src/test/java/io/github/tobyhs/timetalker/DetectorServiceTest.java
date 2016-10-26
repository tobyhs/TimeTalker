package io.github.tobyhs.timetalker;

import android.content.Intent;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DetectorServiceTest {
    @Test
    public void onBind_ReturnsNull() {
        DetectorService service = new DetectorService();
        assertThat(service.onBind(new Intent()), is(nullValue()));
    }
}
