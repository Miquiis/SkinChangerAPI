package me.miquiis.skinchangerapi.client;

import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class LoadSkinTextureEvent extends Event {

    private SkinLocation skinLocation;
    @Nullable
    private Texture texture;

    public LoadSkinTextureEvent(SkinLocation skinLocation, @Nullable Texture texture)
    {
        this.skinLocation = skinLocation;
        this.texture = texture;
    }

    public SkinLocation getSkinLocation() {
        return skinLocation;
    }

    public Texture getTexture() {
        return texture;
    }

    @Cancelable
    public static class Pre extends LoadSkinTextureEvent
    {
        public Pre(SkinLocation skinLocation, @Nullable Texture texture) {
            super(skinLocation, texture);
        }
    }

    public static class Post extends LoadSkinTextureEvent
    {
        public Post(SkinLocation skinLocation, @Nullable Texture texture) {
            super(skinLocation, texture);
        }
    }
}
