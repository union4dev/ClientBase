package org.union4dev.base.module;

import com.darkmagician6.eventapi.EventManager;

public final class Module {

    private boolean state;

    private final String name;

    private final Category category;

    private final Object object;

    public Module(String name,Category category,Object object){
        this.state = false;
        this.name = name;
        this.category = category;
        this.object = object;
    }

    void setEnable(boolean state) {

        if(state == this.state) return;

        this.state = state;

        if(state){
            EventManager.register(object);
        }else {
            EventManager.unregister(object);
        }
    }

    Category getCategory() {
        return category;
    }

    String getName() {
        return name;
    }

    boolean isEnabled() {
        return state;
    }
}
