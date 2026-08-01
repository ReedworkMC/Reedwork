package dev.okaj.paper.common.inject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ClassScanner {

    public List<Class<?>> scan(String packageName) {

        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        try {
            ClassLoader classLoader =
                    Thread.currentThread()
                            .getContextClassLoader();

            Enumeration<URL> resources =
                    classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource =
                        resources.nextElement();

                File directory =
                        new File(resource.toURI());

                scanDirectory(
                        packageName,
                        directory,
                        classes
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not scan package " + packageName,
                    e
            );
        }

        return classes;
    }


    private void scanDirectory(String packageName, File directory, List<Class<?>> classes) {

        File[] files = directory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(
                        packageName + "." + file.getName(),
                        file,
                        classes
                );
            } else if (file.getName().endsWith(".class")) {

                String className =
                        packageName
                                + "."
                                + file.getName()
                                .replace(".class", "");

                try {
                    classes.add(Class.forName(className));

                } catch (ClassNotFoundException ignored) {

                }
            }
        }
    }
}
