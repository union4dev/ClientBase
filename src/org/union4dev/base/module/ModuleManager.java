package org.union4dev.base.module;

import net.minecraft.util.EnumChatFormatting;
import org.union4dev.base.annotations.module.Startup;
import org.union4dev.base.module.movement.Sprint;
import org.union4dev.base.module.render.FullBright;
import org.union4dev.base.module.render.HUD;
import org.union4dev.base.value.AbstractValue;
import org.union4dev.base.value.impl.BooleanValue;
import org.union4dev.base.value.impl.ComboValue;
import org.union4dev.base.value.impl.NumberValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Module Manager, Manage and access module.
 *
 * @author cubk
 */
public final class ModuleManager {
    /**
     * Index Maps
     */
    private final HashMap<Class<?>, ModuleHandle> modules = new HashMap<>();
    private final HashMap<String, Class<?>> nameMap = new HashMap<>();
    private final HashMap<Object, Class<?>> objectMap = new HashMap<>();

    /**
     * Initialize modules
     */
    public ModuleManager() {
        // Register Combat

        // Register Movement
        register(Sprint.class, "Sprint", Category.Movement);

        // Register Render
        register(HUD.class, "HUD", Category.Render);
        register(FullBright.class, "FullBright", Category.Render);

        // Register Misc

    }

    /**
     * Register a module to manager
     *
     * @param clazz    Module Class
     * @param name     Name
     * @param category Module Category, see {@link Category}
     */
    private void register(Class<?> clazz, String name, Category category) {
        try {
            Object instance = clazz.newInstance();
            ModuleHandle module = new ModuleHandle(name, category, instance);

            nameMap.put(name.toLowerCase(), clazz);
            objectMap.put(instance, clazz);

            if (clazz.isAnnotationPresent(Startup.class)) {
                module.setEnable(true);
            }

            for (final Field field : clazz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    final Object obj = field.get(instance);
                    if (obj instanceof AbstractValue<?>) {
                        module.getValues().add((AbstractValue<?>) obj);
                        sortValue(module);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            this.modules.put(clazz, module);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * sort value
     *
     * @param mod module handle
     */
    private void sortValue(ModuleHandle mod) {
        ArrayList<AbstractValue<?>> sorted = new ArrayList<>();
        ArrayList<AbstractValue<?>> values = mod.getValues();

        for (AbstractValue<?> v : values) {
            if (v instanceof ComboValue)
                sorted.add(v);
        }
        for (AbstractValue<?> v : values) {
            if (v instanceof NumberValue)
                sorted.add(v);
        }

        for (AbstractValue<?> v : values) {
            if (v instanceof BooleanValue)
                sorted.add(v);
        }

        mod.setValues(sorted);
    }

    /**
     * Get a module class by name
     *
     * @param name Module Name
     * @return {@link Class}<{@link ?}>
     */
    public Class<?> getModuleClass(String name) {
        return nameMap.get(name.toLowerCase());
    }

    /**
     * Toggle module state
     *
     * @param module module class
     */
    public boolean toggle(Class<?> module) {
        modules.get(module).setEnable(!modules.get(module).isEnabled());
        return modules.get(module).isEnabled();
    }

    /**
     * Get a module name
     *
     * @param module Module Class
     * @return {@link String}
     */
    public String getName(Class<?> module) {
        return modules.get(module).getName();
    }


    /**
     * Get a module key bind
     *
     * @param module module class
     * @return key bind code
     */
    public int getKey(Class<?> module) {
        return modules.get(module).getKey();
    }

    /**
     * Set a module key bind
     *
     * @param module module class
     * @param key    key bind code
     */
    public void setKey(Class<?> module, int key) {
        modules.get(module).setKey(key);
    }

    /**
     * Get all module classes
     *
     * @return {@link Iterable}<{@link Class}<{@link ?}>>
     */
    public Iterable<Class<?>> getModules() {
        return modules.keySet();
    }

    /**
     * format module to array display (+suffix)
     *
     * @param module module class
     * @return {@link String}
     */
    public String format(Class<?> module) {
        ModuleHandle m = modules.get(module);
        return m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getSuffix());
    }

    /**
     * Get handle
     *
     * @param module module class
     * @return {@link ModuleHandle}
     */
    public ModuleHandle getHandle(Class<?> module) {
        return modules.get(module);
    }

    /**
     * Get handle
     *
     * @param object module object
     * @return {@link ModuleHandle}
     */
    public ModuleHandle getHandle(Object object) {
        return modules.get(objectMap.get(object));
    }

    /**
     * Get all values for module
     *
     * @param module Module Class
     * @return {@link Iterable}<{@link AbstractValue}<{@link ?}>>
     */
    public Iterable<AbstractValue<?>> getValues(Class<?> module) {
        return new ArrayList<>(modules.get(module).getValues());
    }

    /**
     * Get a module enable status
     *
     * @param module Module Class
     * @return Status
     */
    public boolean isEnabled(Class<?> module) {
        return modules.get(module).isEnabled();
    }

    /**
     * Set a module enable status
     *
     * @param module Module Class
     * @param state  Status
     */
    public void setEnable(Class<?> module, boolean state) {
        this.modules.get(module).setEnable(state);
    }
}
