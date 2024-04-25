package org.union4dev.base.module.render;

import net.minecraft.client.Minecraft;
import org.union4dev.base.annotations.system.Disable;
import org.union4dev.base.annotations.system.Enable;
import org.union4dev.base.annotations.system.Module;
import org.union4dev.base.module.Category;

@Module(value = "Full Bright",category = Category.Render)
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
