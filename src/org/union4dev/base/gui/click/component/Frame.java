package org.union4dev.base.gui.click.component;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import org.union4dev.base.Access;
import org.union4dev.base.gui.click.ClickGuiScreen;
import org.union4dev.base.gui.click.component.components.Button;
import org.union4dev.base.module.Category;

import java.util.ArrayList;


public class Frame {

    public ArrayList<Component> components;
    public Category category;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public Frame(Category cat) {
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (Class<?> mod : Access.getInstance().getModuleManager().getModulesByCategory(category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame() {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, ClickGuiScreen.color);
        GL11.glPushMatrix();
        Access.getInstance().getFontManager().F18.drawStringWithShadow(this.category.name(), (this.x + 2)  + 5, (this.y + 2.5f) , 0xFFFFFFFF);
        Access.getInstance().getFontManager().F18.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10)  + 3, (this.y + 2.5f) , -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.components.isEmpty()) {
                for (Component component : components) {
                    component.renderComponent();
                }
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
	}

}
