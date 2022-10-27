package me.miquiis.skinchangerapi.mixin.client;

import me.miquiis.skinchangerapi.SkinChangerAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "isChild", cancellable = true)
    private void _isChild(CallbackInfoReturnable<Boolean> cir)
    {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (livingEntity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)livingEntity;
            if (!SkinChangerAPI.getPlayerSkin(player).getSkinId().isEmpty())
            {
                cir.cancel();
                cir.setReturnValue(SkinChangerAPI.getPlayerSkin(player).isBaby());
            }
        }
    }

}
