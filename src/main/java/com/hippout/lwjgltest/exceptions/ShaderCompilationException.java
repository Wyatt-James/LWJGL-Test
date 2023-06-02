package com.hippout.lwjgltest.exceptions;

import javax.annotation.*;

public class ShaderCompilationException extends RuntimeException {
    public ShaderCompilationException(@Nonnull String message)
    {
        super(message);
    }

    public ShaderCompilationException()
    {
        super();
    }
}
