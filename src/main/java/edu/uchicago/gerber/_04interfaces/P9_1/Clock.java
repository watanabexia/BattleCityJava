package edu.uchicago.gerber._04interfaces.P9_1;

import java.time.LocalTime;

public class Clock {
    public String getHours() {
        return LocalTime.now().toString().split(":")[0];
    }

    public String getMinutes() {
        return LocalTime.now().toString().split(":")[1];
    }

    public String getTime() {
        return getHours() + ":" + getMinutes();
    }
}
