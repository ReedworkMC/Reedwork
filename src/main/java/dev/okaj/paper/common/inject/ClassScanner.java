package dev.okaj.paper.common.inject;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public final class ClassScanner {

    private final JavaPlugin plugin;

    public ClassScanner(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<Class<?>> scan(String packageName) {

        List<Class<?>> classes = new ArrayList<>();

        String path = packageName.replace('.', '/');

        try {

            File jar = new File(
                    plugin.getClass()
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );

            try (JarFile jarFile = new JarFile(jar)) {

                jarFile.stream()
                        .filter(entry -> entry.getName().startsWith(path))
                        .filter(entry -> entry.getName().endsWith(".class"))
                        .filter(entry -> !entry.getName().contains("$"))
                        .forEach(entry -> {

                            String className =
                                    entry.getName()
                                            .replace("/", ".")
                                            .replace(".class", "");

                            try {
                                classes.add(
                                        Class.forName(
                                                className,
                                                false,
                                                plugin.getClass().getClassLoader()
                                        )
                                );

                            } catch (Throwable e) {
                                plugin.getLogger()
                                        .warning("Failed loading " + className);
                            }
                        });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return classes;
    }
}