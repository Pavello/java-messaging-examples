package io.github.pavello.messaging.jms;

import java.time.Duration;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TimeUtils {

    public static void waitForDuration(Duration duration) {
        try {
            Thread.sleep(duration.toMillis(), duration.toNanosPart());
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public static void doInfiniteLoop() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
