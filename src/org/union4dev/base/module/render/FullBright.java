package org.union4dev.base.module.render;

import net.minecraft.client.Minecraft;
import org.union4dev.base.annotations.module.Disable;
import org.union4dev.base.annotations.module.Enable;

public class FullBright {

    @Enable
    public void onEnable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 300;
    }

    @Disable
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
    }


}
