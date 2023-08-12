package com.hippout.lwjgltest.tutorials.tut04;

import com.hippout.lwjgltest.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class MatrixPerspective extends GLTest {

    public static final float[] vertexData = {
            0.25f, 0.25f, -1.25f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,
            -0.25f, 0.25f, -1.25f, 1.0f,

            0.25f, -0.25f, -1.25f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,
            -0.25f, 0.25f, -1.25f, 1.0f,

            0.25f, 0.25f, -2.75f, 1.0f,
            -0.25f, 0.25f, -2.75f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,
            -0.25f, 0.25f, -2.75f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,

            -0.25f, 0.25f, -1.25f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,

            -0.25f, 0.25f, -1.25f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,
            -0.25f, 0.25f, -2.75f, 1.0f,

            0.25f, 0.25f, -1.25f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,

            0.25f, 0.25f, -1.25f, 1.0f,
            0.25f, 0.25f, -2.75f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,

            0.25f, 0.25f, -2.75f, 1.0f,
            0.25f, 0.25f, -1.25f, 1.0f,
            -0.25f, 0.25f, -1.25f, 1.0f,

            0.25f, 0.25f, -2.75f, 1.0f,
            -0.25f, 0.25f, -1.25f, 1.0f,
            -0.25f, 0.25f, -2.75f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,


            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,

            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,

            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f
    };

    public static final float frustumScale = 1.0f, zNear = 0.5f, zFar = 3.0f;

    private int program;
    private int vertexBufferObject;

    private int offsetUniform;

    private long window;

    public MatrixPerspective()
    {
        super("tut04");
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("MatrixPerspective.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("StandardColors.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final int prog = Framework.createProgram(shaderList);

        offsetUniform = glGetUniformLocation(prog, "offset");

        int perspectiveMatrixUniform = glGetUniformLocation(prog, "perspectiveMatrix");

        glUseProgram(prog);

        final float[] perspectiveMatrix = new float[16];
        Arrays.fill(perspectiveMatrix, 0.0f);

        perspectiveMatrix[0] = frustumScale;
        perspectiveMatrix[5] = frustumScale;
        perspectiveMatrix[10] = (zFar + zNear) / (zNear - zFar);
        perspectiveMatrix[14] = (2 * zFar * zNear) / (zNear - zFar);
        perspectiveMatrix[11] = -1.0f;

        glUniformMatrix4fv(perspectiveMatrixUniform, false, perspectiveMatrix);

        glUseProgram(0);
        return prog;
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

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CW);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glUniform2f(offsetUniform, 0.5f, 0.5f);

        int colorDataOffset = vertexData.length * Float.BYTES / 2;
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, colorDataOffset);

        glDrawArrays(GL_TRIANGLES, 0, 36);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
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
