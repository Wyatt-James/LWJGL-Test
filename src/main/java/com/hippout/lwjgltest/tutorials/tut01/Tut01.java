package com.hippout.lwjgltest.tutorials.tut01;

import com.hippout.lwjgltest.*;
import org.lwjgl.opengl.*;

import javax.annotation.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Tut01 extends GLTest {
    public static final String VERTEX_SHADER_STR = "" +
            "#version 330\n" +
            "layout(location = 0) in vec4 position;\n" +
            "void main()\n" +
            "{\n" +
            "   gl_Position = position;\n" +
            "}\n";
    public static final String FRAGMENT_SHADER_STR = "" +
            "#version 330\n" +
            "out vec4 outputColor;\n" +
            "void main()\n" +
            "{\n" +
            "   outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);\n" +
            "}\n";

    public static final float[] vertexPositions = {
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f,
    };

    private int program;
    private int positionBuffer;

    private long window;

    public Tut01()
    {
        super("tut01");
    }

    private int compileShader(@Nonnull ShaderType type, String source)
    {
        Objects.requireNonNull(type, "Shader type cannot be null.");
        Objects.requireNonNull(source, "Source cannot be null.");

        int shader = glCreateShader(type.glShaderType);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int success = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int logLen = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
            String log = glGetShaderInfoLog(shader, logLen);

            System.out.printf("%s shader failed to compile with error: %s\n", type.friendlyName, log);
        }

        return shader;
    }

    private int createProgram(List<Integer> shaderList)
    {
        int program = glCreateProgram();

        shaderList.forEach(s -> glAttachShader(program, s));

        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
            System.out.printf("Linker failure: %s\n", glGetProgramInfoLog(program));

        shaderList.forEach(s -> glDetachShader(program, s));

        return program;
    }

    private void initializeProgram()
    {
        final List<Integer> shaderList = new ArrayList<>();

        shaderList.add(compileShader(ShaderType.VERTEX, VERTEX_SHADER_STR));
        shaderList.add(compileShader(ShaderType.FRAGMENT, FRAGMENT_SHADER_STR));

        program = createProgram(shaderList);
        shaderList.forEach(GL20::glDeleteShader);
    }

    private void initializeVertexBuffer()
    {
        positionBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, positionBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexPositions, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void init(long window)
    {
        this.window = window;
        initializeProgram();
        initializeVertexBuffer();

        glfwSetWindowSizeCallback(window, new WindowSizeCallback());

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(program);

        glBindBuffer(GL_ARRAY_BUFFER, positionBuffer);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0L);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glUseProgram(0);

        glfwSwapBuffers(window);
    }
}
