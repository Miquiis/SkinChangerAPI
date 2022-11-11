package me.miquiis.skinchangerapi.client;

import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SkinChangerAPIClient {

    public static void loadSkin(SkinLocation skinLocation)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if (!skinLocation.getSkinId().isEmpty())
        {
            Texture texture = minecraft.getTextureManager().getTexture(skinLocation.getSkinLocation());
            if (MinecraftForge.EVENT_BUS.post(new LoadSkinTextureEvent.Pre(skinLocation, texture))) return;
            if (!skinLocation.getSkinURL().isEmpty()) {
                File skinTextureFile = new File(skinLocation.getSkinURL());
                if (!skinTextureFile.exists())
                {
                    try (DownloadingTexture downloadingTexture = new DownloadingTexture(null, skinLocation.getSkinURL(), DefaultPlayerSkin.getDefaultSkinLegacy(), true, null)) {
                        minecraft.getTextureManager().loadTexture(skinLocation.getSkinLocation(), downloadingTexture);
                    }
                } else {
                    try (LoadingLocalTexture downloadingTexture = new LoadingLocalTexture(null, skinTextureFile, DefaultPlayerSkin.getDefaultSkinLegacy(), true, null)) {
                        minecraft.getTextureManager().loadTexture(skinLocation.getSkinLocation(), downloadingTexture);
                    }
                }
            }
            MinecraftForge.EVENT_BUS.post(new LoadSkinTextureEvent.Post(skinLocation, texture));
        }
    }

}
