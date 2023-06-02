package com.hippout.lwjgltest;

import com.hippout.lwjgltest.io.*;

import javax.annotation.*;
import java.io.*;

public abstract class GLTest {
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
}
