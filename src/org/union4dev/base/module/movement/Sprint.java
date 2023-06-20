package org.union4dev.base.module.movement;

import org.union4dev.base.Access;
import org.union4dev.base.annotations.event.EventTarget;
import org.union4dev.base.annotations.module.Disable;
import org.union4dev.base.annotations.module.Enable;
import org.union4dev.base.events.update.TickEvent;
import org.union4dev.base.util.ChatUtil;

public class Sprint implements Access.InstanceAccess {

    /**
     * Subscribe a {@link TickEvent}
     *
     * @param event Event
     */
    @EventTarget
    public void onUpdate(TickEvent event) {

        // set suffix
        setSuffix("Normal", this);

        if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    }

    /**
     * An Example of onEnable
     * A class can contain multiple this annotations
     */
    @Enable
    public void onEnable() {
        ChatUtil.info("Sprint Enabled.");
    }

    /**
     * An Example of onDisable
     * A class can contain multiple this annotations
     */
    @Disable
    public void onDisable() {
        ChatUtil.info("Sprint Disabled.");
    }
}
