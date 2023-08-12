package com.hippout.lwjgltest.tutorials.tut05;

import com.hippout.lwjgltest.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class VertexClipping extends GLTest {

    public static final float frustumScale = 1.0f, zNear = 1.0f, zFar = 3.0f;

    private static final float[] perspectiveMatrix = new float[16];

    private final int numberOfVertices;
    private final float[] vertexData;
    private final short[] indexData;

    private int program;
    private int vertexBufferObject, indexBufferObject;

    private int vao;

    private int offsetUniform, perspectiveMatrixUniform;

    private long window;

    public VertexClipping()
    {
        super("tut05");

        perspectiveMatrix[0] = frustumScale;
        perspectiveMatrix[5] = frustumScale;
        perspectiveMatrix[10] = (zFar + zNear) / (zNear - zFar);
        perspectiveMatrix[14] = (2 * zFar * zNear) / (zNear - zFar);
        perspectiveMatrix[11] = -1.0f;

        numberOfVertices = MeshData.numberOfVertices;
        vertexData = MeshData.getVertexData();
        indexData = MeshData.getIndexData();
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("Standard.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("Standard.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final int prog = Framework.createProgram(shaderList);

        offsetUniform = glGetUniformLocation(prog, "offset");
        perspectiveMatrixUniform = glGetUniformLocation(prog, "perspectiveMatrix");

        initializePerspectiveMatrix(prog);

        return prog;
    }

    private void initializePerspectiveMatrix(int program)
    {
        glUseProgram(program);
        glUniformMatrix4fv(perspectiveMatrixUniform, false, perspectiveMatrix);
        glUseProgram(0);
    }

    private int initializeVertexBuffer()
    {
        int vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    private int initializeIndexBuffer()
    {
        int indexBuffer = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        return indexBuffer;
    }

    private int initializeVertexArrayObject()
    {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int posDataOffset1 = 0;
        int colorDataGlobalOffset = Float.BYTES * 3 * numberOfVertices;

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, posDataOffset1);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, colorDataGlobalOffset);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);

        glBindVertexArray(0);

        return vao;
    }

    public void init(long window)
    {
        this.window = window;

        program = initializeProgram();
        vertexBufferObject = initializeVertexBuffer();
        indexBufferObject = initializeIndexBuffer();
        vao = initializeVertexArrayObject();


        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CW);

        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 1.0f);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glClearDepth(1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram(program);

        glBindVertexArray(vao);
        glUniform3f(offsetUniform, 0.0f, 0.0f, 0.5f);
        glDrawElements(GL_TRIANGLES, indexData.length, GL_UNSIGNED_SHORT, 0);

        glBindVertexArray(vao);
        glUniform3f(offsetUniform, 0.0f, 0.0f, -1.0f);
        glDrawElementsBaseVertex(GL_TRIANGLES, indexData.length, GL_UNSIGNED_SHORT, 0, numberOfVertices / 2);

        glBindVertexArray(0);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }

    @Override
    protected void onWindowSizeChange(long window, int width, int height)
    {
        perspectiveMatrix[0] = frustumScale / (width / (float) height);
        perspectiveMatrix[5] = frustumScale;
        initializePerspectiveMatrix(program);
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
