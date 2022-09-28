package me.miquiis.skinchangerapi.mixin.client;

import me.miquiis.skinchangerapi.SkinChangerAPI;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;")
    private void getLocationSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) (Object)this;
        if (SkinChangerAPI.getPlayerSkin(clientPlayer).equals(SkinChangerAPI.PLAYER_SKIN.getDefaultValueSupplier().get()))
        {
            cir.setReturnValue(SkinChangerAPI.getPlayerSkin(clientPlayer));
        }
    }

}
