package com.hippout.lwjgltest.tutorials.tut02;

import com.hippout.lwjgltest.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class VertexColors extends GLTest {

    public static final float[] vertexData = {
            0.0f, 0.5f, 0.0f, 1.0f,
            0.5f, -0.366f, 0.0f, 1.0f,
            -0.5f, -0.366f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
    };

    private int program;
    private int vertexBufferObject;

    private long window;

    public VertexColors()
    {
        super("tut02");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("VertexColors.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("VertexColors.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return Framework.createProgram(shaderList);
    }

    private int initializeVertexBuffer()
    {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    public void init(long window)
    {
        this.window = window;

        program = initializeProgram();
        vertexBufferObject = initializeVertexBuffer();

        glfwSetWindowSizeCallback(window, new WindowSizeCallback());

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 48L);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }
}
