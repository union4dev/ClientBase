package cc.stylesclient.events.events.impl;

import cc.stylesclient.events.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class AttackEvent extends EventCancellable {
    private final Entity target;
    private final boolean isPre;

    public AttackEvent(Entity ent, boolean pre) {
        this.target = ent;
        this.isPre = pre;
    }

    public AttackEvent(Entity ent) {
        this.target = ent;
        this.isPre = false;
    }

    public boolean isPre() {
        return this.isPre;
    }

    public boolean isPost() {
        return !this.isPre;
    }

    public Entity getTarget() {
        return this.target;
    }
}
