package com.hippout.lwjgltest.tutorials.tut06;

import com.hippout.lwjgltest.*;
import com.hippout.lwjgltest.util.*;

import java.io.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Scale extends GLTest {

    public static final float frustumScale = calculateFrustumScale(45.0f), zNear = 1.0f, zFar = 45.0f;
    public static final MapFunction<Float, Vec3f> nullScale = (f) -> new Vec3f(1.0f, 1.0f, 1.0f);
    public static final MapFunction<Float, Vec3f> staticUniformScale = (f) -> new Vec3f(4.0f, 4.0f, 4.0f);
    public static final MapFunction<Float, Vec3f> staticNonUniformScale = (f) -> new Vec3f(0.5f, 1.0f, 10.0f);
    public static final MapFunction<Float, Vec3f> dynamicUniformScale = (elapsedTime) ->
    {
        final float loopDuration = 3.0f;

        return new Vec3f(MathUtil.lerp(1.0f, 4.0f, MathUtil.calcLerpFactor(elapsedTime, loopDuration)));
    };
    public static final MapFunction<Float, Vec3f> dynamicNonUniformScale = (elapsedTime) ->
    {
        final float xLoopDuration = 3.0f;
        final float zLoopDuration = 5.0f;

        return new Vec3f(
                MathUtil.lerp(1.0f, 0.5f, MathUtil.calcLerpFactor(elapsedTime, xLoopDuration)),
                1.0f,
                MathUtil.lerp(1.0f, 10.0f, MathUtil.calcLerpFactor(elapsedTime, zLoopDuration)));
    };
    private final Vec4f[] cameraToClipMatrix;
    private final int numberOfVertices;
    private final float[] vertexData;
    private final short[] indexData;
    private final Instance[] instances = new Instance[]{
            new Instance(nullScale, new Vec3f(0.0f, 0.0f, -45.0f)),
            new Instance(staticUniformScale, new Vec3f(-10.0f, -10.0f, -45.0f)),
            new Instance(staticNonUniformScale, new Vec3f(-10.0f, 10.0f, -45.0f)),
            new Instance(dynamicUniformScale, new Vec3f(10.0f, 10.0f, -45.0f)),
            new Instance(dynamicNonUniformScale, new Vec3f(10.0f, -10.0f, -45.0f))
    };
    private int program;
    private int vertexBufferObject, indexBufferObject, vao;
    private int modelToCameraMatrixUnif, cameraToClipMatrixUnif;
    private long window;

    public Scale()
    {
        super("tut06");

        cameraToClipMatrix = Mtx4fUtil.emptyMtx();
        cameraToClipMatrix[0].x = frustumScale;
        cameraToClipMatrix[1].y = frustumScale;
        cameraToClipMatrix[2].z = (zFar + zNear) / (zNear - zFar);
        cameraToClipMatrix[2].w = -1.0f;
        cameraToClipMatrix[3].z = (2 * zFar * zNear) / (zNear - zFar);

        numberOfVertices = Tut06MeshData.numberOfVertices;
        vertexData = Tut06MeshData.getVertexData();
        indexData = Tut06MeshData.getIndexData();
    }

    public static float calculateFrustumScale(float fovDegrees)
    {
        return (float) (1.0 / Math.tan(Math.toRadians(fovDegrees) / 2.0));
    }

    private int initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        // Load our shaders from file
        try {
            shaderList.add(Framework.compileShader(ShaderType.VERTEX, shaderLoader.loadText("PosColorLocalTransform.vert")));
            shaderList.add(Framework.compileShader(ShaderType.FRAGMENT, shaderLoader.loadText("ColorPassthrough.frag")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final int prog = Framework.createProgram(shaderList);

        modelToCameraMatrixUnif = glGetUniformLocation(prog, "modelToCameraMatrix");
        cameraToClipMatrixUnif = glGetUniformLocation(prog, "cameraToClipMatrix");

        initializePerspectiveMatrix(prog);

        return prog;
    }

    private void initializePerspectiveMatrix(int program)
    {
        glUseProgram(program);
        glUniformMatrix4fv(cameraToClipMatrixUnif, false, Mtx4fUtil.toGlMatrix(cameraToClipMatrix));
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

        int posDataOffset = 0;
        int colorDataOffset = Float.BYTES * 3 * numberOfVertices;

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, posDataOffset);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, colorDataOffset);
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
        glClearDepth(1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram(program);
        glBindVertexArray(vao);

        float elapsedTime = (float) glfwGetTime();

        for (Instance i : instances) {
            final Vec4f[] mtx = i.constructMatrix(elapsedTime);

            glUniformMatrix4fv(modelToCameraMatrixUnif, false, Mtx4fUtil.toGlMatrix(mtx));
            glDrawElements(GL_TRIANGLES, indexData.length, GL_UNSIGNED_SHORT, 0);
        }

        glBindVertexArray(0);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }

    @Override
    protected void onWindowSizeChange(long window, int width, int height)
    {
        cameraToClipMatrix[0].x = frustumScale * (height / (float) width);
        cameraToClipMatrix[1].y = frustumScale;

        initializePerspectiveMatrix(program);
        glViewport(0, 0, width, height);
    }

    @Override
    protected final void onKeyboard(long window, int key)
    {
        if (key == GLFW_KEY_ESCAPE)
            glfwSetWindowShouldClose(window, true);
    }

    private static class Instance {

        private final MapFunction<Float, Vec3f> scaleSupplier;
        private final Vec3f offset;

        public Instance(MapFunction<Float, Vec3f> scaleSupplier, Vec3f offset)
        {
            this.scaleSupplier = Objects.requireNonNull(scaleSupplier, "Scale supplier cannot be null.");
            this.offset = Objects.requireNonNull(offset, "Offset cannot be null.");
        }

        public Vec4f[] constructMatrix(float elapsedTime)
        {
            final Vec3f scale = scaleSupplier.get(elapsedTime);

            final Vec4f[] mtx = Mtx4fUtil.identityMtx();

            // Apply scale
            mtx[0].x = scale.x;
            mtx[1].y = scale.y;
            mtx[2].z = scale.z;

            // Apply translation
            mtx[3] = new Vec4f(offset, 1.0f);

            return mtx;
        }
    }
}
