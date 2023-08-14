package com.hippout.lwjgltest.util;

public final class MathUtil {
    public static float lerp(float start, float end, float lerpVal)
    {
        return start + ((end - start) * lerpVal);
    }

    public static float calcLerpFactor(float fElapsedTime, float fLoopDuration)
    {
        float lerpVal = (fElapsedTime % fLoopDuration) / fLoopDuration;

        if (lerpVal > 0.5f)
            lerpVal = 1.0f - lerpVal;

        return lerpVal * 2.0f;
    }
}
