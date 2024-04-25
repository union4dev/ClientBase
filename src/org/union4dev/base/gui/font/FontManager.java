package org.union4dev.base.gui.font;

import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontManager {
    public CFontRenderer F14 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 14));
    public CFontRenderer F18 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 18));
    public CFontRenderer F16 = new CFontRenderer(getFont(new ResourceLocation("client/fonts/SFDisplay.ttf"), 16));
	
    private Font getFont(ResourceLocation resourceLocation, float fontSize) {
        try {
            Font output = Font.createFont(Font.PLAIN, Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream());
            output = output.deriveFont(fontSize);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}
