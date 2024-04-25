package org.union4dev.base.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cubk
 */
public class LiteInvoke {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    /**
     * Registers a bean instance.
     */
    public void registerBean(Object beanInstance) {
        beans.put(beanInstance.getClass(), beanInstance);
    }

    /**
     * Registers multi bean instance.
     */
    public void registerBean(Object... beanInstances) {
        for (Object beanInstance : beanInstances) {
            beans.put(beanInstance.getClass(), beanInstance);
        }
    }

    /**
     * Get instance of the type.
     *
     * @return The instance of type if found, otherwise null.
     */
    public <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(beans.get(beanClass));
    }


    /**
     * Registers fields annotated with @Instance in the provided instance.
     *
     * @param instance The instance whose fields are to be registered as beans.
     */
    public void registerFields(Object instance) throws Exception{
        boolean all = instance.getClass().isAnnotationPresent(Instance.class);
        for(Field field : instance.getClass().getDeclaredFields()){
            if(all || field.isAnnotationPresent(Instance.class)){
                field.setAccessible(true);
                registerBean(field.get(Modifier.isStatic(field.getModifiers()) ? null : instance));
            }
        }
    }

    /**
     * Registers static fields annotated with @Instance in the provided class.
     *
     * @param clazz The class whose static fields are to be registered as beans.
     */
    public void registerFields(Class<?> clazz) throws Exception{
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Instance.class) && Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                registerBean(field.get(null));
            }
        }
    }

    /**
     * Autowires fields annotated with @Autowired in the provided instance.
     */
    public void autoWired(Object instance) throws Exception {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object bean = getBean(field.getType());
                if (bean != null) {
                    System.out.println(field.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }
        }
    }

    /**
     * Creates an instance of the specified class using constructor injection.
     *
     * @param clazz The class from which to create an instance.
     * @param <T>   The type of the instance to be created.
     * @return An instance of the specified class with injected dependencies.
     */
    public <T> T createInstance(Class<T> clazz) throws Exception {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<T> constr = null;
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            boolean foundParam = true;
            for (Parameter parameter : parameters) {
                if (!beans.containsKey(parameter.getType())) {
                    foundParam = false;
                    break;
                }
            }
            if (foundParam) {
                constr = (Constructor<T>) constructor;
                break;
            }
        }
        if (constr == null) {
            throw new IllegalArgumentException("Your constructor is very sb.");
        }
        constr.setAccessible(true);
        Object[] args = new Object[constr.getParameterCount()];
        for (int i = 0; i < args.length; i++) {
            args[i] = beans.get(constr.getParameterTypes()[i]);
        }

        T inst = constr.newInstance(args);

        return inst;
    }


    /**
     * Invokes a method on the provided instance with dependency injection support.
     *
     * @param instance The instance on which to invoke the method.
     * @param method   The method to invoke.
     * @return The result of method invocation.
     */
    public Object invokeMethod(Object instance,Method method) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterType = parameter.getType();
            if (beans.containsKey(parameterType)) {
                args[i] = beans.get(parameterType);
            } else {
                throw new IllegalArgumentException("Instance not found for parameter: " + parameter.getName());
            }
        }
        method.setAccessible(true);
        return method.invoke(instance, args);
    }

    public Object invokeMethod(Object instance,Method method,Object firstParam) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        args[0] = firstParam;
        for (int i = 1; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterType = parameter.getType();
            if (beans.containsKey(parameterType)) {
                args[i] = beans.get(parameterType);
            } else {
                throw new IllegalArgumentException("Instance not found for parameter: " + parameter.getName());
            }
        }
        method.setAccessible(true);
        return method.invoke(instance, args);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Autowired {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Instance {
    }
}
