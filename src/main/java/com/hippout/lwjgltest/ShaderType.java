package com.hippout.lwjgltest;

import org.lwjgl.opengl.*;

public enum ShaderType {
    VERTEX(GL33.GL_VERTEX_SHADER, "Vertex"),
    GEOMETRY(GL33.GL_GEOMETRY_SHADER, "Geometry"),
    FRAGMENT(GL33.GL_FRAGMENT_SHADER, "Fragment");

    public final int glShaderType;
    public final String friendlyName;

    ShaderType(int glShaderType, String friendlyName)
    {
        this.glShaderType = glShaderType;
        this.friendlyName = friendlyName;
    }
}
