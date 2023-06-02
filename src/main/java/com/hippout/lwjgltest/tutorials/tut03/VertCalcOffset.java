package com.hippout.lwjgltest.tutorials.tut03;

import com.hippout.lwjgltest.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class VertCalcOffset extends GLTest {

    public static final float[] vertexPositions = {
            0.25f, 0.25f, 0.0f, 1.0f,
            0.25f, -0.25f, 0.0f, 1.0f,
            -0.25f, -0.25f, 0.0f, 1.0f,
    };

    private int program;
    private int positionBufferObject;

    private int offsetTime;

    private long window;

    public VertCalcOffset()
    {
        super("tut03");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("calcOffset.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("standard.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int prog = Framework.createProgram(shaderList);

        // Set the loop duration only once
        int offsetLoopDuration = glGetUniformLocation(prog, "loopDuration");
        glUseProgram(prog);
        glUniform1f(offsetLoopDuration, 5.0f);
        glUseProgram(0);

        offsetTime = glGetUniformLocation(prog, "time");
        return prog;
    }

    private int initializeVertexBuffer()
    {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexPositions, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    public void init(long window)
    {
        this.window = window;

        program = initializeProgram();
        positionBufferObject = initializeVertexBuffer();

        glfwSetWindowSizeCallback(window, new WindowSizeCallback());

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glUniform1f(offsetTime, (float) glfwGetTime());

        glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }
}
