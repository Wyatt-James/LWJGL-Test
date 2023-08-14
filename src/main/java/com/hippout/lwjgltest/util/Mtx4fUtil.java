package com.hippout.lwjgltest.util;

public final class Mtx4fUtil {
    private static final int DIMENSION = 4;
    private static final int AREA = DIMENSION * DIMENSION;

    public static Vec4f[] filledMtx(float value)
    {
        final Vec4f[] mtx = new Vec4f[DIMENSION];

        for (int i = 0; i < mtx.length; i++)
            mtx[i] = new Vec4f(value);

        return mtx;
    }

    public static Vec4f[] emptyMtx()
    {
        return filledMtx(0.0f);
    }

    public static Vec4f[] diagonalMtx(float val)
    {
        Vec4f[] mtx = emptyMtx();

        mtx[0].x = val;
        mtx[1].y = val;
        mtx[2].z = val;
        mtx[3].w = val;

        return mtx;
    }

    public static Vec4f[] diagonalMtx(double val)
    {
        return diagonalMtx((float) val);
    }

    public static Vec4f[] identityMtx()
    {
        return diagonalMtx(1.0f);
    }

    public static Vec4f[] fromMtx3f(Vec3f[] mtx3f)
    {
        if (mtx3f.length != 3)
            throw new IllegalArgumentException("Not a 3x3 float mtx.");

        final Vec4f[] mtx = new Vec4f[DIMENSION];

        for (int i = 0; i < mtx.length - 1; i++)
            mtx[i] = new Vec4f(mtx3f[i], 0);

        mtx[mtx.length - 1] = new Vec4f(0.0f, 0.0f, 0.0f, 1.0f);

        return mtx;
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public static float[] toGlMatrix(Vec4f[] mtx)
    {
        if (mtx.length != DIMENSION)
            throw new IllegalArgumentException(String.format("Not a %1$dx%1$d float mtx.", DIMENSION));

        final float[] glMatrix = new float[16];

        for (int i = 0; i < mtx.length; i++) {
            glMatrix[4 * i + 0] = mtx[i].x;
            glMatrix[4 * i + 1] = mtx[i].y;
            glMatrix[4 * i + 2] = mtx[i].z;
            glMatrix[4 * i + 3] = mtx[i].w;
        }

        return glMatrix;
    }
}
