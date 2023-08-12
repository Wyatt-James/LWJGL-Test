package com.hippout.lwjgltest.tutorials.tut03;

import com.hippout.lwjgltest.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class FragChangeColor extends GLTest {

    public static final float[] vertexPositions = {
            0.25f, 0.25f, 0.0f, 1.0f,
            0.25f, -0.25f, 0.0f, 1.0f,
            -0.25f, -0.25f, 0.0f, 1.0f,
    };

    private int program;
    private int positionBufferObject;

    private int elapsedTimeUniform;

    private long window;

    public FragChangeColor()
    {
        super("tut03");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("calcOffset.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("calcColor.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int prog = Framework.createProgram(shaderList);

        elapsedTimeUniform = glGetUniformLocation(prog, "time");

        // Set the loop durations only once
        int vertLoopDurationOffset = glGetUniformLocation(prog, "loopDuration");
        int fragLoopDurationOffset = glGetUniformLocation(prog, "fragLoopDuration");

        glUseProgram(prog);
        glUniform1f(vertLoopDurationOffset, 5.0f);
        glUniform1f(fragLoopDurationOffset, 10.0f);
        glUseProgram(0);
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

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glUniform1f(elapsedTimeUniform, (float) glfwGetTime());

        glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }

    @Override
    protected void onWindowSizeChange(long window, int width, int height)
    {
        glViewport(0, 0, width, height);
    }

    @Override
    protected final void onKeyboard(long window, int key)
    {
        if (key == GLFW_KEY_ESCAPE) {
            glfwSetWindowShouldClose(window, true);
        }
    }
}
