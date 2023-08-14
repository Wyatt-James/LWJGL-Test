package com.hippout.lwjgltest.util;

public final class Mtx3fUtil {
    private static final int DIMENSION = 3;
    private static final int AREA = DIMENSION * DIMENSION;

    public static Vec3f[] filledMtx(float value)
    {
        final Vec3f[] mtx = new Vec3f[DIMENSION];

        for (int i = 0; i < mtx.length; i++)
            mtx[i] = new Vec3f(value);

        return mtx;
    }

    public static Vec3f[] emptyMtx()
    {
        return filledMtx(0.0f);
    }

    public static Vec3f[] diagonalMtx(float val)
    {
        Vec3f[] mtx = emptyMtx();

        mtx[0].x = val;
        mtx[1].y = val;
        mtx[2].z = val;

        return mtx;
    }

    public static Vec3f[] diagonalMtx(double val)
    {
        return diagonalMtx((float) val);
    }

    public static Vec3f[] identityMtx()
    {
        return diagonalMtx(1.0f);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public static float[] toGlMatrix(Vec3f[] mtx)
    {
        if (mtx.length != DIMENSION)
            throw new IllegalArgumentException(String.format("Not a %1$dx%1$d float mtx.", DIMENSION));

        final float[] glMatrix = new float[AREA];

        for (int i = 0; i < mtx.length; i++) {
            glMatrix[4 * i + 0] = mtx[i].x;
            glMatrix[4 * i + 1] = mtx[i].y;
            glMatrix[4 * i + 2] = mtx[i].z;
        }

        return glMatrix;
    }
}
