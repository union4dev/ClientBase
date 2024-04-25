package org.union4dev.base.module.render;

import org.lwjgl.input.Keyboard;
import org.union4dev.base.Access;
import org.union4dev.base.annotations.system.Binding;
import org.union4dev.base.annotations.system.Enable;
import org.union4dev.base.annotations.system.Module;
import org.union4dev.base.gui.click.ClickGuiScreen;
import org.union4dev.base.module.Category;

@Module(value = "Click Gui",category = Category.Render)
@Binding(Keyboard.KEY_RSHIFT)
public class ClickGui implements Access.InstanceAccess {
    @Enable
    public void onEnable(ClickGuiScreen clickGuiScreen){
        setEnable(this,false);
        mc.displayGuiScreen(clickGuiScreen);
    }
}
