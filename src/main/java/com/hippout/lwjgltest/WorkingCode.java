package com.hippout.lwjgltest;

import javax.annotation.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class WorkingCode implements GLTest {
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
    private long startTime = System.nanoTime();
    private long window;

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

    private void initializeProgram()
    {

    }

    public void init(long window)
    {
        this.window = window;
    }

    public void display()
    {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

        glfwSwapBuffers(window); // swap the color buffers
        glCreateS
    }
}
