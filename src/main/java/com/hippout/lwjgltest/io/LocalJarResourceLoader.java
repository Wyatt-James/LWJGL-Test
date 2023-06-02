package com.hippout.lwjgltest.io;

import com.hippout.lwjgltest.*;
import com.hippout.lwjgltest.util.*;

import javax.annotation.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.jar.*;
import java.util.stream.*;
import java.util.zip.*;

/**
 * A persistent object for loading file resources from a JAR file.
 *
 * @author Wyatt James
 */
public final class LocalJarResourceLoader implements ResourceLoader {
    public static final String PATH_SEPARATOR = "/";

    public final String basePath, jarPath;
    private final JarFile jarFile;
    private boolean isClosed;

    public LocalJarResourceLoader(@Nonnull String basePath) throws IOException
    {
        this(getMainJarPath(), basePath, false);
    }

    public LocalJarResourceLoader(@Nonnull String jarPath, @Nonnull String basePath, boolean verifyJarFile) throws IOException
    {
        this.basePath = Objects.requireNonNull(basePath, "Base path cannot be null.");
        this.jarPath = Objects.requireNonNull(jarPath, "JAR path cannot be null.");

        this.jarFile = new JarFile(new File(jarPath), verifyJarFile, ZipFile.OPEN_READ);
        final JarEntry baseJarEntry = jarFile.getJarEntry(basePath);

        if (baseJarEntry == null)
            throw new FileNotFoundException("Base URI does not exist inside JAR. URI: " + basePath);

        if (!baseJarEntry.isDirectory())
            throw new IOException("Base URI is not a directory.");
    }

    private static String getMainJarPath()
    {
        try {
            return Framework.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Uncorrectable URISyntaxException on resolving the main JAR's path.");
        }
    }

    @Override
    public String loadText(@Nonnull String path, @Nonnull String fileName) throws IOException
    {
        return new BufferedReader(new InputStreamReader(loadResource(path, fileName), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }

    @Override
    public String loadText(@Nonnull String filePath) throws IOException
    {
        return new BufferedReader(new InputStreamReader(loadResource(filePath), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }

    @Override
    @Nonnull
    public InputStream loadResource(@Nonnull String path, @Nonnull String fileName) throws IOException
    {
        Objects.requireNonNull(path, "Path cannot be null.");
        Objects.requireNonNull(fileName, "File name cannot be null.");

        return internalLoadResource(combinePath(basePath, path, fileName));
    }

    @Override
    @Nonnull
    public InputStream loadResource(@Nonnull String filePath) throws IOException
    {
        if (isClosed)
            throw new IllegalStateException("Cannot use a closed LocalJarResourceLoader.");

        Objects.requireNonNull(filePath, "File path cannot be null.");

        return internalLoadResource(combinePath(basePath, filePath));
    }

    @Nonnull
    private InputStream internalLoadResource(@Nonnull String fullPath) throws IOException
    {
        final ZipEntry entry = jarFile.getEntry(fullPath);

        if (entry == null)
            throw new FileNotFoundException(
                    String.format("File could not be found at %s inside JAR.", fullPath));

        if (entry.isDirectory())
            throw new IllegalArgumentException(
                    String.format("Cannot load resource %s as it is a directory.", fullPath));

        return jarFile.getInputStream(entry);
    }

    @Override
    @Nonnull
    public String combinePath(@Nonnull String... parts)
    {
        return StringUtil.concatArrayToString(PATH_SEPARATOR, (Object[]) parts);
    }

    @Override
    public void close() throws IOException
    {
        if (isClosed)
            throw new IllegalStateException("Cannot close already-closed LocalJarResourceLoader.");

        isClosed = true;
        jarFile.close();
    }
}
