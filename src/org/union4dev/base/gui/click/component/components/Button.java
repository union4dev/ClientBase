package org.union4dev.base.gui.click.component.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import org.union4dev.base.Access;
import org.union4dev.base.gui.click.ClickGuiScreen;
import org.union4dev.base.gui.click.component.Component;
import org.union4dev.base.gui.click.component.Frame;
import org.union4dev.base.gui.click.component.components.sub.Checkbox;
import org.union4dev.base.gui.click.component.components.sub.*;
import org.union4dev.base.value.AbstractValue;
import org.union4dev.base.value.impl.BooleanValue;
import org.union4dev.base.value.impl.ComboValue;
import org.union4dev.base.value.impl.NumberValue;

import java.awt.*;
import java.util.ArrayList;

public class Button extends Component {

    public Class<?> mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private final ArrayList<Component> subcomponents;
    public boolean open;
    private final int height;

    public Button(Class<?> mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        height = 12;
        int opY = offset + 12;
        if (Access.getInstance().getModuleManager().hasValue(mod)) {
            for (AbstractValue<?> value : Access.getInstance().getModuleManager().getValues(mod)) {
                if (value instanceof ComboValue) {
                    this.subcomponents.add(new ModeButton(this, (ComboValue) value, opY));
                    opY += 12;
                }
                if (value instanceof NumberValue) {
                    Slider slider = new Slider((NumberValue) value, this, opY);
                    this.subcomponents.add(slider);
                    opY += 12;
                }
                if (value instanceof BooleanValue) {
                    Checkbox check = new Checkbox((BooleanValue) value, this, opY);
                    this.subcomponents.add(check);
                    opY += 12;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        int opY = offset + 12;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (Access.getInstance().getModuleManager().isEnabled(this.mod) ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (Access.getInstance().getModuleManager().isEnabled(this.mod) ? new Color(14, 14, 14).getRGB() : 0xFF111111));
        GL11.glPushMatrix();
        Access.getInstance().getFontManager().F18.drawStringWithShadow(Access.getInstance().getModuleManager().getName(this.mod), (parent.getX() + 2) , (parent.getY() + offset + 2)  , Access.getInstance().getModuleManager().isEnabled(this.mod) ? ClickGuiScreen.color : -1);
        if (this.subcomponents.size() > 2)
            Access.getInstance().getFontManager().F18.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) , (parent.getY() + offset + 2)  , -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.subcomponents.isEmpty()) {
                for (Component comp : this.subcomponents) {
                    comp.renderComponent();
                }
                Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGuiScreen.color);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return (12 * (this.subcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            Access.getInstance().getModuleManager().toggle(this.mod);
        }
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
		return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
	}
}
