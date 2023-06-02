package com.hippout.lwjgltest;

import org.lwjgl.glfw.*;

import static org.lwjgl.opengl.GL33.*;

public class WindowSizeCallback extends GLFWWindowSizeCallback {
    @Override
    public void invoke(long window, int width, int height)
    {
        glViewport(0, 0, width, height);
    }
}
