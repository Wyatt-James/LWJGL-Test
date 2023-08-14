package com.hippout.lwjgltest.util;

public class Vec3f {
    public float x, y, z;

    public Vec3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(double x, double y, double z)
    {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public Vec3f(float all)
    {
        this.x = all;
        this.y = all;
        this.z = all;
    }

    public Vec3f(double all)
    {
        this((float) all);
    }

    public static Vec3f mix(Vec3f v1, Vec3f v2, float factor)
    {
        return new Vec3f(
                MathUtil.lerp(v1.x, v2.x, factor),
                MathUtil.lerp(v1.y, v2.y, factor),
                MathUtil.lerp(v1.z, v2.z, factor));
    }

    public Vec3f normalized()
    {
        final float len = length();

        return new Vec3f(x / len, y / len, z / len);
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}
