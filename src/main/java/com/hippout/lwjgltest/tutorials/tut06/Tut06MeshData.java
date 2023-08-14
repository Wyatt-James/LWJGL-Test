package com.hippout.lwjgltest.tutorials.tut06;

import java.util.*;

public class Tut06MeshData {

    public static final int numberOfVertices = 8;

    private static final float[] vertexData = {
            +1.0f, +1.0f, +1.0f,
            -1.0f, -1.0f, +1.0f,
            -1.0f, +1.0f, -1.0f,
            +1.0f, -1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f,
            +1.0f, +1.0f, -1.0f,
            +1.0f, -1.0f, +1.0f,
            -1.0f, +1.0f, +1.0f,

            0.0f, 1.0f, 0.0f, 1.0f, // Green,
            0.0f, 0.0f, 1.0f, 1.0f, // Blue,
            1.0f, 0.0f, 0.0f, 1.0f, // Red,
            0.5f, 0.5f, 0.0f, 1.0f, // Brown,

            0.0f, 1.0f, 0.0f, 1.0f, // Green,
            0.0f, 0.0f, 1.0f, 1.0f, // Blue,
            1.0f, 0.0f, 0.0f, 1.0f, // Red,
            0.5f, 0.5f, 0.0f, 1.0f, // Brown
    };

    private static final short[] indexData = {
            0, 1, 2,
            1, 0, 3,
            2, 3, 0,
            3, 2, 1,

            5, 4, 6,
            4, 5, 7,
            7, 6, 4,
            6, 7, 5,
    };

    public static float[] getVertexData()
    {
        return Arrays.copyOf(vertexData, vertexData.length);
    }

    public static short[] getIndexData()
    {
        return Arrays.copyOf(indexData, indexData.length);
    }
}
