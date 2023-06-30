package org.union4dev.base.module.handlers;

import net.minecraft.client.Minecraft;
import org.union4dev.base.events.EventManager;

public class SubModuleHandle {

    protected final Minecraft mc = Minecraft.getMinecraft();

    private final ModuleHandle parent;
    private boolean enabled;
    private final String name;

    public SubModuleHandle(ModuleHandle parent,String name,Object instance) {
        this.parent = parent;
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) subscribe();
            else remove();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName(){
        return name;
    };

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    private void subscribe() {
        try {
            EventManager.register(this);
            onEnable();
        } catch (Exception ex) {
            remove();
            enabled = false;
            if (mc.thePlayer != null || mc.theWorld != null) {
                ex.printStackTrace();
            }
        }
    }

    private void remove() {
        try {
            EventManager.unregister(this);
            onDisable();
        } catch (Exception ex) {
            if (mc.thePlayer != null || mc.theWorld != null) {
                ex.printStackTrace();
            }
        }
    }

    public ModuleHandle getParent() {
        return parent;
    }
}
