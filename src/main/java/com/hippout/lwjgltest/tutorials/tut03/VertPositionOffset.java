package com.hippout.lwjgltest.tutorials.tut03;

import com.hippout.lwjgltest.*;
import com.hippout.lwjgltest.util.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class VertPositionOffset extends GLTest {

    public static final float[] vertexPositions = {
            0.25f, 0.25f, 0.0f, 1.0f,
            0.25f, -0.25f, 0.0f, 1.0f,
            -0.25f, -0.25f, 0.0f, 1.0f,
    };

    private int program;
    private int positionBufferObject;
    private int offsetLocation;

    private long window;

    public VertPositionOffset()
    {
        super("tut03");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("positionOffset.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("standard.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int prog = Framework.createProgram(shaderList);
        offsetLocation = glGetUniformLocation(prog, "offset");
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

    private Pair<Float, Float> computePositionOffsets(double elapsedTime)
    {
        final double loopDuration = 5.0f;
        final double scale = (float) (Math.PI * 2.0 / loopDuration);

        double timeInLoop = elapsedTime % loopDuration;

        float xOffset = (float) Math.cos(timeInLoop * scale) * 0.5f;
        float yOffset = (float) Math.sin(timeInLoop * scale) * 0.5f;

        return new Pair<>(xOffset, yOffset);
    }

    public void display()
    {
        final Pair<Float, Float> offsets = computePositionOffsets(glfwGetTime());
        final float offsetX = offsets.e1, offsetY = offsets.e2;

        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glUniform2f(offsetLocation, offsetX, offsetY);

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
}
