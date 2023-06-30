package org.union4dev.base.module.render;

import org.lwjgl.input.Keyboard;
import org.union4dev.base.Access;
import org.union4dev.base.annotations.module.Binding;
import org.union4dev.base.annotations.module.Enable;

@Binding(Keyboard.KEY_RSHIFT)
public class ClickGui implements Access.InstanceAccess {
    @Enable
    public void onEnable(){
        setEnable(this,false);
        mc.displayGuiScreen(access.getClickGui());
    }
}
