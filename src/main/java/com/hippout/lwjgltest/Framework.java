package com.hippout.lwjgltest;

import com.hippout.lwjgltest.exceptions.*;
import com.hippout.lwjgltest.io.*;
import com.hippout.lwjgltest.tutorials.tut03.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.annotation.*;
import java.nio.*;
import java.util.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Framework {

    public static final String shaderBasePath = "resources/shaders";

    public static ResourceLoader jarResourceLoader;

    // The window handle
    private long window;

    public static void main(String[] args)
    {
        // Wait if we have received an arg
        if (args.length >= 1) {
            final String waitTimeStr = args[0];
            try {
                final double waitTime = Double.parseDouble(waitTimeStr);

                System.out.printf("Waiting for %f seconds.\n", waitTime);
                Thread.sleep((long) (waitTime * 1000));
            } catch (NumberFormatException e) {
                System.out.printf("Arg error: %s is not a valid double. Will not wait.\n", waitTimeStr);
            } catch (InterruptedException e) {
                System.out.println("Finished waiting.");
            }
        }

        new Framework().run();
    }

    public void run()
    {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static int createProgram(@Nonnull List<Integer> shaderList)
    {
        Objects.requireNonNull(shaderList, "Shader list cannot be null.");

        int program = glCreateProgram();

        shaderList.forEach(s -> glAttachShader(program, s));

        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
            throw new ProgramBuildException(String.format("Linker failure: %s\n", glGetProgramInfoLog(program)));

        shaderList.forEach(s -> glDetachShader(program, s));
        shaderList.forEach(GL20::glDeleteShader);

        return program;
    }

    public static int compileShader(@Nonnull ShaderType type, String source)
    {
        Objects.requireNonNull(type, "Shader type cannot be null.");
        Objects.requireNonNull(source, "Source cannot be null.");

        int shader = glCreateShader(type.glShaderType);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int success = glGetShaderi(shader, GL_COMPILE_STATUS);

        if (success == GL_FALSE)
            throw new ShaderCompilationException(
                    String.format("%s shader failed to compile with error: %s\n",
                            type.friendlyName, glGetShaderInfoLog(shader)));

        return shader;
    }

    private void init()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automaticallyLocalJarResourceLoader

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void loop()
    {
        final GLTest test = new FragChangeColor();

        test.init(window);

        // Make the window visible
        glfwShowWindow(window);

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            test.display();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}