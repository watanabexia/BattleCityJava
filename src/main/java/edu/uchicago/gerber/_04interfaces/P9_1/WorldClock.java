package edu.uchicago.gerber._04interfaces.P9_1;

import java.time.LocalTime;

public class WorldClock extends Clock {
    private final int offset;

    public WorldClock(int offset) {
        this.offset = offset;
    }

    public String getHours() {
        return String.valueOf(Integer.parseInt(LocalTime.now().toString().split(":")[0]) + offset);
    }
}
