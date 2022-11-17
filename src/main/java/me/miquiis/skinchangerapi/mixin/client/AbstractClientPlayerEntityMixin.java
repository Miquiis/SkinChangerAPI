package me.miquiis.skinchangerapi.mixin.client;

import me.miquiis.skinchangerapi.SkinChangerAPI;
import me.miquiis.skinchangerapi.client.DownloadingTexture;
import me.miquiis.skinchangerapi.client.SkinChangerAPIClient;
import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "getSkinType")
    private void getSkinType(CallbackInfoReturnable<String> cir)
    {
        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) (Object)this;
        if (!SkinChangerAPI.getPlayerSkin(clientPlayer).getSkinId().isEmpty())
        {
            cir.setReturnValue(SkinChangerAPI.getPlayerSkin(clientPlayer).isSlim() ? "slim" : "default");
        }
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;")
    private void getLocationSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) (Object)this;
        if (!SkinChangerAPI.getPlayerSkin(clientPlayer).getSkinId().isEmpty())
        {
            SkinLocation playerSkinLocation = SkinChangerAPI.getPlayerSkin(clientPlayer);
            Texture texture = minecraft.getTextureManager().getTexture(playerSkinLocation.getSkinLocation());
            if (texture != null) {
                cir.setReturnValue(playerSkinLocation.getSkinLocation());
            } else if (!playerSkinLocation.getSkinURL().isEmpty()){
                SkinChangerAPIClient.loadSkin(playerSkinLocation);
                return;
            }
            cir.setReturnValue(playerSkinLocation.getSkinLocation());
        }
    }

}
