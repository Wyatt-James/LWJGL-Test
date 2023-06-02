package com.hippout.lwjgltest.exceptions;

import javax.annotation.*;

public class ProgramBuildException extends RuntimeException {
    public ProgramBuildException(@Nonnull String message)
    {
        super(message);
    }

    public ProgramBuildException()
    {
        super();
    }
}
