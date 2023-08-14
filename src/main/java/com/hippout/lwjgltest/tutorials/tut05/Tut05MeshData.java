package com.hippout.lwjgltest.tutorials.tut05;

import java.util.*;

public class Tut05MeshData {

    public static final int numberOfVertices = 36;

    private static final float RIGHT_EXTENT = 0.8f;
    private static final float LEFT_EXTENT = -RIGHT_EXTENT;
    private static final float TOP_EXTENT = 0.20f;
    private static final float MIDDLE_EXTENT = 0.0f;
    private static final float BOTTOM_EXTENT = -TOP_EXTENT;
    private static final float FRONT_EXTENT = -1.25f;
    private static final float REAR_EXTENT = -1.75f;

    private static final float[] vertexData = {

            //Object 1 positions
            LEFT_EXTENT, TOP_EXTENT, REAR_EXTENT,
            LEFT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            RIGHT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            RIGHT_EXTENT, TOP_EXTENT, REAR_EXTENT,

            LEFT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,
            LEFT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            RIGHT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            RIGHT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,

            LEFT_EXTENT, TOP_EXTENT, REAR_EXTENT,
            LEFT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            LEFT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,

            RIGHT_EXTENT, TOP_EXTENT, REAR_EXTENT,
            RIGHT_EXTENT, MIDDLE_EXTENT, FRONT_EXTENT,
            RIGHT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,

            LEFT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,
            LEFT_EXTENT, TOP_EXTENT, REAR_EXTENT,
            RIGHT_EXTENT, TOP_EXTENT, REAR_EXTENT,
            RIGHT_EXTENT, BOTTOM_EXTENT, REAR_EXTENT,

            //Object 2 positions
            TOP_EXTENT, RIGHT_EXTENT, REAR_EXTENT,
            MIDDLE_EXTENT, RIGHT_EXTENT, FRONT_EXTENT,
            MIDDLE_EXTENT, LEFT_EXTENT, FRONT_EXTENT,
            TOP_EXTENT, LEFT_EXTENT, REAR_EXTENT,

            BOTTOM_EXTENT, RIGHT_EXTENT, REAR_EXTENT,
            MIDDLE_EXTENT, RIGHT_EXTENT, FRONT_EXTENT,
            MIDDLE_EXTENT, LEFT_EXTENT, FRONT_EXTENT,
            BOTTOM_EXTENT, LEFT_EXTENT, REAR_EXTENT,

            TOP_EXTENT, RIGHT_EXTENT, REAR_EXTENT,
            MIDDLE_EXTENT, RIGHT_EXTENT, FRONT_EXTENT,
            BOTTOM_EXTENT, RIGHT_EXTENT, REAR_EXTENT,

            TOP_EXTENT, LEFT_EXTENT, REAR_EXTENT,
            MIDDLE_EXTENT, LEFT_EXTENT, FRONT_EXTENT,
            BOTTOM_EXTENT, LEFT_EXTENT, REAR_EXTENT,

            BOTTOM_EXTENT, RIGHT_EXTENT, REAR_EXTENT,
            TOP_EXTENT, RIGHT_EXTENT, REAR_EXTENT,
            TOP_EXTENT, LEFT_EXTENT, REAR_EXTENT,
            BOTTOM_EXTENT, LEFT_EXTENT, REAR_EXTENT,

            //Object 1 colors
            0.75f, 0.75f, 1.0f, 1.0f, // Green
            0.75f, 0.75f, 1.0f, 1.0f, // Green
            0.75f, 0.75f, 1.0f, 1.0f, // Green
            0.75f, 0.75f, 1.0f, 1.0f, // Green

            0.0f, 0.5f, 0.0f, 1.0f, // Blue
            0.0f, 0.5f, 0.0f, 1.0f, // Blue
            0.0f, 0.5f, 0.0f, 1.0f, // Blue
            0.0f, 0.5f, 0.0f, 1.0f, // Blue

            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 0.0f, 0.0f, 1.0f, // Red

            0.8f, 0.8f, 0.8f, 1.0f, // Gray
            0.8f, 0.8f, 0.8f, 1.0f, // Gray
            0.8f, 0.8f, 0.8f, 1.0f, // Gray

            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown

            //Object 2 colors
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 0.0f, 0.0f, 1.0f, // Red

            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown
            0.5f, 0.5f, 0.0f, 1.0f, // Brown

            0.0f, 0.5f, 0.0f, 1.0f, // Blue
            0.0f, 0.5f, 0.0f, 1.0f, // Blue
            0.0f, 0.5f, 0.0f, 1.0f, // Blue

            0.75f, 0.75f, 1.0f, 1.0f, // Green
            0.75f, 0.75f, 1.0f, 1.0f, // Green
            0.75f, 0.75f, 1.0f, 1.0f, // Green

            0.8f, 0.8f, 0.8f, 1.0f, // Gray
            0.8f, 0.8f, 0.8f, 1.0f, // Gray
            0.8f, 0.8f, 0.8f, 1.0f, // Gray
            0.8f, 0.8f, 0.8f, 1.0f  // Gray
    };

    private static final short[] indexData = {
            0, 2, 1,
            3, 2, 0,

            4, 5, 6,
            6, 7, 4,

            8, 9, 10,
            11, 13, 12,

            14, 16, 15,
            17, 16, 14,
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
