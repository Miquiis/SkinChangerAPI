package me.miquiis.skinchangerapi.client;

import me.miquiis.skinchangerapi.SkinChangerAPI;
import me.miquiis.skinchangerapi.client.cache.TextureCache;
import me.miquiis.skinchangerapi.common.LocalCache;
import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Optional;
import java.util.function.Predicate;

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
                    // TODO: Add caching skin textures to URL
                    Predicate<LocalCache<TextureCache>.Cached> predicate = cached -> cached.getValue().getUrl().equals(skinLocation.getSkinURL());
                    if (SkinChangerAPI.getInstance().getClientTextureCache().hasCache(predicate))
                    {
                        try {
                            Optional<LocalCache<TextureCache>.Cached> optionalCached = SkinChangerAPI.getInstance().getClientTextureCache().getCache(predicate);
                            if (optionalCached.isPresent())
                            {
                                File tempFile = File.createTempFile(skinLocation.getSkinId(), ".png", null);
                                FileOutputStream fos = new FileOutputStream(tempFile);
                                fos.write(optionalCached.get().getValue().getTextureBytes());
                                fos.close();
                                try (LoadingLocalTexture downloadingTexture = new LoadingLocalTexture(null, tempFile, DefaultPlayerSkin.getDefaultSkinLegacy(), true, null)) {
                                    minecraft.getTextureManager().loadTexture(skinLocation.getSkinLocation(), downloadingTexture);
                                }
                                if (optionalCached.get().getSecondsSinceLastUpdated() >= 60 * 5)
                                {
                                    try (DownloadingTexture downloadingTexture = new DownloadingTexture(null, skinLocation.getSkinURL(), DefaultPlayerSkin.getDefaultSkinLegacy(), true, null)) {
                                        minecraft.getTextureManager().loadTexture(skinLocation.getSkinLocation(), downloadingTexture);
                                    }
                                }
                            }
                            return;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
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
