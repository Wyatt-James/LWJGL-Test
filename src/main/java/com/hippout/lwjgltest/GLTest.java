package com.hippout.lwjgltest;

import com.hippout.lwjgltest.io.*;
import org.lwjgl.glfw.*;

import javax.annotation.*;
import java.io.*;

public abstract class GLTest extends GLFWWindowSizeCallback {
    protected ResourceLoader shaderLoader;

    public GLTest(@Nonnull String shaderPath)
    {
        try {
            shaderLoader = new LocalJarResourceLoader(Framework.shaderBasePath + "/" + shaderPath);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public abstract void init(long window);

    public abstract void display();

    public final void invoke(long window, int width, int height)
    {
        onWindowSizeChange(window, width, height);
    }

    protected abstract void onWindowSizeChange(long window, int width, int height);

    protected abstract void onKeyboard(long window, int key);
}
