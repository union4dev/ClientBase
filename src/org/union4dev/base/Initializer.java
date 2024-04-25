package org.union4dev.base;

import org.union4dev.base.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface Initializer {
   default void initialize(Consumer<Class<?>> handler){
        List<String> paths = new ArrayList<>();

        // 在此处添加你的包名，比如me.client.client.
        // 如果需要混淆建议添加自定义包名
        paths.add(Initializer.class.getName().replace(".Initializer","."));

        for (String path : paths) {
            if (!ReflectionUtil.dirExist(path)) {
                continue;
            }
            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);
            for (Class<?> clazz : classes) {
                handler.accept(clazz);
            }
            break;
        }
    }
}
