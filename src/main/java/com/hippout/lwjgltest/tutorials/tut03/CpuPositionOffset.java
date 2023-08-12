package com.hippout.lwjgltest.tutorials.tut03;

import com.hippout.lwjgltest.*;
import com.hippout.lwjgltest.util.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class CpuPositionOffset extends GLTest {

    public static final float[] vertexPositions = {
            0.25f, 0.25f, 0.0f, 1.0f,
            0.25f, -0.25f, 0.0f, 1.0f,
            -0.25f, -0.25f, 0.0f, 1.0f,
    };

    private int program;
    private int positionBufferObject;

    private long window;

    public CpuPositionOffset()
    {
        super("tut03");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("standard.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("standard.frag")));
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
        glBufferData(GL_ARRAY_BUFFER, vertexPositions, GL_STREAM_DRAW);
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

    private void adjustVertexData(float xOffset, float yOffset)
    {
        float[] newData = Arrays.copyOf(vertexPositions, vertexPositions.length);

        for (int vert = 0; vert < newData.length; vert += 4) {
            newData[vert] += xOffset;
            newData[vert + 1] += yOffset;
        }

        glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
        glBufferSubData(GL_ARRAY_BUFFER, 0, newData);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void display()
    {
        final Pair<Float, Float> offsets = computePositionOffsets(glfwGetTime());

        adjustVertexData(offsets.e1, offsets.e2);

        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

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
