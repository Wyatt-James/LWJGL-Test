package com.hippout.lwjgltest.util;

public class Vec4f {
    public float x, y, z, w;

    public Vec4f(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f(double x, double y, double z, double w)
    {
        this((float) x, (float) y, (float) z, (float) w);
    }

    public Vec4f(float all)
    {
        this.x = all;
        this.y = all;
        this.z = all;
        this.w = all;
    }

    public Vec4f(double all)
    {
        this((float) all);
    }

    public Vec4f(Vec3f v, float w)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    public Vec4f(Vec3f v, double w)
    {
        this(v, (float) w);
    }

    public Vec4f(Vec3f v)
    {
        this(v, 1.0f);
    }

    public static Vec4f wOne()
    {
        return new Vec4f(0f, 0f, 0f, 1f);
    }

    public static Vec4f mix(Vec4f v1, Vec4f v2, float factor)
    {
        return new Vec4f(
                MathUtil.lerp(v1.x, v2.x, factor),
                MathUtil.lerp(v1.y, v2.y, factor),
                MathUtil.lerp(v1.z, v2.z, factor),
                MathUtil.lerp(v1.w, v2.w, factor));
    }

    public Vec4f copy()
    {
        return new Vec4f(x, y, z, w);
    }
}
