package dev.reedworkmc.reedwork.inject.scanner;

import dev.reedworkmc.reedwork.logger.PaperLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public final class ClassScanner {

    private final ClassLoader classLoader;
    private final File source;
    private final PaperLogger logger;

    public ClassScanner(ClassLoader classLoader, File source, PaperLogger logger) {
        this.classLoader = classLoader;
        this.source = source;
        this.logger = logger;
    }

    public List<Class<?>> scan(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        try {
            try (JarFile jarFile = new JarFile(source)) {
                jarFile.stream()
                        .filter(entry -> entry.getName().startsWith(path))
                        .filter(entry -> entry.getName().endsWith(".class"))
                        .filter(entry -> !entry.getName().contains("$"))
                        .forEach(entry -> {

                            String className = entry.getName()
                                    .replace("/", ".")
                                    .replace(".class", "");

                            try {
                                classes.add(
                                        Class.forName(
                                                className,
                                                false,
                                                classLoader
                                        )
                                );

                            } catch (Throwable e) {
                                String classLoaderName = classLoader == null ? "null" : classLoader.getName();
                                logger.error("Failed loading " + className + " using ClassLoader: " + classLoaderName, e);
                            }
                        });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classes;
    }
}