package me.miquiis.skinchangerapi.client;

import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.resources.DefaultPlayerSkin;

public class SkinChangerAPIClient {

    public static void loadSkin(SkinLocation skinLocation)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if (!skinLocation.getSkinId().isEmpty())
        {
            Texture texture = minecraft.getTextureManager().getTexture(skinLocation.getSkinLocation());
            if (texture == null) {
                if (!skinLocation.getSkinURL().isEmpty()){
                    try (DownloadingTexture downloadingTexture = new DownloadingTexture(null, skinLocation.getSkinURL(), DefaultPlayerSkin.getDefaultSkinLegacy(), true, null)) {
                        minecraft.getTextureManager().loadTexture(skinLocation.getSkinLocation(), downloadingTexture);
                    }
                }
            }
        }
    }

}
